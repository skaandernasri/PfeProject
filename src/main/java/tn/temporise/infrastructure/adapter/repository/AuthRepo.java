package tn.temporise.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.model.Authentification;

import java.util.Optional;
@Repository
public interface AuthRepo extends JpaRepository<Authentification,Long> {
    Optional<Authentification> findByUserEmail(String email);
}
