package tn.temporise.application.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.NonLocalProviderException;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo  userRepo;
    private final AuthRepo authentificationRepo;
    private final RegistrationService registrationService; // Ajoute ceci si nécessaire


    public CustomUserDetailsService(@Lazy AuthRepo authentificationRepo,UserRepo userRepo,@Lazy    RegistrationService registrationService) {
        this.authentificationRepo = authentificationRepo;
        this.userRepo=userRepo;
        this.registrationService = registrationService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find the user by email
        Utilisateur user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Check if the user has a non-local provider (e.g., Google or Facebook)
        Authentification auth = authentificationRepo.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Authentication method not found for user: " + email));

        if (!auth.getProviderId().equals("0")) {
            // If the provider is not local, throw an exception or handle the case
            throw new NonLocalProviderException("User is registered with a non-local provider. Please use the appropriate login method.");        }

        // If the provider is local, return the UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                auth.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())
        );
    }
//    public UserDetails loadOrCreateOAuth2User(String email) {
//        Utilisateur user = userRepo.findByEmail(email)
//                .orElseGet(() -> {
//                    Utilisateur newUser = new Utilisateur();
//                    newUser.setEmail(email);
//                    newUser.setRoles(Collections.singleton(Role.CLIENT)); // Assign a default role
//                    return userRepo.save(newUser);
//                });
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                "",
//                user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.name()))
//                        .collect(Collectors.toList())
//        );
//    }
        public void saveToken(String email,String token,String provider_id){
        Optional<Authentification> authentification= authentificationRepo.findByUserEmailAndProviderId(email,provider_id);
            if (authentification.get().getUser() == null || authentification.get().getUser().getId() == null) {
                throw new IllegalArgumentException("User in Authentification object cannot be null");
            }

            if (authentification.isPresent()){
                   authentification.get().setToken(token);
                   authentificationRepo.save(authentification.get());
               }

        }
}

