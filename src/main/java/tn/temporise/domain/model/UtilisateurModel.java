package tn.temporise.domain.model;

import java.util.Set;

public record UtilisateurModel(
        Long id,
        String nom,
        String email,
        String password,
        Set<Role> roles
) {
    public UtilisateurModel(String email, String nom, Role role) {
        this(null, nom, email, null, Set.of(role));
    }

    public UtilisateurModel(String email, Role role) {
        this(null, null, email, null, Set.of(role));
    }
    public UtilisateurModel(Long id) {
        this(id, null, null, null, null);
    }
    public UtilisateurModel(Long id,String email) {
        this(id, null, email, null, null);
    }
    public UtilisateurModel(Long id,String email,Set<Role> roles) {
        this(id, null, email, null, roles);
    }

}
