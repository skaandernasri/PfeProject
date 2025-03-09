package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

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