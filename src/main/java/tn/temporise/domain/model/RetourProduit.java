package tn.temporise.domain.model;



import java.time.LocalDateTime;


public record RetourProduit(
        Long id,
        String raisonRetour,
        LocalDateTime dateRetour,
        Produit produit,
        UtilisateurModel utilisateur
) {}