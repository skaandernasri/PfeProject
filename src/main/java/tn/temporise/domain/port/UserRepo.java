package tn.temporise.domain.port;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Optional;
import java.util.Set;
public interface UserRepo extends JpaRepository<UtilisateurEntity,Long> {
    Optional<UtilisateurEntity> findByEmail(String email);
    @Query("SELECT u.roles FROM UtilisateurEntity u WHERE u.id = :id")
    Set<Role> findRolesById(long id);
    void deleteByEmail(String email);
}

