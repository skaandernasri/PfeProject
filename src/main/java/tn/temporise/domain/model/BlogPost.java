package tn.temporise.domain.model;



import java.util.Date;


public record BlogPost(
        Long id,
        String titre,
        String contenu,
        Date datePublication,
        UtilisateurModel user
) {}