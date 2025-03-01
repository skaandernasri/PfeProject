package tn.temporise.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.TypeAuthentification;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.infrastructure.adapter.repository.AuthRepo;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;
    private final AuthRepo authRepo;
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    public CustomOAuth2UserService(UserRepo userRepo, AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");

        logger.info("Loading OAuth2 user with email: {}", email);

        Utilisateur user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    logger.info("Creating new user for email: {}", email);
                    Utilisateur newUser = new Utilisateur();
                    newUser.setEmail(email);
                    newUser.setRoles(Collections.singleton(Role.CLIENT));
                    return userRepo.save(newUser);
                });

        if (authRepo.findByUserEmail(email).isEmpty()) {
            logger.info("Saving authentication for user: {}", email);
            authRepo.save(new Authentification(user, "1", TypeAuthentification.GOOGLE));
        }

        return oauth2User;
    }
}