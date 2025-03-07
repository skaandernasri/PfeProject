package tn.temporise.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.NonLocalProviderException;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
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
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
        return new CustomUserDetails(
                user.getEmail(),
                auth.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList()),
                auth.getProviderId()
        );
    }
    public CustomUserDetails getUserDetails(String email) throws UsernameNotFoundException{
        // Find the user by email
        Utilisateur user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Check the authentication method
        Authentification auth = authentificationRepo.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Authentication method not found for user: " + email));

        String providerId = auth.getProviderId(); // Retrieve providerId

        // Convert roles to authorities
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        // Return custom UserDetails object
        return new CustomUserDetails(user.getEmail(), auth.getPassword(), authorities, providerId);
    }
        public void saveToken(String email,String token,String provider_id){
        Optional<Authentification> authentification= authentificationRepo.findByUserEmailAndProviderId(email,provider_id);
        if (authentification.isPresent()){
                   authentification.get().setToken(token);
                   authentificationRepo.save(authentification.get());
               }
        }
    public void removeToken(CustomUserDetails customUserDetails) {
        // Remove the token from the database or cache
        // Example: tokenRepository.deleteByUsername(username);
        String provider_id= customUserDetails.getProviderId();
        String email= customUserDetails.getUsername();
        Optional<Authentification> authentification=authentificationRepo.findByUserEmailAndProviderId(email,provider_id);
        if(authentification.isPresent()){
            authentification.get().setToken(null);
            authentificationRepo.save(authentification.get());
        }
        log.info("Token removed for user: "+email);
    }
}

