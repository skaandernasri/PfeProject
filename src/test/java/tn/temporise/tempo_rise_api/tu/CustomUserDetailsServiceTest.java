package tn.temporise.tempo_rise_api.tu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.temporise.application.exception.UsernameNotFoundException;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.domain.model.*;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthRepo authRepo;

    @Mock
    private AuthMapper authMapper;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        UtilisateurModel user = new UtilisateurModel(1L, "test@example.com", Set.of(Role.ADMIN));
        AuthentificationEntity auth = new AuthentificationEntity();
        auth.setProviderId("0");
        auth.setPassword("password");
        Authentification authModel = new Authentification(0L, "password", "0");

        when(authMapper.entityToModel(auth)).thenReturn(authModel);
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(authRepo.findByUserEmail("test@example.com")).thenReturn(Optional.of(authModel));

        CustomUserDetails result = customUserDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
        assertEquals("password", result.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("test@example.com"));
    }
}