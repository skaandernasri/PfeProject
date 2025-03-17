package tn.temporise.application.service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.BadRequestException;
import tn.temporise.application.exception.GoogleException;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.domain.model.*;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.text.ParseException;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AuthRepo authRepo;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RegMapper regMapper;
    @Autowired
    private AuthMapper authMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oauth2User = super.loadUser(userRequest);

            // Extract the email from the OAuth2 user attributes
            String email = oauth2User.getAttribute("email");
            String idToken = userRequest.getAccessToken().getTokenValue();
            log.info("Loading OAuth2 user with email: {}", email);

            // Find the user by email, or create a new one if not found
            UtilisateurModel user = userRepo.findByEmail(email)
                    .orElseGet(() -> {
                        log.info("Creating new user for email: {}", email);
                        UtilisateurModel newUser = new UtilisateurModel(email, Role.CLIENT);
//                        newUser.setEmail(email);
//                        newUser.setRoles(Collections.singleton(Role.CLIENT)); // Assign a default role
                        return userRepo.save(newUser); // Save the new user to the database
                    });

            // Check if the user already has an authentication record for this provider
            Optional<Authentification> existingAuth = authRepo.findByUserEmailAndProviderId(email, "1");
            if (existingAuth.isEmpty()) {

                // Create a new authentication record
                AuthentificationEntity auth = new AuthentificationEntity();
                auth.setUser(regMapper.modelToEntity(user)); // Link the authentication to the user
                auth.setProviderId("1"); // Google provider ID
                auth.setType(TypeAuthentification.GOOGLE);
                auth.setRefreshToken(jwtUtil.generateRefreshToken(new CustomUserDetails(email, "1"))); // Save the Google ID token
                authRepo.save(authMapper.entityToModel(auth)); // Save the authentication entry to the database
            }
            // Return the OAuth2 user details
            return oauth2User;
        } catch (OAuth2AuthenticationException e) {
            log.error("Error during OAuth2 user loading: ", e);
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }

    public TokenResponse signinGoogle(String idToken) {
        if (idToken == null || idToken.isEmpty()) {
            throw new BadRequestException("Token Google manquant", "400");
        }

        try {
            JWT jwt = JWTParser.parse(idToken);
            JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
            String email = claimsSet.getStringClaim("email");
            CustomUserDetails userDetails = userDetailsService.getUserDetails(email, "1");
            String accessToken = jwtUtil.generateAccessToken(userDetails);

            TokenResponse token = new TokenResponse();
            token.setToken(accessToken);
            return token;
        } catch (GoogleException | ParseException e) {
            throw new GoogleException("Échec de l'authentification Google");
        }
    }
}