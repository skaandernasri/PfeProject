package tn.temporise.domain.model;


import java.util.Date;


public record Commentaire(
        Long id,
        String contenu,
        Date datePublication,
        UtilisateurModel user,
        BlogPost blogPost
) {}
