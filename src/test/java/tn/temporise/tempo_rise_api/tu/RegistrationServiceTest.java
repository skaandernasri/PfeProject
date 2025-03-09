package tn.temporise.tempo_rise_api.tu;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.application.service.RegistrationService;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Optional;

public class RegistrationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthRepo authRepo;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        Utilisateur user = new Utilisateur();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        registrationService.register(user);

        verify(userRepo, times(1)).save(user);
        verify(authRepo, times(1)).save(any(Authentification.class));
    }

    @Test
    public void testRegister_EmailAlreadyRegistered() {
        Utilisateur user = new Utilisateur();
        user.setEmail("test@example.com");

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Email already registered", exception.getReason());
    }
}