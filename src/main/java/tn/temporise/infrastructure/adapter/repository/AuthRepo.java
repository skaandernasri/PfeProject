package tn.temporise.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.temporise.domain.model.Authentification;

import java.util.Optional;

public interface AuthRepo extends JpaRepository<Authentification,Long> {
    Optional<Authentification> findByUserEmail(String email);
}
