package tn.temporise.domain.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.infrastructure.adapter.repository.AuthRepo;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo  userRepo;
    private final AuthRepo authentificationRepo;

    public CustomUserDetailsService(AuthRepo authentificationRepo,UserRepo userRepo) {
        this.authentificationRepo = authentificationRepo;
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = userRepo.findByEmail(email)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("User not found with email: " + email);
                });
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())
        );
    }
    public UserDetails loadOrCreateOAuth2User(String email) {
        // Check if the user already exists in the database
        Utilisateur user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    // Create a new user if they don't exist
                    Utilisateur newUser = new Utilisateur();
                    newUser.setEmail(email);
                    newUser.setRoles(Collections.singleton(Role.CLIENT)); // Assign a default role
                    return userRepo.save(newUser);
                });
        // Return a UserDetails object without a password
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "",
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())
        );
    }
}

