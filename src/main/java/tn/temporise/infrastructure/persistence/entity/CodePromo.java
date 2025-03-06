package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodePromo {

    private Long id;

    private String code;

    private double reduction;
    private LocalDateTime dateExpiration;
//    @OneToOne(mappedBy = "codePromo")
//    private Commande commande;
}