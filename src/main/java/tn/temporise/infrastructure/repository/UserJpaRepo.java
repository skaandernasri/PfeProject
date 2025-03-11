package tn.temporise.infrastructure.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Optional;
import java.util.Set;
@Repository
public interface UserJpaRepo extends JpaRepository<UtilisateurEntity,Long> {
    Optional<UtilisateurEntity> findByEmail(String email);
    @Query("SELECT u.roles FROM UtilisateurEntity u WHERE u.id = :id")
    Set<Role> findRolesById(long id);
    void deleteByEmail(String email);
    @Query("SELECT c FROM UtilisateurEntity c WHERE c.id = :id")
    Optional<UtilisateurEntity> findById(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM UtilisateurEntity c WHERE c.id = :id")
    void deleteById(@Param("id") Long id);

}
