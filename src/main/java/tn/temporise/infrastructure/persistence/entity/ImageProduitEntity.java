package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imageproduit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private ProduitEntity produit;
    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private UtilisateurEntity auteur;
    @ManyToOne
    @JoinColumn(name = "blogpost_id", nullable = false)
    private BlogPostEntity blogPost;
}
