package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.ModePaiement;
import tn.temporise.infrastructure.persistence.entity.StatutCommande;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.time.LocalDateTime;
import java.util.List;


public record Commande(
        Long id,
        LocalDateTime date,
        StatutCommande statut,
        UtilisateurEntity user,
        ModePaiement modePaiement,
        CodePromo codePromo,
        List<LigneCommande> lignesCommande,
        Long factureId
) {}
