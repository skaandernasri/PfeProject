package tn.temporise.domain.model;

import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

public record Authentification(
        Long id,
        String password,
        String providerId,
        String token,
        TypeAuthentification type,
        UtilisateurEntity user
) { }
