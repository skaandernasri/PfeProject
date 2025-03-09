package tn.temporise.tempo_rise_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.infrastructure.security.utils.JwtUtil;
import org.springframework.security.oauth2.jwt.JwtEncoder;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testGenerateAccessToken() {
        CustomUserDetails userDetails = userDetailsService.getUserDetails("user@example.com");
        String token = jwtUtil.generateAccessToken(userDetails);
        assertNotNull(token);
        System.out.println("Generated Token: " + token);
    }

}