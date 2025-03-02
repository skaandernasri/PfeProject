package tn.temporise.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.Utilisateur;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<Utilisateur,Long> {
    Optional<Utilisateur> findByEmail(String email);
    @Query("SELECT u.roles FROM Utilisateur u WHERE u.id = :id")
    Set<Role> findRolesById(@Param("id") long id);
    void deleteByEmail(String email);
}
