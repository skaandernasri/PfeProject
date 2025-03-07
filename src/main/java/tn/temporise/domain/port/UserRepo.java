package tn.temporise.domain.port;

import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

import java.util.Optional;
import java.util.Set;

public interface UserRepo {
    Optional<Utilisateur> findByEmail(String email);
    Set<Role> findRolesById(long id);
    void deleteByEmail(String email);
    Utilisateur save(Utilisateur user);
}

