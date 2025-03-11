package tn.temporise.domain.model;


import java.time.LocalDateTime;


public record Facture(
        Long id,
        LocalDateTime dateEmission,
        Double total
) {}
