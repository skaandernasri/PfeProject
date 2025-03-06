package tn.temporise.tempo_rise_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

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
    private UserDetailsService userDetailsService;

    @Test
    public void testGenerateAccessToken() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user@example.com");
        String token = jwtUtil.generateAccessToken(userDetails);
        assertNotNull(token);
        System.out.println("Generated Token: " + token);
    }

}