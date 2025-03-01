package tn.temporise.domain.service;

import org.springframework.stereotype.Service;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.TypeAuthentification;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.infrastructure.adapter.repository.AuthRepo;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final AuthRepo authRepo;

    public UserService(UserRepo userRepo, AuthRepo authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }

    public Utilisateur findOrCreateUser(String email, Role defaultRole) {
        return userRepo.findByEmail(email)
                .orElseGet(() -> {
                    Utilisateur newUser = new Utilisateur();
                    newUser.setEmail(email);
                    newUser.setRoles(Collections.singleton(defaultRole));
                    return userRepo.save(newUser);
                });
    }

    public void saveAuthentication(Utilisateur user, String password, TypeAuthentification type) {
        if (authRepo.findByUserEmail(user.getEmail()).isEmpty()) {
            authRepo.save(new Authentification(user, password, type));
        }
    }
}
