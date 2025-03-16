package tn.temporise.domain.model;

import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;

public record Authentification(
        Long id,
        String password,
        String providerId,
        String token,
        TypeAuthentification type,
        UtilisateurModel user
) {
    public Authentification(long id, String password,String providerId) {
        this(id, password, providerId,null,null,null);
    }
}
