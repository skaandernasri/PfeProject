package tn.temporise.application.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.CategorieRequest;
import tn.temporise.domain.model.CategorieResponse;
import tn.temporise.infrastructure.persistence.entity.CategorieEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T10:20:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CategorieMapperImpl implements CategorieMapper {

    @Override
    public Categorie dtoToModel(CategorieRequest categorieRequest) {
        if ( categorieRequest == null ) {
            return null;
        }

        String nom = null;
        String description = null;

        nom = categorieRequest.getNom();
        description = categorieRequest.getDescription();

        Long id = null;

        Categorie categorie = new Categorie( id, nom, description );

        return categorie;
    }

    @Override
    public CategorieEntity modelToEntity(Categorie categorie) {
        if ( categorie == null ) {
            return null;
        }

        CategorieEntity categorieEntity = new CategorieEntity();

        categorieEntity.setId( categorie.id() );
        categorieEntity.setNom( categorie.nom() );
        categorieEntity.setDescription( categorie.description() );

        return categorieEntity;
    }

    @Override
    public Categorie entityToModel(CategorieEntity categorie) {
        if ( categorie == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String description = null;

        id = categorie.getId();
        nom = categorie.getNom();
        description = categorie.getDescription();

        Categorie categorie1 = new Categorie( id, nom, description );

        return categorie1;
    }

    @Override
    public CategorieResponse modelToResponse(Categorie categorie) {
        if ( categorie == null ) {
            return null;
        }

        CategorieResponse categorieResponse = new CategorieResponse();

        categorieResponse.setId( categorie.id() );
        categorieResponse.setNom( categorie.nom() );
        categorieResponse.setDescription( categorie.description() );

        return categorieResponse;
    }

    @Override
    public CategorieResponse entityToResponse(CategorieEntity categorie) {
        if ( categorie == null ) {
            return null;
        }

        CategorieResponse categorieResponse = new CategorieResponse();

        categorieResponse.setId( categorie.getId() );
        categorieResponse.setNom( categorie.getNom() );
        categorieResponse.setDescription( categorie.getDescription() );

        return categorieResponse;
    }
}
