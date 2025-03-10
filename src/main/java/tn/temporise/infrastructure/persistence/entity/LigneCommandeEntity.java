package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lignecommande")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneCommandeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private ProduitEntity produit;

    private int quantite;

    private double prixTotal;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeEntity commande;



}

