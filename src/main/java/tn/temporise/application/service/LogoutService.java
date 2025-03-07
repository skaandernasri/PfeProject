package tn.temporise.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.BadRequestException;
import tn.temporise.application.exception.LogoutException;
import tn.temporise.application.exception.UnauthorizedException;
import tn.temporise.domain.model.CustomUserDetails;
@Slf4j
@Service
public class LogoutService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get the current authentication context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                // Extract the username or email from the authentication object
                String username = authentication.getName();
                CustomUserDetails customUserDetails = userDetailsService.getUserDetails(username);

                // Logout the user by clearing their authentication info from the SecurityContext
                new SecurityContextLogoutHandler().logout(request, response, authentication);

                // Get the Authorization header from the request to retrieve the JWT token
                String token = request.getHeader("Authorization");

                // Check if the token is in "Bearer <token>" format, then remove "Bearer " prefix
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7); // Extract the actual token
                } else {
                    throw new BadRequestException("Invalid token format","4000");
                }

                // Remove the token from the database
                userDetailsService.removeToken(customUserDetails);

                log.info("User logged out successfully: " + username);
            } else {
                throw new UnauthorizedException("User is not authenticated","401");
            }
        } catch (Exception e) {
            log.error("Error during logout: ", e);
            throw new LogoutException("Failed to logout user","5000");
        }
    }
}