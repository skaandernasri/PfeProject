package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.Produit;
import tn.temporise.infrastructure.persistence.entity.StatutCommande;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueCommande {

    private Long id;
    private LocalDateTime dateCommande;
    private StatutCommande statut;
    Set<Produit> produits=new HashSet<>();

    private Utilisateur user;
}