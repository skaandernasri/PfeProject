package tn.temporise.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepo userRepo;
    private final AuthRepo authRepo;
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    public CustomOAuth2UserService(UserRepo userRepo, AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oauth2User = super.loadUser(userRequest);

            // Extract the email from the OAuth2 user attributes
            String email = oauth2User.getAttribute("email");
            String idToken = userRequest.getAccessToken().getTokenValue();
            logger.info("Loading OAuth2 user with email: {}", email);

            // Find the user by email, or create a new one if not found
            Utilisateur user = userRepo.findByEmail(email)
                    .orElseGet(() -> {
                        logger.info("Creating new user for email: {}", email);
                        Utilisateur newUser = new Utilisateur();
                        newUser.setEmail(email);
                        newUser.setRoles(Collections.singleton(Role.CLIENT)); // Assign a default role
                        return userRepo.save(newUser); // Save the new user to the database
                    });

            // Check if the user already has an authentication record for this provider
            Optional<Authentification> existingAuth = authRepo.findByUserEmailAndProviderId(email, "1");
            if (existingAuth.isPresent()) {
                // Update the existing authentication record with the new token
                Authentification auth = existingAuth.get();
                auth.setToken(idToken);
                authRepo.save(auth);
            } else {
                // Create a new authentication record
                Authentification auth = new Authentification();
                auth.setUser(user); // Link the authentication to the user
                auth.setProviderId("1"); // Google provider ID
                auth.setType(TypeAuthentification.GOOGLE);
                auth.setToken(idToken); // Save the Google ID token
                authRepo.save(auth); // Save the authentication entry to the database
            }

            // Return the OAuth2 user details
            return oauth2User;
        } catch (OAuth2AuthenticationException e) {
            logger.error("Error during OAuth2 user loading: ", e);
            throw new OAuth2AuthenticationException("Failed to load OAuth2 user");
        }
    }
}