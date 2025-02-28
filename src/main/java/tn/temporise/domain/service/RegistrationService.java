package tn.temporise.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepo userRepo,PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Utilisateur user) {
        // Check if the email is already registered
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");
        }
        // Save the User
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        // Create a new Authentification
        //Authentification authentification = new Authentification();
        //authentification.setMotDePasse(passwordEncoder.encode(request.getMotDePasse())); // Hash the password
        //authentification.setType(TypeAuthentification.EMAIL); // Default authentication type
        //authentification.setUser(user); // Link to the User

        // Save the Authentification
        //authentificationRepo.save(authentification);
    }
}