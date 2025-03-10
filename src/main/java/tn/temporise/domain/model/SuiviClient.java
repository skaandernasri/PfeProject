package tn.temporise.domain.model;



import java.time.LocalDateTime;


public record SuiviClient(
        Long id,
        UtilisateurModel utilisateur,
        String action,
        LocalDateTime date
) {}
