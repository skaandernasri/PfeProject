package tn.temporise.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.BusinessException;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.domain.model.Response;
@Service
public class LogoutService {
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Transactional
    // Ensures that the logout process, including any database changes, is handled within a single transaction
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Get the current authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Extract the username or email from the authentication object
            String username = authentication.getName();
            CustomUserDetails customUserDetails=userDetailsService.getUserDetails(username);
            // Logout the user by clearing their authentication info from the SecurityContext
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            // Get the Authorization header from the request to retrieve the JWT token
            String token = request.getHeader("Authorization");

            // Check if the token is in "Bearer <token>" format, then remove "Bearer " prefix to extract only the token value
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Extract the actual token by removing the "Bearer " prefix
            } else {
                // If no valid token is provided, throw a custom exception indicating the refresh token does not exist
                throw new BusinessException("token does not exist");
            }

            // Search the database for the provided token in the refreshTokenRepository
            userDetailsService.removeToken(customUserDetails);

            // Create and return a successful response
            Response responseBody = new Response();
            responseBody.setCode("200");
            responseBody.setMessage("Déconnecter avec succés");
            ResponseEntity.ok().body(responseBody);
        } else {
            // Throw an exception if the user was not authenticated in the first place
            throw new BusinessException("User does not exist");
        }
    }
}
