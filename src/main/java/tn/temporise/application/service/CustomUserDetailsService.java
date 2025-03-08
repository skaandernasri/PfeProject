package tn.temporise.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.NonLocalProviderException;
import tn.temporise.application.exception.UsernameNotFoundException;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final AuthRepo authentificationRepo;
    private final RegistrationService registrationService;

    @Autowired
    public CustomUserDetailsService(@Lazy AuthRepo authentificationRepo, UserRepo userRepo, @Lazy RegistrationService registrationService) {
        this.authentificationRepo = authentificationRepo;
        this.userRepo = userRepo;
        this.registrationService = registrationService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            // Find the user by email
            Utilisateur user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email,"404"));

            // Check if the user has a non-local provider (e.g., Google or Facebook)
            Authentification auth = authentificationRepo.findByUserEmail(user.getEmail())
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
        } catch (UsernameNotFoundException e) {
            log.error("Error loading user by username: ", e);
            throw new UsernameNotFoundException("Failed to load user by username","404");
        }
    }

    public CustomUserDetails getUserDetails(String email) throws UsernameNotFoundException {
        try {
            // Find the user by email
            Utilisateur user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email,"404"));

            // Check the authentication method
            Authentification auth = authentificationRepo.findByUserEmail(user.getEmail())
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

    public void saveToken(String email, String token, String providerId) {
        try {
            Optional<Authentification> authentification = authentificationRepo.findByUserEmailAndProviderId(email, providerId);
            if (authentification.isPresent()) {
                authentification.get().setToken(token);
                authentificationRepo.save(authentification.get());
            }
        } catch (InternalServerErrorException e) {
            log.error("Error saving token: ", e);
            throw new InternalServerErrorException("Failed to save token","500");
        }
    }

    public void removeToken(CustomUserDetails customUserDetails) {
        try {
            String providerId = customUserDetails.getProviderId();
            String email = customUserDetails.getUsername();
            Optional<Authentification> authentification = authentificationRepo.findByUserEmailAndProviderId(email, providerId);
            if (authentification.isPresent()) {
                authentification.get().setToken(null);
                authentificationRepo.save(authentification.get());
            }
            log.info("Token removed for user: " + email);
        } catch (InternalServerErrorException e) {
            log.error("Error removing token: ", e);
            throw new InternalServerErrorException("Failed to remove token","500");
        }
    }
}
