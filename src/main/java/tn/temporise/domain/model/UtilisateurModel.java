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

}
