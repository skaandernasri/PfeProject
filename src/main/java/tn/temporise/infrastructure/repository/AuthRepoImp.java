package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.domain.model.Authentification;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class AuthRepoImp implements AuthRepo {
    private final AuthJpaRepo authJpaRepo;
    private final AuthMapper authMapper;
    @Override
    public Optional<Authentification> findByUserEmail(String email) {
        Optional<AuthentificationEntity> authentification=authJpaRepo.findByUserEmail(email);
        return authentification.map(authMapper::entityToModel);
    }

    @Override
    public Optional<Authentification> findByUserEmailAndProviderId(String email, String providerId) {
        Optional<AuthentificationEntity> authentification=authJpaRepo.findByUserEmailAndProviderId(email,providerId);
        return authentification.map(authMapper::entityToModel);
    }

    @Override
    public Optional<Authentification> findByRefreshToken(String token) {
        Optional<AuthentificationEntity> authentification=authJpaRepo.findByRefreshToken(token);
        return authentification.map(authMapper::entityToModel);
    }

    @Override
    public Authentification save(Authentification authentification) {
        AuthentificationEntity auth=authJpaRepo.save(authMapper.modelToEntity(authentification));
        return authMapper.entityToModel(auth);
    }
}
