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
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.temporise.application.exception.ConflictException;
import tn.temporise.application.exception.RegistrationException;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.application.service.RegistrationService;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

class RegistrationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthRepo authRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private RegMapper regMapper;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_NewUser() {
        // Given: A new user model
        UtilisateurModel utilisateurModel = new UtilisateurModel(null,"test","test@example.com","Password123.", Set.of(Role.ADMIN));
        UtilisateurEntity userEntity = new UtilisateurEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("Password123.");
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Password123.")).thenReturn("encodedPassword123");

        when(regMapper.modelToEntity(utilisateurModel)).thenReturn(userEntity);
        when(regMapper.entityToModel(userEntity)).thenReturn(utilisateurModel);
        registrationService.register(utilisateurModel);

        verify(userRepo, times(1)).save(any(UtilisateurModel.class));
        verify(authRepo, times(1)).save(any(Authentification.class)); // Ensure authRepo is saving the right object
    }

    @Test
    void testRegister_EmailAlreadyRegistered() {
        UtilisateurEntity userEntity = new UtilisateurEntity();
        userEntity.setEmail("test@example.com");
        Authentification auth = new Authentification(null,null,"0",null,null,null);
        UtilisateurModel utilisateurModel=new UtilisateurModel(null,"test@example.com");

        when(regMapper.entityToModel(userEntity)).thenReturn(utilisateurModel);
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateurModel));
        when(authRepo.findByUserEmail("test@example.com")).thenReturn(Optional.of(auth));

        assertThrows(RegistrationException.class, () -> registrationService.register(utilisateurModel));
    }
}