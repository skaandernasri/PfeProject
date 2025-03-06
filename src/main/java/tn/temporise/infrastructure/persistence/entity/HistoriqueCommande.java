package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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