package tn.temporise.domain.model;



import java.time.LocalDateTime;


public record Avis (  Long id,
        int note,
        String commentaire,
        LocalDateTime datePublication,


        UtilisateurModel user,


        Produit produit){


}
