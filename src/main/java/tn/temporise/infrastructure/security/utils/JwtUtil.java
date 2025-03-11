package tn.temporise.infrastructure.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import tn.temporise.domain.model.CustomUserDetails;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Component
public class JwtUtil {

    private final String secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    @Autowired
    public JwtUtil(@Value("${jwt.secret.key}")String secretKey,JwtEncoder jwtEncoder, JwtDecoder jwtDecoder,@Value("${jwt.refresh.token.expiration}") long refreshTokenExpiration,@Value("${jwt.access.token.expiration}") long accessTokenExpiration) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.accessTokenExpiration = accessTokenExpiration;
        this.secretKey=secretKey;
    }

    public String extractEmail(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    public Instant extractExpiration(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getExpiresAt();
    }

    private Boolean isTokenExpired(String token) {
        Instant expiration = extractExpiration(token);
        return expiration != null && expiration.isBefore(Instant.now());
    }
    public String extractProviderId(String token){
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaim("provider_id").toString();
    }

    // Generate Access Token
    public String generateAccessToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("provider_id",userDetails.getProviderId());
        log.info("-------authorities "+ claims.values());
        return createToken(claims, userDetails.getUsername(), accessTokenExpiration);
    }

    // Generate Refresh Token
    public String generateRefreshToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("provider_id",userDetails.getProviderId());
        claims.put("roles",userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return createToken(claims, userDetails.getUsername(), refreshTokenExpiration);
    }
    private String createToken(Map<String, Object> claims, String subject,long expiration) {
        var jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256).build();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("tempo-rise") // Set your issuer
                .subject(subject)
                .claims(claimsMap -> claimsMap.putAll(claims)) // Use a lambda to add claims
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(expiration))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claimsSet)).getTokenValue();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    // Extract Claims from Token
    public  Map<String, Object> extractClaims(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }


}