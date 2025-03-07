package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {

    private Long id;

    private String titre;
    private String contenu;
    private Date datePublication;


    private Utilisateur user;

//    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
//    private List<Commentaire> commentaires;
//
//    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
//    private List<ImageBlogPost> images;
}
