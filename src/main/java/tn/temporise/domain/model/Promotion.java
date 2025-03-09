package tn.temporise.domain.model;



import java.util.Date;


public record Promotion(
        Long id,
        String nom,
        String description,
        double pourcentageReduction,
        Date dateDebut,
        Date dateFin,
        Produit produit
) {}
