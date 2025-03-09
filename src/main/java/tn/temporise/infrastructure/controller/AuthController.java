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
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.exception.*;
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
    private final LogoutService logoutService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Autowired
    public AuthController(AuthMapper authMapper, AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService, JwtUtil jwtUtil,
                          RegMapper regMapper, RegistrationService registrationService,
                          LogoutService logoutService, HttpServletRequest request,
                          HttpServletResponse response) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.regMapper = regMapper;
        this.registrationService = registrationService;
        this.authMapper = authMapper;
        this.logoutService = logoutService;
        this.request = request;
        this.response = response;
    }

    @Override
    public ResponseEntity<Response> _logoutUser() {
        try {
            logoutService.logout(request, response);
            Response responseBody = new Response();
            responseBody.setCode("200");
            responseBody.setMessage("Déconnecter avec succés");
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            log.error("Error during logout: ", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<TokenResponse> _refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();
            String email = jwtUtil.extractEmail(refreshToken);
            CustomUserDetails userDetails = userDetailsService.getUserDetails(email);

            if (!jwtUtil.validateToken(refreshToken, userDetails)) {
                throw new UnauthorizedException("Token de rafraîchissement invalide","401");
            }

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);
            userDetailsService.saveToken(email, newAccessToken, (String) jwtUtil.extractClaims(newAccessToken).get("provider_id"));

            TokenResponse response = new TokenResponse();
            response.setToken(newAccessToken);
            response.setRefreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Token refresh failed: ", e);
            throw new UnauthorizedException("Échec du rafraîchissement du token","401");
        }
    }

    @Override
    public ResponseEntity<TokenResponse> _signinFacebook(SigninFacebookRequest signinFacebookRequest) {
        // Implementation for Facebook sign-in
        return null;
    }

    @Override
    public ResponseEntity<TokenResponse> _signinGoogle(SigninGoogleRequest signinGoogleRequest) {
        String idToken = signinGoogleRequest.getIdToken();
        if (idToken == null || idToken.isEmpty()) {
            throw new BadRequestException("Token Google manquant","400");
        }

        try {
            JWT jwt = JWTParser.parse(idToken);
            JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
            String email = claimsSet.getStringClaim("email");
            CustomUserDetails userDetails = userDetailsService.getUserDetails(email);

            String accessToken = jwtUtil.generateAccessToken(userDetails);
            TokenResponse token = new TokenResponse();
            token.setToken(accessToken);
            return ResponseEntity.ok(token);
        } catch (ParseException e) {
            log.error("Failed to parse Google ID token: ", e);
            throw new UnauthorizedException("Échec de l'authentification Google","401");
        } catch (Exception e) {
            log.error("Unexpected error during Google sign-in: ", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<TokenResponse> _signinUser(SigninUserRequest signinUserRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinUserRequest.getEmail(), signinUserRequest.getPassword())
            );
            Utilisateur user = authMapper.toEntity(signinUserRequest);
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            String token = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            TokenResponse response = new TokenResponse();
            response.setToken(token);
            response.setRefreshToken(refreshToken);
            userDetailsService.saveToken(userDetails.getUsername(), token, "0");

            return ResponseEntity.ok(response);

        }catch (AuthenticationException e){
            throw new BadCredentialsException(e.getMessage(),"6000");
        }
        catch (Exception e) {
            log.error("Authentication failed: ", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<Response> _signupUser(SignupUserRequest signupUserRequest) {
        try {
            Utilisateur user = regMapper.toEntity(signupUserRequest);
            registrationService.register(user);
            log.info("User registered successfully");
            Response response=new Response();
            response.setCode("201");
            response.setMessage("utilisateur crée avec succés");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
        catch (Exception e){
            throw e;
        }

    }
}