package tn.temporise.application.service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.BadRequestException;
import tn.temporise.application.exception.UnauthorizedException;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.domain.model.TokenResponse;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.text.ParseException;
import java.util.Collections;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AuthRepo authRepo;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oauth2User = super.loadUser(userRequest);

            // Extract the email from the OAuth2 user attributes
            String email = oauth2User.getAttribute("email");
            String idToken = userRequest.getAccessToken().getTokenValue();
            log.info("Loading OAuth2 user with email: {}", email);

            // Find the user by email, or create a new one if not found
            UtilisateurEntity user = userRepo.findByEmail(email)
                    .orElseGet(() -> {
                        log.info("Creating new user for email: {}", email);
                        UtilisateurEntity newUser = new UtilisateurEntity();
                        newUser.setEmail(email);
                        newUser.setRoles(Collections.singleton(Role.CLIENT)); // Assign a default role
                          return userRepo.save(newUser); // Save the new user to the database
                    });

            // Check if the user already has an authentication record for this provider
            Optional<AuthentificationEntity> existingAuth = authRepo.findByUserEmailAndProviderId(email, "1");
            if (existingAuth.isEmpty()) {
                // Create a new authentication record
                AuthentificationEntity auth = new AuthentificationEntity();
                auth.setUser(user); // Link the authentication to the user
                auth.setProviderId("1"); // Google provider ID
                auth.setType(TypeAuthentification.GOOGLE);
                auth.setToken(jwtUtil.generateRefreshToken(new CustomUserDetails(email,"1"))); // Save the Google ID token
                authRepo.save(auth); // Save the authentication entry to the database
            }
            // Return the OAuth2 user details
            return oauth2User;
        } catch (OAuth2AuthenticationException e) {
            log.error("Error during OAuth2 user loading: ", e);
            throw new OAuth2AuthenticationException("Failed to load OAuth2 user");
        }
    }
    public TokenResponse signinGoogle(String idToken) {
        if (idToken == null || idToken.isEmpty()) {
            throw new BadRequestException("Token Google manquant", "400");
        }

        try {
            JWT jwt = JWTParser.parse(idToken);
            JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
            String email = claimsSet.getStringClaim("email");
            CustomUserDetails userDetails = userDetailsService.getUserDetails(email,"1");
            String accessToken = jwtUtil.generateAccessToken(userDetails);

            TokenResponse token = new TokenResponse();
            token.setToken(accessToken);
            return token;
        } catch (ParseException e) {
            throw new UnauthorizedException("Échec de l'authentification Google", "401");
        } catch (Exception e) {
            throw new UnauthorizedException("Unexpected error during Google sign-in", "401");
        }
    }
}