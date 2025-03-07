package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.Produit;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneCommande {

    private Long id;


    private Produit produit;

    private int quantite;

    private double prixTotal;


    private Commande commande;



}

