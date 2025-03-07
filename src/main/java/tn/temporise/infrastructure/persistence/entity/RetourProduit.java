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
public class RetourProduit {

    private Long id;
    private String raisonRetour;
    private LocalDateTime dateRetour;


    private Produit produit;

    private Utilisateur utilisateur;
}