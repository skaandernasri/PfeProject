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
            // Check if the email is already registered
            UtilisateurEntity utilisateurEntity = regMapper.modelToEntity(user);
            Optional<UtilisateurModel> existingUser = userRepo.findByEmail(utilisateurEntity.getEmail());

            if (existingUser.isPresent()) {
                // If the user exists, check their authentication provider
                Optional<Authentification> existingAuth = authRepo.findByUserEmail(utilisateurEntity.getEmail());
                log.info("------------------------------------------Testing existingUser.isPresent() first if");
                if (existingAuth.isPresent() && existingAuth.get().providerId().equals("0")) {
                    log.info("---------------------------second if");
                    throw new ConflictException("Email already registered", "409");
                } else if (existingAuth.isPresent()) {
                    log.info("---------------------------third if");
                    throw new ConflictException("Email already registered with Gmail or Facebook account", "409");
                }
            }

            // Validate password
            if (user.password().length() < 8 || !user.password().matches(".*[A-Z].*") || user.password().chars().noneMatch(Character::isDigit)) {
                throw new PasswordException("Weak password: at least 8 characters, contains an uppercase letter, and numbers");
            }

            // Save the UtilisateurEntity first
            UtilisateurModel savedUtilisateur = userRepo.save(user);

            // Save authentication details
            AuthentificationEntity newAuth = new AuthentificationEntity();
            newAuth.setUser(regMapper.modelToEntity(savedUtilisateur)); // Use the saved entity
            newAuth.setPassword(passwordEncoder.encode(utilisateurEntity.getPassword()));
            newAuth.setType(TypeAuthentification.EMAIL);
            newAuth.setProviderId("0");
            authRepo.save(authMapper.entityToModel(newAuth));

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Failed to register user", "500");
        }
    }
}