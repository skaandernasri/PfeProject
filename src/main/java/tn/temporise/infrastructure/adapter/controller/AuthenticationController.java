package tn.temporise.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.security.JwtUtil;
import tn.temporise.application.dto.AuthenticationRequest;
import tn.temporise.application.dto.AuthenticationResponse;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.domain.service.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    AuthMapper authMapper;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    CustomUserDetailsService userDetailsService,
                                    JwtUtil jwtUtil
                                    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Utilisateur user = authMapper.toEntity(authenticationRequest);
            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new AuthenticationResponse("error", "User not found", null, null));
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse("success", "Login successful", jwt, user.getEmail()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("error", "Invalid email or password", null, null));
        }
    }

    @GetMapping("/oauth2/login-success")
    public ResponseEntity<?> oauth2LoginSuccess(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("error", "OAuth2 authentication failed", null, null));
        }

        String email = oauth2User.getAttribute("email");
        //String name = oauth2User.getAttribute("name");

        UserDetails userDetails = userDetailsService.loadOrCreateOAuth2User(email);

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse("success", "OAuth2 login successful", jwt, email));
    }
}
