package tn.temporise.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.component.JwtUtil;
import tn.temporise.application.dto.AuthenticationRequest;
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

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Utilisateur user=authMapper.toEntity(authenticationRequest);
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
}
