package tn.temporise.infrastructure.controller;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.application.service.RegistrationService;
import tn.temporise.domain.model.*;
import tn.temporise.infrastructure.api.AuthentificationApi;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;

    @Slf4j
    @RestController
    public class AuthController implements AuthentificationApi {

        private final AuthenticationManager authenticationManager;
        private final CustomUserDetailsService userDetailsService;
        private final JwtUtil jwtUtil;
        private final RegMapper regMapper;
        private final RegistrationService registrationService;
        @Value("${spring.security.oauth2.client.registration.google.client-id}")
        private String googleClientId;
        @Autowired
        public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, RegMapper regMapper, RegistrationService registrationService) {
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
            this.jwtUtil = jwtUtil;
            this.regMapper = regMapper;
            this.registrationService = registrationService;
        }

        @Override
        public ResponseEntity<Void> _logoutUser() throws Exception {
            log.info("User logged out successfully");
            return ResponseEntity.ok().build();
        }

        @Override
        public ResponseEntity<RefreshTokenResponse> _refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
            try {
                String refreshToken = refreshTokenRequest.getRefreshToken();
                String email = jwtUtil.extractEmail(refreshToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (!jwtUtil.validateToken(refreshToken, userDetails)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                // Generate new access and refresh tokens
                String newAccessToken = jwtUtil.generateAccessToken(userDetails);

                RefreshTokenResponse response = new RefreshTokenResponse();
                response.setRefreshToken(newAccessToken);
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

                // Create an OidcIdToken
                OidcIdToken oidcIdToken = new OidcIdToken(
                        idToken,
                        Instant.now(),
                        Instant.ofEpochSecond(claimsSet.getExpirationTime().getTime()),
                        claimsSet.getClaims()
                );

                // Create an OidcUserInfo
                OidcUserInfo oidcUserInfo = new OidcUserInfo(claimsSet.getClaims());

                // Create a DefaultOidcUser
                DefaultOidcUser oidcUser = new DefaultOidcUser(
                        Collections.emptyList(), // Authorities (empty for now)
                        oidcIdToken,
                        oidcUserInfo,
                        "email" // Name attribute key
                );

                // Load or create the user in your system
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Generate JWT tokens
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                String refreshToken = jwtUtil.generateRefreshToken(userDetails);

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

                UserDetails userDetails = userDetailsService.loadUserByUsername(signinUserRequest.getEmail());
                String token = jwtUtil.generateAccessToken(userDetails);
                TokenResponse response = new TokenResponse();
                response.setToken(token);
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
        public ResponseEntity<Void> _signupUser(SignupUserRequest signupUserRequest) {
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