package tn.temporise.domain.port;


import tn.temporise.infrastructure.persistence.entity.Authentification;

import java.util.Optional;
public interface AuthRepo {
    Optional<Authentification> findByUserEmail(String email);
    Optional<Authentification> findByUserEmailAndProviderId(String email, String providerId);
    Authentification save(Authentification authentification);
    Optional<Authentification> findByToken (String token);
}

