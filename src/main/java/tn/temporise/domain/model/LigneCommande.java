package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.ProduitEntity;


public record LigneCommande(
        Long id,
        ProduitEntity produit,
        int quantite,
        double prixTotal,
        Commande commande
) {}
