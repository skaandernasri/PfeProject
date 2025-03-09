package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Categorie {

    private Long id;

    private String nom;

    private String description;

//    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
//    private List<Produit> produits = new ArrayList<>();
}

