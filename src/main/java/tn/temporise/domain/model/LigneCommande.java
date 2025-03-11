package tn.temporise.domain.model;




public record LigneCommande(
        Long id,
        Produit produit,
        int quantite,
        double prixTotal,
        Commande commande
) {}
