package tn.temporise.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.util.Collections;

public class TestUtils {

    public static String generateTestJwtToken(JwtUtil jwtUtil,String email) {
        // Create a test user with authorities
        CustomUserDetails userDetails = new CustomUserDetails(
                email, // Username
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")) // Authorities
        );
        // Generate the JWT token
        return jwtUtil.generateAccessToken(userDetails);
    }

}