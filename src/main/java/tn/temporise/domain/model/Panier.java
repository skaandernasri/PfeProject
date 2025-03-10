package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Set;


public record Panier(
        Long id,
        Set<Produit> produits,
        UtilisateurEntity user
) {}
