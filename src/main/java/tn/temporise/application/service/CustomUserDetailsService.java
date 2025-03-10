package tn.temporise.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.BadCredentialsException;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.NonLocalProviderException;
import tn.temporise.application.exception.UsernameNotFoundException;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.domain.model.SigninUserRequest;
import tn.temporise.domain.model.TokenResponse;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    private final AuthRepo authenticationRepo;
    private final JwtUtil jwtUtil;



    @Override
    public CustomUserDetails loadUserByUsername(String email)  {
        try {
            // Find the user by email
            UtilisateurEntity user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email,"404"));

            // Check if the user has a non-local provider (e.g., Google or Facebook)
            AuthentificationEntity auth = authenticationRepo.findByUserEmail(user.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Authentication method not found for user: " + email,"404"));

            if (!auth.getProviderId().equals("0")) {
                // If the provider is not local, throw an exception
                throw new NonLocalProviderException("User is registered with a non-local provider. Please use the appropriate login method.","400");
            }

            // If the provider is local, return the UserDetails object
            return new CustomUserDetails(
                    user.getEmail(),
                    auth.getPassword(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .collect(Collectors.toList()),
                    auth.getProviderId()
            );
        } catch (InternalServerErrorException e) {
            log.error("Error loading user by username: ", e);
            throw new UsernameNotFoundException("internal error occured","500");
        }
    }

    public CustomUserDetails getUserDetails(String email,String provider_id) throws UsernameNotFoundException {
        try {
            // Find the user by email
            UtilisateurEntity user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email,"404"));

            // Check the authentication method
            AuthentificationEntity auth = authenticationRepo.findByUserEmailAndProviderId(user.getEmail(),provider_id)
                    .orElseThrow(() -> new UsernameNotFoundException("Authentication method not found for user: " + email,"404"));

            String providerId = auth.getProviderId(); // Retrieve providerId

            // Convert roles to authorities
            Collection<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList());

            // Return custom UserDetails object
            return new CustomUserDetails(user.getEmail(), auth.getPassword(), authorities, providerId);
        } catch (UsernameNotFoundException e) {
            log.error("Error getting user details: ", e);
            throw new UsernameNotFoundException("Failed to get user details","404");
        }
    }

    public TokenResponse signinUser(SigninUserRequest signinUserRequest,AuthenticationManager authenticationManager,TokenService tokenService) {
        try {
            // Authenticate the user using the provided email and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinUserRequest.getEmail(), signinUserRequest.getPassword())
            );

            // Load user details
            CustomUserDetails userDetails = loadUserByUsername(signinUserRequest.getEmail());

            // Generate the access token and refresh token
            String token = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Prepare the response object
            TokenResponse response = new TokenResponse();
            response.setToken(token);

            // Retrieve the authentication record from the database
            Optional<AuthentificationEntity> authOpt = authenticationRepo.findByUserEmailAndProviderId(userDetails.getUsername(), userDetails.getProviderId());

            // If the user exists and the token is empty, save the refresh token to the database
            if (authOpt.isPresent()) {
                AuthentificationEntity auth = authOpt.get();
                if (auth.getRefreshToken() == null || auth.getRefreshToken().isEmpty()) {
                    tokenService.saveToken(userDetails.getUsername(), refreshToken, userDetails.getProviderId());
                }
            } else {
                // If user is not found in the database, you can save the token or handle this case differently
                tokenService.saveToken(userDetails.getUsername(), refreshToken, userDetails.getProviderId());
            }

            return response;

        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage(), "6000");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage(), "6000");
        }
    }

}
