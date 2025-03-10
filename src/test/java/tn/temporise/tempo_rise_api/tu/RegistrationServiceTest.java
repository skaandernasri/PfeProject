package tn.temporise.tempo_rise_api.tu;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.temporise.application.exception.ConflictException;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.application.service.RegistrationService;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;

import java.util.Optional;

class RegistrationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthRepo authRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_NewUser() {
        // Mock user and repository behavior
        UtilisateurEntity user = new UtilisateurEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Call the method under test
        registrationService.register(user);

        // Verify interactions
        verify(userRepo, times(1)).save(user);
        verify(authRepo, times(1)).save(any(AuthentificationEntity.class));
    }

    @Test
    void testRegister_EmailAlreadyRegistered() {
        // Mock user and repository behavior
        UtilisateurEntity user = new UtilisateurEntity();
        user.setEmail("test@example.com");
        AuthentificationEntity auth = new AuthentificationEntity();
        auth.setProviderId("0"); // Set a non-null providerId

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(authRepo.findByUserEmail("test@example.com")).thenReturn(Optional.of(auth));

        // Assert exception
        assertThrows(ConflictException.class, () -> {
            registrationService.register(user);
        });
    }
}