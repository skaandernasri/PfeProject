package tn.temporise.domain.model;




public record Categorie(
        Long id,
        String nom,
        String description
) {
    public Categorie (Long id) {
        this(id,null,null);
    }
}

