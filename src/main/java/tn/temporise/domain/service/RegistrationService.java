package tn.temporise.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.TypeAuthentification;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.infrastructure.adapter.repository.AuthRepo;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepo authRepo;

    public RegistrationService(UserRepo userRepo,PasswordEncoder passwordEncoder,AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authRepo=authRepo;
    }

    public void register(Utilisateur user) throws ResponseStatusException{
        // Check if the email is already registered
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");
        }
        // Save the User
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
         //Save the Authentification
        authRepo.save(new Authentification(passwordEncoder.encode(user.getPassword()),TypeAuthentification.EMAIL,user,"0"));
    }
}