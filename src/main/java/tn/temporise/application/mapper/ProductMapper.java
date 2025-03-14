package tn.temporise.application.mapper;

import org.mapstruct.Mapper;
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.ProductRequest;
import tn.temporise.domain.model.ProductResponse;
import tn.temporise.domain.model.Produit;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Produit dtoToModel(ProductRequest productRequest);
    ProduitEntity modelToEntity(Produit produit);
    Produit entityToModel(ProduitEntity produitEntity);
//    @Mapping(source = "promotions", target = "promotions")
//    @Mapping(source = "categorie",target = "categorie")
    ProductResponse modelToResponse(Produit produit);
    ProductResponse entityToResponse(ProduitEntity produit);
    default Categorie map(Long categorieId) {
        if (categorieId == null) {
            return null;
        }
        return new Categorie(categorieId);
    }
    default OffsetDateTime map(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atOffset(ZoneOffset.UTC); // Convert Date to OffsetDateTime
    }
}
