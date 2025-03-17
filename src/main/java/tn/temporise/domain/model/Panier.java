package tn.temporise.domain.model;



import java.util.Set;


public record Panier(
        Long id,
        Set<Produit> produits,
        UtilisateurModel utilisateur
) {
    public Panier withId(Long id) {
        return new Panier(id, this.produits, this.utilisateur);
    }
}
