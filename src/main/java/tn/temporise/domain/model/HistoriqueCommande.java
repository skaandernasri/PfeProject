package tn.temporise.domain.model;


import tn.temporise.infrastructure.persistence.entity.StatutCommande;

import java.time.LocalDateTime;
import java.util.Set;


public record HistoriqueCommande(
        Long id,
        LocalDateTime dateCommande,
        StatutCommande statut,
        Set<Produit> produits,
        UtilisateurModel user
) {}
