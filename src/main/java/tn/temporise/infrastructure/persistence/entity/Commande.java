package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commande {

    private Long id;

    private LocalDateTime date;


    private StatutCommande statut;


    private Utilisateur user;


    private ModePaiement modePaiement;


    private CodePromo codePromo;

//    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignesCommande = new ArrayList<>();

    //@OneToOne(mappedBy ="commande" )
    private Long facture_id;
}

