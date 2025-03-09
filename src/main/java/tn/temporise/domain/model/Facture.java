package tn.temporise.domain.model;


import java.time.LocalDateTime;


public record Facture(
        Long id,
        Facture facture,
        LocalDateTime dateEmission,
        Double total
) {}
