//package tn.temporise.tempo_rise_api.tu;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import tn.temporise.application.service.CustomOAuth2UserService;
//import tn.temporise.domain.port.AuthRepo;
//import tn.temporise.domain.port.UserRepo;
//import tn.temporise.infrastructure.persistence.entity.Authentification;
//import tn.temporise.infrastructure.persistence.entity.Utilisateur;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//public class CustomOAuth2UserServiceTest {
//    @Mock
//    private UserRepo userRepo;
//
//    @Mock
//    private AuthRepo authRepo;
//    @Mock
//    private DefaultOAuth2UserService defaultOAuth2UserService;
//
//    @InjectMocks
//    private CustomOAuth2UserService customOAuth2UserService;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testLoadUser() {
//        // Mock OAuth2UserRequest and its dependencies
//        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
//        ClientRegistration clientRegistration = mock(ClientRegistration.class);
//        ClientRegistration.ProviderDetails providerDetails = mock(ClientRegistration.ProviderDetails.class);
//        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = mock(ClientRegistration.ProviderDetails.UserInfoEndpoint.class);
//
//        // Mock the OAuth2UserRequest to return a valid ClientRegistration
//        when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
//        when(clientRegistration.getProviderDetails()).thenReturn(providerDetails);
//        when(clientRegistration.getRegistrationId()).thenReturn("google"); // Set the registration ID
//
//        // Mock the ProviderDetails to return a valid UserInfoEndpoint
//        when(providerDetails.getUserInfoEndpoint()).thenReturn(userInfoEndpoint);
//        when(userInfoEndpoint.getUri()).thenReturn("https://example.com/userinfo"); // Set the user info endpoint URI
//
//        // Mock the OAuth2User
//        OAuth2User oauth2User = mock(OAuth2User.class);
//        when(oauth2User.getAttribute("email")).thenReturn("test@example.com");
//
//        // Mock the DefaultOAuth2UserService to return the mocked OAuth2User
//        DefaultOAuth2UserService defaultOAuth2UserService = mock(DefaultOAuth2UserService.class);
//        when(defaultOAuth2UserService.loadUser(userRequest)).thenReturn(oauth2User);
//
//        // Mock userRepo and authRepo behavior
//        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
//        when(userRepo.save(any(Utilisateur.class))).thenReturn(new Utilisateur());
//        when(authRepo.findByUserEmailAndProviderId("test@example.com", "1")).thenReturn(Optional.empty());
//
//        // Call the method under test
//        OAuth2User result = customOAuth2UserService.loadUser(userRequest);
//
//        // Assertions
//        assertNotNull(result);
//        verify(userRepo, times(1)).save(any(Utilisateur.class));
//        verify(authRepo, times(1)).save(any(Authentification.class));
//    }
//}
