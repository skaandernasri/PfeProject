package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
