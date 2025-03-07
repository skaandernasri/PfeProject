package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    private Long id;
    private String nom;
    private String description;
    private double pourcentageReduction;
    private Date dateDebut;
    private Date dateFin;

    private Produit produit;
}
