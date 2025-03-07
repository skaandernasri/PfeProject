package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.temporise.infrastructure.persistence.entity.ModePaiement;
import tn.temporise.infrastructure.persistence.entity.StatutCommande;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

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

