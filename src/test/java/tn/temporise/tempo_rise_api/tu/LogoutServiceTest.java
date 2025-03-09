package tn.temporise.tempo_rise_api.tu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.application.service.LogoutService;
import tn.temporise.application.service.TokenService;
import tn.temporise.domain.model.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

import static org.mockito.Mockito.*;

class LogoutServiceTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogout() {
        // Mock authentication and user details
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true); // Ensure authentication is marked as authenticated
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");

        // Create a user locally
        CustomUserDetails userDetails = new CustomUserDetails(
                "test@example.com", // username/email
                "password", // password
                Collections.emptyList(), // authorities/roles
                "0" // provider ID
        );

        // Mock user details service behavior
        when(userDetailsService.getUserDetails("test@example.com","0")).thenReturn(userDetails);
        doNothing().when(tokenService).removeToken(userDetails); // Mock removeToken to do nothing

        // Call the method under test
        logoutService.logout(request, response);

        // Verify interactions
        verify(tokenService, times(1)).removeToken(userDetails);
    }
}