package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.time.LocalDateTime;


public record RetourProduit(
        Long id,
        String raisonRetour,
        LocalDateTime dateRetour,
        Produit produit,
        UtilisateurEntity utilisateur
) {}