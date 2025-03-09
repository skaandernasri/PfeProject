package tn.temporise.domain.port;


import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

import java.util.Optional;
public interface AuthRepo extends JpaRepository<AuthentificationEntity,Long> {
    Optional<AuthentificationEntity> findByUserEmail(String email);
    Optional<AuthentificationEntity> findByUserEmailAndProviderId(String email, String providerId);
    Optional<AuthentificationEntity> findByToken (String token);
}

