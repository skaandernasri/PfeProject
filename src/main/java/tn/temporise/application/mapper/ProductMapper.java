package tn.temporise.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tn.temporise.domain.model.*;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Map ProductRequest to Produit
    @Mapping(target = "categorie", source = "categorie",qualifiedByName = "mapCategorieIdToCategorie")
    Produit dtoToModel(ProductRequest productRequest);

    // Map Produit to ProduitEntity
    @Mapping(target = "categorie", source = "categorie")
    ProduitEntity modelToEntity(Produit produit);

    // Map ProduitEntity to Produit
    @Mapping(target = "categorie", source = "categorie")
    Produit entityToModel(ProduitEntity produitEntity);

    // Map Produit to ProductResponse
    @Mapping(target = "categorie", source = "categorie")
    ProductResponse modelToResponse(Produit produit);

    // Map ProduitEntity to ProductResponse
    @Mapping(target = "categorie", source = "categorie")
    ProductResponse entityToResponse(ProduitEntity produit);

    // Map Long to Categorie
    @Named("mapCategorieIdToCategorie")
    default Categorie mapCategorieIdToCategorie(Long categorieId) {
        if (categorieId == null) {
            return null;
        }
            return new Categorie(categorieId);
    }
    // Convert Date to OffsetDateTime
    default OffsetDateTime mapDateToOffsetDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atOffset(ZoneOffset.UTC); // Convert Date to OffsetDateTime
    }
}

