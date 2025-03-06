package tn.temporise.domain.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.temporise.infrastructure.persistence.entity.Authentification;

import java.util.Optional;
public interface AuthRepo {
    Optional<Authentification> findByUserEmail(String email);
    Optional<Authentification> findByUserEmailAndProviderId(String email, String providerId);
    Authentification save(Authentification authentification);
}

