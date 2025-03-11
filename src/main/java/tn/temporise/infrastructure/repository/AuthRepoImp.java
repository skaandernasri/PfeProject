package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class AuthRepoImp implements AuthRepo {
    private final AuthJpaRepo authJpaRepo;
    @Override
    public Optional<AuthentificationEntity> findByUserEmail(String email) {
        return authJpaRepo.findByUserEmail(email);
    }

    @Override
    public Optional<AuthentificationEntity> findByUserEmailAndProviderId(String email, String providerId) {
        return authJpaRepo.findByUserEmailAndProviderId(email,providerId);
    }

    @Override
    public Optional<AuthentificationEntity> findByRefreshToken(String token) {
        return authJpaRepo.findByRefreshToken(token);
    }

    @Override
    public AuthentificationEntity save(AuthentificationEntity authentification) {
        return authJpaRepo.save(authentification);
    }
}
