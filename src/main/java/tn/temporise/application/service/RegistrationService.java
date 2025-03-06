package tn.temporise.application.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepo authRepo;

    public RegistrationService(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authRepo = authRepo;
    }

    @Transactional
    public void register(Utilisateur user) throws ResponseStatusException {
        // Check if the email is already registered
        Optional<Utilisateur> existingUser = userRepo.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            // If the user exists, check their authentication provider
            Optional<Authentification> existingAuth = authRepo.findByUserEmail(user.getEmail());

            if (existingAuth.isPresent() && existingAuth.get().getProviderId().equals("0")) {
                // If the user is already registered with local authentication, throw an exception
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
            } else if (existingAuth.isPresent() && existingAuth.get().getProviderId().equals("1")) {
                // If the user exists but is registered with a different provider (e.g., Google),
                // add local authentication to their account
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered with Gmail or Facebook account");
            }
        } else {
            // If the user does not exist, save the new user and their roles using UserRepo
            userRepo.save(user);

            // Save authentication details
            Authentification newAuth = new Authentification();
            newAuth.setUser(user);
            newAuth.setPassword(passwordEncoder.encode(user.getPassword()));
            newAuth.setType(TypeAuthentification.EMAIL);
            newAuth.setProviderId("0");
            authRepo.save(newAuth);
        }
    }
}