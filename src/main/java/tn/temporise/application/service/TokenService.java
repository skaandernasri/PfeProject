package tn.temporise.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.UnauthorizedException;
import tn.temporise.domain.model.CustomUserDetails;
import tn.temporise.domain.model.RefreshTokenRequest;
import tn.temporise.domain.model.TokenResponse;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.security.utils.JwtUtil;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthRepo authRepo;

        public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String oldAccessToken = refreshTokenRequest.getAccessToken();
            String email = jwtUtil.extractEmail(oldAccessToken);
            String providerId=jwtUtil.extractProviderId(oldAccessToken);
            CustomUserDetails userDetails = userDetailsService.getUserDetails(email,providerId);
            Optional<AuthentificationEntity> storedToken = authRepo.findByUserEmailAndProviderId(email, providerId);
            if (storedToken.isEmpty()) {
                throw new UnauthorizedException("Aucun token trouvé pour cet utilisateur", "401");
            }

            String refreshToken = storedToken.get().getToken();
            if (!jwtUtil.validateToken(refreshToken, userDetails)) {
                throw new UnauthorizedException("Token de rafraîchissement invalide", "401");
            }

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);
            String newRefreshToken= jwtUtil.generateRefreshToken(userDetails);
            saveToken(email,newRefreshToken,providerId);

            TokenResponse response = new TokenResponse();
            response.setToken(newAccessToken);
            response.setRefreshToken(newRefreshToken);
            return response;
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage(), "401");
        }
    }

    public void saveToken(String email, String token, String providerId) {
        try {
            Optional<AuthentificationEntity> authentification = authRepo.findByUserEmailAndProviderId(email, providerId);
            if (authentification.isPresent()) {
                authentification.get().setToken(token);
                authRepo.save(authentification.get());
            }
        } catch (InternalServerErrorException e) {
            log.error("Error saving token: ", e);
            throw new InternalServerErrorException("Failed to save token","500");
        }
    }

    public void removeToken(CustomUserDetails customUserDetails) {
        try {
            String providerId = customUserDetails.getProviderId();
            String email = customUserDetails.getUsername();
            Optional<AuthentificationEntity> authentification = authRepo.findByUserEmailAndProviderId(email, providerId);
            if (authentification.isPresent()) {
                authentification.get().setToken(null);
                authRepo.save(authentification.get());
            }
            log.info("Token removed for user: " + email);
        } catch (InternalServerErrorException e) {
            log.error("Error removing token: ", e);
            throw new InternalServerErrorException("Failed to remove token","500");
        }
    }
}