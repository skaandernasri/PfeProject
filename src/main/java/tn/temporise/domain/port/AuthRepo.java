package tn.temporise.domain.port;


import tn.temporise.domain.model.Authentification;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

import java.util.Optional;
public interface AuthRepo {
    Optional<Authentification> findByUserEmail(String email);
    Optional<Authentification> findByUserEmailAndProviderId(String email, String providerId);
    Optional<Authentification> findByRefreshToken (String token);
    Authentification save(Authentification authentification);
}

