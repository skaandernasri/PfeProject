package tn.temporise.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Produit {

    private Long id;

    private String nom;

    private String description;

    private double prix;

    private int stock=0;

//    @ManyToMany(mappedBy = "produits")
//    private Set<Panier> paniers;
    private Set<Promotion> promotions=new HashSet<>();

    private Categorie categorie;

}

