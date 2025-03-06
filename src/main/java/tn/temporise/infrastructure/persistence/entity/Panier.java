package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Panier {

    private Long id;
//    @ManyToMany
//    @JoinTable(
//            name = "panier_produit",
//            joinColumns = @JoinColumn(name = "panier_id"),
//            inverseJoinColumns = @JoinColumn(name = "produit_id")
//    )
    private Set<Produit> produits=new HashSet<>();


    private Utilisateur user;

}
