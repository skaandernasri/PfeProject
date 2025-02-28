package tn.temporise.domain.model;

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
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false,name="quantite")
    private int quantite;

    @Column(nullable = false,name = "prixTotal")
    private double prixTotal;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;



}

