package tn.temporise.domain.model;


import java.time.LocalDateTime;


public record CodePromo(
        Long id,
        String code,
        double reduction,
        LocalDateTime dateExpiration
) {}