package tn.temporise.domain.model;

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
public class ImageProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,name = "url")
    private String url;
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
}
