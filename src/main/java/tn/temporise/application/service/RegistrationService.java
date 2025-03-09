package tn.temporise.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.ConflictException;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Optional;
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class RegistrationService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AuthRepo authRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void register(UtilisateurEntity user) {
        try {
            // Check if the email is already registered
            Optional<UtilisateurEntity> existingUser = userRepo.findByEmail(user.getEmail());

            if (existingUser.isPresent()) {
                // If the user exists, check their authentication provider
                Optional<AuthentificationEntity> existingAuth = authRepo.findByUserEmail(user.getEmail());
                log.info("------------------------------------------Testing existingUser.isPresent() first if");
                if (existingAuth.isPresent() && existingAuth.get().getProviderId().equals("0")) {
                    log.info("---------------------------second if");
                    throw new ConflictException("Email already registered","409");

                } else if (existingAuth.isPresent() && existingAuth.get().getProviderId().equals("1")) {
                    log.info("---------------------------third if");
                    throw new ConflictException("Email already registered with Gmail or Facebook account","409");
                }
            } else {
                log.info("---------------------------went to else ");
                // If the user does not exist, save the new user and their roles
                userRepo.save(user);
                // Ensure the user entity has a valid ID and is persisted before saving AuthentificationEntity
                if (user.getId() == null) {
                    throw new IllegalStateException("User ID is not set after saving.");
                }

                // Save authentication details
                AuthentificationEntity newAuth = new AuthentificationEntity();
                newAuth.setUser(user);
                newAuth.setPassword(passwordEncoder.encode(user.getPassword()));
                newAuth.setType(TypeAuthentification.EMAIL);
                newAuth.setProviderId("0");
                authRepo.save(newAuth);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Failed to register user","500");
        }
    }
}