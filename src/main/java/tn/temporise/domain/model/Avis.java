package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.ProduitEntity;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.time.LocalDateTime;


public record Avis (  Long id,
        int note,
        String commentaire,
        LocalDateTime datePublication,


        UtilisateurEntity user,


        ProduitEntity produit){


}
