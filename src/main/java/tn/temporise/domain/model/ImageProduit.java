package tn.temporise.domain.model;




public record ImageProduit(
        Long id,
        String url,
        Produit produit
) {}