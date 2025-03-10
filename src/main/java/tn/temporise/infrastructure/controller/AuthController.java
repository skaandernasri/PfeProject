package tn.temporise.infrastructure.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.application.service.*;
import tn.temporise.domain.model.*;
import tn.temporise.infrastructure.api.AuthentificationApi;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController implements AuthentificationApi {

    private final AuthenticationManager authenticationManager;
    private final RegMapper regMapper;
    @Autowired
    private final RegistrationService registrationService;
    @Autowired
    private final LogoutService logoutService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    @Autowired
    private final TokenService tokenService;
    @Autowired
    private final CustomOAuth2UserService customOAuth2UserService;
    @Autowired
     CustomUserDetailsService customUserDetailsService;
    @Value("${cookie.expiration}")
    private int cookieExpiration;

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
    public ResponseEntity<Response> _refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            TokenResponse tokenResponse = tokenService.refreshToken(refreshTokenRequest);

            Cookie jwtCookie = new Cookie("jwt", tokenResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(cookieExpiration);

            response.addCookie(jwtCookie);
            Response response=new Response();
            response.setCode("200");
            response.setMessage("token rafraichi avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Token refresh failed: ", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<Response> _signinFacebook(SigninFacebookRequest signinFacebookRequest) {
        // Implementation for Facebook sign-in
        return null;
    }

    @Override
    public ResponseEntity<Response> _signinGoogle(SigninGoogleRequest signinGoogleRequest) {
        try {
            TokenResponse tokenResponse = customOAuth2UserService.signinGoogle(signinGoogleRequest.getIdToken());

            Cookie jwtCookie = new Cookie("jwt", tokenResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60);

            response.addCookie(jwtCookie);
            Response response=new Response();
            response.setCode("200");
            response.setMessage("Connecté avec google en succés");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Unexpected error during Google sign-in: ", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<Response> _signinUser(SigninUserRequest signinUserRequest) {
        try {
            TokenResponse tokenResponse = customUserDetailsService.signinUser(signinUserRequest,authenticationManager,tokenService);

            Cookie jwtCookie = new Cookie("jwt", tokenResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60);

            response.addCookie(jwtCookie);
            Response response=new Response();
            response.setCode("200");
            response.setMessage("Connecté en succés");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Authentication failed: ", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<Response> _signupUser(SignupUserRequest signupUserRequest) {
        try {
            UtilisateurEntity user = regMapper.toEntity(signupUserRequest);
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