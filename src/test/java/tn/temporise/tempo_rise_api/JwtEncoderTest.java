package tn.temporise.tempo_rise_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtEncoderTest {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Test
    public void testJwtEncoder() {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("your-issuer")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject("test-user")
                .build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        assertNotNull(jwt.getTokenValue());
        System.out.println("Generated JWT: " + jwt.getTokenValue());
    }
}