package tn.temporise.domain.model;


import lombok.Builder;

import java.util.Set;
@Builder
public record Produit(
        Long id,
        String nom,
        String description,
        double prix,
        Long stock,
        Set<Promotion> promotions,
        Categorie categorie,
        int quantite
) {}
