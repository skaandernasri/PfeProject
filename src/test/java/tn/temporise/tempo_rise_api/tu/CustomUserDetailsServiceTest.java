package tn.temporise.tempo_rise_api.tu;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.domain.service.CustomUserDetailsService;
import tn.temporise.infrastructure.adapter.repository.AuthRepo;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

import java.util.Collections;
import java.util.Optional;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthRepo authRepo;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        Utilisateur user = new Utilisateur();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRoles(Collections.singleton(Role.CLIENT));

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("CLIENT")));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
        assertEquals("User not found with email: " + email, exception.getMessage());
    }

}