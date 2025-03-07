package tn.temporise.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
