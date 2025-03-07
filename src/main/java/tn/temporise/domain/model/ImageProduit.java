package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.Produit;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduit {

    private Long id;
    private String url;

    private Produit produit;
}
