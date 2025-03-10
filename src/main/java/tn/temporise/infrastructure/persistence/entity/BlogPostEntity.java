package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blogpost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String contenu;
    private Date datePublication;


    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private UtilisateurEntity auteur;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    private List<CommentaireEntity> commentaires;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    private List<ImageProduitEntity> images;
}
