package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.ProduitEntity;
import tn.temporise.infrastructure.persistence.entity.StatutCommande;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.time.LocalDateTime;
import java.util.Set;


public record HistoriqueCommande(
        Long id,
        LocalDateTime dateCommande,
        StatutCommande statut,
        Set<ProduitEntity> produits,
        UtilisateurEntity user
) {}
