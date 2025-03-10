package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.ProduitEntity;


public record ImageProduit(
        Long id,
        String url,
        ProduitEntity produit
) {}