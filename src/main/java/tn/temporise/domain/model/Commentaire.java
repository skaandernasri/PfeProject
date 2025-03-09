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
public class Commentaire {

    private Long id;
    private String contenu;
    private Date datePublication;


    private Utilisateur user;


    private BlogPost blogPost;
}
