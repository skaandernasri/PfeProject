package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "produit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProduitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private double prix;

    @ManyToMany(mappedBy = "produits")
    private Set<PanierEntity> paniers;
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private Set<PromotionEntity> promotions=new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private CategorieEntity categorie;
    @ManyToMany(mappedBy = "produits")
    private Set<HistoriqueCommandeEntity> historiqueCommandeEntities;

}

