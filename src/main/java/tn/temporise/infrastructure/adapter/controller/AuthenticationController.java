package tn.temporise.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.component.JwtUtil;
import tn.temporise.application.dto.AuthenticationRequest;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.domain.model.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Utilisateur user = authMapper.toEntity(authenticationRequest);
            if (user == null) {
                throw new Exception("User not found");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            return jwtUtil.generateToken(userDetails);
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new Exception("Invalid email or password", e);
        }
    }

    @GetMapping("/oauth2/login-success")
    public String oauth2LoginSuccess(@AuthenticationPrincipal OAuth2User oauth2User) {

        // Extract user details from OAuth2User
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Load or create the user in your database
        UserDetails userDetails = userDetailsService.loadOrCreateOAuth2User(email);

        // Generate a JWT token for the user
        return jwtUtil.generateToken(userDetails);
    }
}
