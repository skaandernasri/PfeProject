package tn.temporise.domain.model;

import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Date;


public record Commentaire(
        Long id,
        String contenu,
        Date datePublication,
        UtilisateurEntity user,
        BlogPost blogPost
) {}
