package tn.temporise.application.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.application.exception.ConflictException;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Optional;
@Slf4j
@Transactional
@Service
public class RegistrationService {

    private final UserRepo userRepo;

    private final AuthRepo authRepo;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegistrationService(UserRepo userRepo, AuthRepo authRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Utilisateur user) {
        try {
            // Check if the email is already registered
            Optional<Utilisateur> existingUser = userRepo.findByEmail(user.getEmail());

            if (existingUser.isPresent()) {
                // If the user exists, check their authentication provider
                Optional<Authentification> existingAuth = authRepo.findByUserEmail(user.getEmail());
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

                // Save authentication details
                Authentification newAuth = new Authentification();
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