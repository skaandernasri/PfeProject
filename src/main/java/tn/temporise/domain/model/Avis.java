package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.Produit;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avis {

    private Long id;
    private int note;
    private String commentaire;
    private LocalDateTime datePublication;


    private Utilisateur user;


    private Produit produit;
}
