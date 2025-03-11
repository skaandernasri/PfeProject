package tn.temporise.infrastructure.controller;


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
import tn.temporise.infrastructure.security.utils.JwtRequestFilter;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController implements AuthentificationApi {

    private final AuthenticationManager authenticationManager;
    private final RegMapper regMapper;
    private final RegistrationService registrationService;
    private final LogoutService logoutService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final TokenService tokenService;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Value("${cookie.expiration}")
    private int cookieExpiration;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    public ResponseEntity<Response> _logoutUser() throws Exception{
            logoutService.logout(request, response);
            Response responseBody = new Response();
            responseBody.setCode("200");
            responseBody.setMessage("Déconnecter avec succés");
            return ResponseEntity.ok().body(responseBody);

    }

    @Override
    public ResponseEntity<Response> _refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
            TokenResponse tokenResponse = tokenService.refreshToken(refreshTokenRequest);
            jwtRequestFilter.setJwtCookie(response,tokenResponse.getToken(),cookieExpiration);
            Response response = new Response();
            response.setCode("200");
            response.setMessage("token rafraichi avec succès");
            return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> _signinFacebook(SigninFacebookRequest signinFacebookRequest)throws Exception {
        // Implementation for Facebook sign-in
        return null;
    }

    @Override
    public ResponseEntity<Response> _signinGoogle(SigninGoogleRequest signinGoogleRequest) throws Exception {
        TokenResponse tokenResponse = customOAuth2UserService.signinGoogle(signinGoogleRequest.getIdToken());
        jwtRequestFilter.setJwtCookie(response,tokenResponse.getToken(),cookieExpiration);
        Response response = new Response();
        response.setCode("200");
        response.setMessage("Connecté avec google en succés");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<Response> _signinUser(SigninUserRequest signinUserRequest) throws Exception {
            TokenResponse tokenResponse = customUserDetailsService.signinUser(signinUserRequest, authenticationManager, tokenService);
            jwtRequestFilter.setJwtCookie(response,tokenResponse.getToken(),cookieExpiration);
            Response response = new Response();
            response.setCode("200");
            response.setMessage("Connecté avec succés");
            return ResponseEntity.ok(response);
        }
        @Override
        public ResponseEntity<Response> _signupUser (SignupUserRequest signupUserRequest) throws Exception {
            UtilisateurModel user = regMapper.toModel(signupUserRequest);
            registrationService.register(user);
            log.info("User registered successfully");
            Response response = new Response();
            response.setCode("201");
            response.setMessage("utilisateur crée avec succés");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

}