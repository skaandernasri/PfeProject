package tn.temporise.tempo_rise_api.tu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.temporise.application.exception.UsernameNotFoundException;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthRepo authRepo;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private AuthMapper authMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mock user and authentication
        UtilisateurModel user = new UtilisateurModel(1L,"test@example.com", Set.of(Role.ADMIN));
        AuthentificationEntity auth = new AuthentificationEntity();
        auth.setProviderId("0");
        auth.setPassword("password");
        Authentification authModel = new Authentification(0L, "password","0");
        when(authMapper.entityToModel(auth)).thenReturn(authModel);

        // Mock repository behavior
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(authRepo.findByUserEmail("test@example.com")).thenReturn(Optional.of(authModel));

        // Call the method under test
        CustomUserDetails result = customUserDetailsService.loadUserByUsername("test@example.com");

        // Assertions
        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
        assertEquals("password", result.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock repository behavior
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Assert exception
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("test@example.com");
        });
    }
}