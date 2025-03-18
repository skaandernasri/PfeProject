package tn.temporise.tempo_rise_api.tu;



import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tn.temporise.application.exception.LogoutException;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.application.service.LogoutService;
import tn.temporise.application.service.TokenService;
import tn.temporise.domain.model.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tn.temporise.infrastructure.security.utils.JwtRequestFilter;
import tn.temporise.infrastructure.security.utils.JwtUtil;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LogoutServiceTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenService tokenService;

    @Mock
    private JwtRequestFilter jwtRequestFilter;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testLogoutSuccess() {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true);

        // Simulate the JWT cookie
        Cookie jwtCookie = new Cookie("jwt", "valid-jwt-token");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});

        // Mocking JWT extraction
        when(jwtUtil.extractEmail("valid-jwt-token")).thenReturn("test@example.com");
        when(jwtUtil.extractProviderId("valid-jwt-token")).thenReturn("0");

        doNothing().when(tokenService).removeToken(any(CustomUserDetails.class));

        // Call logout method
        logoutService.logout(request, response);

        // Capture the argument passed to tokenService.removeToken
        ArgumentCaptor<CustomUserDetails> userDetailsCaptor = ArgumentCaptor.forClass(CustomUserDetails.class);
        verify(tokenService, times(1)).removeToken(userDetailsCaptor.capture());

        // Extract captured user details
        CustomUserDetails capturedUser = userDetailsCaptor.getValue();

        // Assertions
        assertEquals("test@example.com", capturedUser.getUsername());
        assertNotNull(capturedUser);
    }

    @Test
    void testLogoutFailure_UserNotAuthenticated() {
        // No authentication present in the security context
        SecurityContextHolder.clearContext();

        Exception exception = assertThrows(LogoutException.class, () -> {
            logoutService.logout(request, response);
        });

        assertEquals("User is not authenticated", exception.getMessage());

        // Ensure no logout operations are performed
        verify(tokenService, never()).removeToken(any());
        verify(jwtRequestFilter, never()).removeJwtCookie(response);
    }


}