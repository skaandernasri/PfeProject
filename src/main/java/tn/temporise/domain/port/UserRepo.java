package tn.temporise.domain.port;

import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.infrastructure.persistence.entity.Role;
import java.util.Optional;
import java.util.Set;

public interface UserRepo {
    Optional<UtilisateurModel> findByEmail(String email);
    Set<Role> findRolesById(long id);
    void deleteByEmail(String email);
    UtilisateurModel save(UtilisateurModel utilisateurModel);

    void deleteAll();
}

