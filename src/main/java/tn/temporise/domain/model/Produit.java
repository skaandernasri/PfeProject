package tn.temporise.domain.model;


import java.util.Set;

public record Produit(
        Long id,
        String nom,
        String description,
        double prix,
        int stock,
        Set<Promotion> promotions,
        Categorie categorie
) {}
