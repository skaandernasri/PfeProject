package tn.temporise.domain.model;



import java.util.Set;


public record Panier(
        Long id,
        Set<Produit> produits,
        UtilisateurModel user
) {}
