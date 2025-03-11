package tn.temporise.domain.model;

import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;

public record Authentification(
        Long id,
        String password,
        String providerId,
        String token,
        TypeAuthentification type,
        UtilisateurModel user
) { }
