package tn.temporise.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;

import java.util.Optional;


@Repository
public interface AuthJpaRepo extends JpaRepository<AuthentificationEntity,Long>  {
    Optional<AuthentificationEntity> findByUserEmail(String email);
    Optional<AuthentificationEntity> findByUserEmailAndProviderId(String email, String providerId);
    Optional<AuthentificationEntity> findByRefreshToken (String token);
}
