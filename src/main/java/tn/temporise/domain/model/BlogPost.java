package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Date;


public record BlogPost(
        Long id,
        String titre,
        String contenu,
        Date datePublication,
        UtilisateurEntity user
) {}