package tn.temporise.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.ConflictException;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.PasswordException;
import tn.temporise.application.exception.RegistrationException;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Optional;
@Slf4j
//@Transactional
@Service
@RequiredArgsConstructor
public class RegistrationService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AuthRepo authRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final RegMapper regMapper;
    private final AuthMapper authMapper;

    @Transactional
    public void register(UtilisateurModel user) {
        try {
            // Convert the model to the entity for database operations
            UtilisateurEntity utilisateurEntity = regMapper.modelToEntity(user);  // Map the model to entity

            // Check if the email is already registered
            Optional<UtilisateurModel> existingUser = userRepo.findByEmail(user.email());  // Check the model for email

            if (existingUser.isPresent()) {
                // If the user exists, check their authentication provider
                Optional<Authentification> existingAuth = authRepo.findByUserEmail(user.email());
                log.info("Checking existing user and authentication provider...");
                if (existingAuth.isPresent() && existingAuth.get().providerId().equals("0")) {
                    log.info("Email already registered with email provider.");
                    throw new ConflictException("Email already registered", "409");
                } else if (existingAuth.isPresent()) {
                    log.info("Email already registered with Gmail or Facebook account.");
                    throw new ConflictException("Email already registered with Gmail or Facebook account", "409");
                }
            }

            // Validate password
            if (user.password().length() < 8 || !user.password().matches(".*[A-Z].*") || user.password().chars().noneMatch(Character::isDigit)) {
                throw new PasswordException("Weak password: at least 8 characters, contains an uppercase letter, and numbers");
            }

            // Save the UtilisateurEntity to the database
            UtilisateurModel utilisateurModel = userRepo.save(user);  // Save the converted entity

            // Create and save the authentication entity
            Authentification authModel = new Authentification(utilisateurModel, passwordEncoder.encode(user.password()),TypeAuthentification.EMAIL,"0");  // Create model with user data
//            AuthentificationEntity newAuth = new AuthentificationEntity();
//            newAuth.setUser(savedUtilisateurEntity);  // Link to the saved entity
//            newAuth.setPassword(passwordEncoder.encode(user.password()));  // Encode the password from the model
//            newAuth.setType(TypeAuthentification.EMAIL);
//            newAuth.setProviderId("0");

            // Save the authentication details to the repository
            authRepo.save(authModel);  // Save the converted authentification model

        } catch (Exception e) {
            throw new RegistrationException("Failed to register user: " + e.getMessage());
        }
    }

}