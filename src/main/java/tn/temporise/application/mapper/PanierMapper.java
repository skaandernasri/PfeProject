package tn.temporise.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.temporise.domain.model.*;
import tn.temporise.infrastructure.persistence.entity.PanierEntity;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PanierMapper {

    @Mapping(target = "utilisateur", source = "utilisateurId")
    @Mapping(target = "produits", source = "articles")
    Panier dtoToModel(PanierRequest panierRequest);

    @Mapping(target = "utilisateurId", source = "utilisateur.id")
    @Mapping(target = "articles", source = "produits")
    PanierResponse modelToResponse(Panier panier);

    @Mapping(target = "utilisateurId", source = "utilisateur.id")
    @Mapping(target = "articles", source = "produits")
    PanierResponse entityToResponse(PanierEntity panier);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "produits", source = "produits")
    @Mapping(target = "utilisateur", source = "utilisateur")
    Panier entityToModel(PanierEntity panier);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "produits", source = "produits")
    @Mapping(target = "utilisateur", source = "utilisateur")
    PanierEntity modelToEntity(Panier panier);

    @Mapping(target = "articleId", source = "id")
    @Mapping(target = "quantite", source = "quantite")
    PanierRequestArticlesInner produitToArticle(Produit produit);

    default List<PanierRequestArticlesInner> mapProduitsToArticles(Set<Produit> produits) {
        return produits.stream()
                .map(this::produitToArticle)
                .collect(Collectors.toList());
    }

    default UtilisateurEntity utilisateurIdToEntity(Long utilisateurId) {
        if (utilisateurId == null) return null;
        return new UtilisateurEntity(utilisateurId);
    }

    default UtilisateurModel utilisateurIdToModel(Long utilisateurId) {
        if (utilisateurId == null) return null;
        return new UtilisateurModel(utilisateurId);
    }

    default Set<Produit> mapArticlesToProduits(List<PanierRequestArticlesInner> articles) {
        if (articles == null) {
            return new HashSet<>();
        }
        return articles.stream()
                .map(article -> new Produit(article.getArticleId(), null, null, 0.0, 0L, new HashSet<>(), null, article.getQuantite()))
                .collect(Collectors.toSet());
    }
}