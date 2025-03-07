package tn.temporise.infrastructure.controller;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.application.service.LogoutService;
import tn.temporise.application.service.RegistrationService;
import tn.temporise.domain.model.*;
import tn.temporise.infrastructure.api.AuthentificationApi;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.text.ParseException;

@Slf4j
    @RestController
    public class AuthController implements AuthentificationApi {

        private final AuthenticationManager authenticationManager;
        private final CustomUserDetailsService userDetailsService;
        private final JwtUtil jwtUtil;
        private final RegMapper regMapper;
        private final RegistrationService registrationService;
        private final AuthMapper authMapper;
        @Autowired
        LogoutService logoutService;
         @Autowired
         private HttpServletRequest request;
         @Autowired
        private HttpServletResponse response;

        @Autowired
        public AuthController(AuthMapper authMapper,AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, RegMapper regMapper, RegistrationService registrationService) {
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
            this.jwtUtil = jwtUtil;
            this.regMapper = regMapper;
            this.registrationService = registrationService;
            this.authMapper=authMapper;
        }

    @Override
    public ResponseEntity<Response> _logoutUser() throws Exception {
        try {
            // Call the logout service with the injected request and response
            logoutService.logout(request, response);

            // Create and return a successful response
            Response responseBody = new Response();
            responseBody.setCode("200");
            responseBody.setMessage("Déconnecter avec succés");
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            log.error("Error during logout: ", e);

            // Create and return an error response
            Response errorResponse = new Response();
            errorResponse.setCode("500");
            errorResponse.setMessage("Erreur serveur interne");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
//            // Get the current authentication context
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication != null) {
//                // Extract the username or email from the authentication object
//                String username = authentication.getName();
//                // Invalidate the token (e.g., remove it from the database or cache)
//
//                CustomUserDetails customUserDetails=userDetailsService.getUserDetails(username);
//                userDetailsService.removeToken(customUserDetails);
//                // Clear the security context
//                SecurityContextHolder.clearContext();
//
//                log.info("User logged out successfully: " + username);
//            } else {
//                log.warn("No user is currently authenticated.");
//            }
//            Response response=new Response();
//            response.setCode("201");
//            response.setMessage("Déconnecter avec succés");
//            return ResponseEntity.ok().body(response);


        }

        @Override
        public ResponseEntity<TokenResponse> _refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
            try {
                String refreshToken = refreshTokenRequest.getRefreshToken();
                String email = jwtUtil.extractEmail(refreshToken);
                CustomUserDetails userDetails = userDetailsService.getUserDetails(email);

                if (!jwtUtil.validateToken(refreshToken, userDetails)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                // Generate new access and refresh tokens
                String newAccessToken = jwtUtil.generateAccessToken(userDetails);
                log.info("-------------------------------------"+ jwtUtil.extractClaims(newAccessToken).get("provider_id"));
                userDetailsService.saveToken(email,newAccessToken,(String) jwtUtil.extractClaims(newAccessToken).get("provider_id"));
                TokenResponse response = new TokenResponse();
                response.setToken(newAccessToken);
                response.setRefreshToken(refreshToken);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                log.error("Token refresh failed: ", e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        @Override
        public ResponseEntity<TokenResponse> _signinFacebook(SigninFacebookRequest signinFacebookRequest) throws Exception {
            return null;
        }

        @Override
        public ResponseEntity<TokenResponse> _signinGoogle(SigninGoogleRequest signinGoogleRequest) throws Exception {
            String idToken = signinGoogleRequest.getIdToken();
            if (idToken == null || idToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TokenResponse());
            }

            try {
                // Parse the Google ID token
                JWT jwt = JWTParser.parse(idToken);
                JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();

                // Extract user information from the ID token
                String email = claimsSet.getStringClaim("email");
                // Load or create the user in your system
                CustomUserDetails userDetails = userDetailsService.getUserDetails(email);
                // Generate JWT tokens
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                //String refreshToken = jwtUtil.generateRefreshToken(userDetails);

                TokenResponse token = new TokenResponse();
                token.setToken(accessToken);
                //token.setRefreshToken(refreshToken);
                return ResponseEntity.ok(token);
            } catch (ParseException e) {
                log.error("Failed to parse Google ID token: ", e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse());
            } catch (Exception e) {
                log.error("Unexpected error during Google sign-in: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TokenResponse());
            }
        }

        @Override
        public ResponseEntity<TokenResponse> _signinUser(SigninUserRequest signinUserRequest) throws Exception {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(signinUserRequest.getEmail(), signinUserRequest.getPassword())
                );
                Utilisateur user=authMapper.toEntity(signinUserRequest);
                CustomUserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                String token = jwtUtil.generateAccessToken(userDetails);
                String refreshToken= jwtUtil.generateRefreshToken(userDetails);
                TokenResponse response = new TokenResponse();
                response.setToken(token);
                response.setRefreshToken(refreshToken);
                userDetailsService.saveToken(userDetails.getUsername(),token,"0");
                //log.info("user authorities -----------------------------:"+jwtUtil.extractClaims(token));
                //log.info("user authorities -----------------------------:"+userDetails.getAuthorities());

                return ResponseEntity.ok(response);
            } catch (Exception e) {
                log.error("Authentication failed: ", e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse());
            }
        }


        @Override
        public ResponseEntity<Response> _signupUser(SignupUserRequest signupUserRequest) {
            try {
                Utilisateur user = regMapper.toEntity(signupUserRequest);
                registrationService.register(user);

                log.info("User registered successfully");
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (ResponseStatusException e) {
                log.error("ResponseStatusException: ", e);
                return ResponseEntity.status(e.getStatusCode()).build();
            } catch (Exception e) {
                log.error("Unexpected error occurred: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }