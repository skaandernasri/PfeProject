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
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.UtilisateurModel;
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
    @Mock
    AuthMapper authMapper;
    @Mock
    RegMapper regMapper;
    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_NewUser() {
        // Mock user and repository behavior
        UtilisateurEntity userEntity = new UtilisateurEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        UtilisateurModel utilisateurModel=regMapper.entityToModel(userEntity);
        // Call the method under test
        registrationService.register(utilisateurModel);

        // Verify interactions
        verify(userRepo, times(1)).save(utilisateurModel);
        verify(authRepo, times(1)).save(any(Authentification.class));
    }

    @Test
    void testRegister_EmailAlreadyRegistered() {
        // Mock user and repository behavior
        UtilisateurEntity userEntity = new UtilisateurEntity();
        userEntity.setEmail("test@example.com");
        AuthentificationEntity auth = new AuthentificationEntity();
        auth.setProviderId("0"); // Set a non-null providerId
        UtilisateurModel utilisateurModel=regMapper.entityToModel(userEntity);

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateurModel));
        when(authRepo.findByUserEmail("test@example.com")).thenReturn(Optional.of(authMapper.entityToModel(auth)));

        // Assert exception
        assertThrows(ConflictException.class, () -> {
            registrationService.register(utilisateurModel);
        });
    }
}