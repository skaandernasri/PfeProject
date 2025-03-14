package tn.temporise.application.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.ProductRequest;
import tn.temporise.domain.model.ProductRequestPromotionsInner;
import tn.temporise.domain.model.ProductResponse;
import tn.temporise.domain.model.ProductResponseCategorie;
import tn.temporise.domain.model.ProductResponsePromotionsInner;
import tn.temporise.domain.model.Produit;
import tn.temporise.domain.model.Promotion;
import tn.temporise.infrastructure.persistence.entity.CategorieEntity;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;
import tn.temporise.infrastructure.persistence.entity.PromotionEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T10:20:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Produit dtoToModel(ProductRequest productRequest) {
        if ( productRequest == null ) {
            return null;
        }

        String nom = null;
        String description = null;
        double prix = 0.0d;
        int stock = 0;
        Set<Promotion> promotions = null;
        Categorie categorie = null;

        nom = productRequest.getNom();
        description = productRequest.getDescription();
        if ( productRequest.getPrix() != null ) {
            prix = productRequest.getPrix();
        }
        if ( productRequest.getStock() != null ) {
            stock = productRequest.getStock().intValue();
        }
        promotions = productRequestPromotionsInnerListToPromotionSet( productRequest.getPromotions() );
        categorie = map( productRequest.getCategorie() );

        Long id = null;

        Produit produit = new Produit( id, nom, description, prix, stock, promotions, categorie );

        return produit;
    }

    @Override
    public ProduitEntity modelToEntity(Produit produit) {
        if ( produit == null ) {
            return null;
        }

        ProduitEntity produitEntity = new ProduitEntity();

        produitEntity.setId( produit.id() );
        produitEntity.setNom( produit.nom() );
        produitEntity.setDescription( produit.description() );
        produitEntity.setPrix( produit.prix() );
        produitEntity.setPromotions( promotionSetToPromotionEntitySet( produit.promotions() ) );
        produitEntity.setCategorie( categorieToCategorieEntity( produit.categorie() ) );

        return produitEntity;
    }

    @Override
    public Produit entityToModel(ProduitEntity produitEntity) {
        if ( produitEntity == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String description = null;
        double prix = 0.0d;
        Set<Promotion> promotions = null;
        Categorie categorie = null;

        id = produitEntity.getId();
        nom = produitEntity.getNom();
        description = produitEntity.getDescription();
        prix = produitEntity.getPrix();
        promotions = promotionEntitySetToPromotionSet( produitEntity.getPromotions() );
        categorie = categorieEntityToCategorie( produitEntity.getCategorie() );

        int stock = 0;

        Produit produit = new Produit( id, nom, description, prix, stock, promotions, categorie );

        return produit;
    }

    @Override
    public ProductResponse modelToResponse(Produit produit) {
        if ( produit == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId( produit.id() );
        productResponse.setNom( produit.nom() );
        productResponse.setDescription( produit.description() );
        productResponse.setPrix( produit.prix() );
        productResponse.setStock( produit.stock() );
        productResponse.setCategorie( categorieToProductResponseCategorie( produit.categorie() ) );
        productResponse.setPromotions( promotionSetToProductResponsePromotionsInnerList( produit.promotions() ) );

        return productResponse;
    }

    @Override
    public ProductResponse entityToResponse(ProduitEntity produit) {
        if ( produit == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId( produit.getId() );
        productResponse.setNom( produit.getNom() );
        productResponse.setDescription( produit.getDescription() );
        productResponse.setPrix( produit.getPrix() );
        productResponse.setCategorie( categorieEntityToProductResponseCategorie( produit.getCategorie() ) );
        productResponse.setPromotions( promotionEntitySetToProductResponsePromotionsInnerList( produit.getPromotions() ) );

        return productResponse;
    }

    protected Promotion productRequestPromotionsInnerToPromotion(ProductRequestPromotionsInner productRequestPromotionsInner) {
        if ( productRequestPromotionsInner == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String description = null;
        double pourcentageReduction = 0.0d;
        Date dateDebut = null;
        Date dateFin = null;
        Produit produit = null;

        Promotion promotion = new Promotion( id, nom, description, pourcentageReduction, dateDebut, dateFin, produit );

        return promotion;
    }

    protected Set<Promotion> productRequestPromotionsInnerListToPromotionSet(List<ProductRequestPromotionsInner> list) {
        if ( list == null ) {
            return null;
        }

        Set<Promotion> set = new LinkedHashSet<Promotion>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( ProductRequestPromotionsInner productRequestPromotionsInner : list ) {
            set.add( productRequestPromotionsInnerToPromotion( productRequestPromotionsInner ) );
        }

        return set;
    }

    protected PromotionEntity promotionToPromotionEntity(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionEntity promotionEntity = new PromotionEntity();

        promotionEntity.setId( promotion.id() );
        promotionEntity.setNom( promotion.nom() );
        promotionEntity.setDescription( promotion.description() );
        promotionEntity.setPourcentageReduction( promotion.pourcentageReduction() );
        promotionEntity.setDateDebut( promotion.dateDebut() );
        promotionEntity.setDateFin( promotion.dateFin() );
        promotionEntity.setProduit( modelToEntity( promotion.produit() ) );

        return promotionEntity;
    }

    protected Set<PromotionEntity> promotionSetToPromotionEntitySet(Set<Promotion> set) {
        if ( set == null ) {
            return null;
        }

        Set<PromotionEntity> set1 = new LinkedHashSet<PromotionEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Promotion promotion : set ) {
            set1.add( promotionToPromotionEntity( promotion ) );
        }

        return set1;
    }

    protected CategorieEntity categorieToCategorieEntity(Categorie categorie) {
        if ( categorie == null ) {
            return null;
        }

        CategorieEntity categorieEntity = new CategorieEntity();

        categorieEntity.setId( categorie.id() );
        categorieEntity.setNom( categorie.nom() );
        categorieEntity.setDescription( categorie.description() );

        return categorieEntity;
    }

    protected Promotion promotionEntityToPromotion(PromotionEntity promotionEntity) {
        if ( promotionEntity == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String description = null;
        double pourcentageReduction = 0.0d;
        Date dateDebut = null;
        Date dateFin = null;
        Produit produit = null;

        id = promotionEntity.getId();
        nom = promotionEntity.getNom();
        description = promotionEntity.getDescription();
        pourcentageReduction = promotionEntity.getPourcentageReduction();
        dateDebut = promotionEntity.getDateDebut();
        dateFin = promotionEntity.getDateFin();
        produit = entityToModel( promotionEntity.getProduit() );

        Promotion promotion = new Promotion( id, nom, description, pourcentageReduction, dateDebut, dateFin, produit );

        return promotion;
    }

    protected Set<Promotion> promotionEntitySetToPromotionSet(Set<PromotionEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Promotion> set1 = new LinkedHashSet<Promotion>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PromotionEntity promotionEntity : set ) {
            set1.add( promotionEntityToPromotion( promotionEntity ) );
        }

        return set1;
    }

    protected Categorie categorieEntityToCategorie(CategorieEntity categorieEntity) {
        if ( categorieEntity == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String description = null;

        id = categorieEntity.getId();
        nom = categorieEntity.getNom();
        description = categorieEntity.getDescription();

        Categorie categorie = new Categorie( id, nom, description );

        return categorie;
    }

    protected ProductResponseCategorie categorieToProductResponseCategorie(Categorie categorie) {
        if ( categorie == null ) {
            return null;
        }

        ProductResponseCategorie productResponseCategorie = new ProductResponseCategorie();

        productResponseCategorie.setId( categorie.id() );
        productResponseCategorie.setNom( categorie.nom() );
        productResponseCategorie.setDescription( categorie.description() );

        return productResponseCategorie;
    }

    protected ProductResponsePromotionsInner promotionToProductResponsePromotionsInner(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        ProductResponsePromotionsInner productResponsePromotionsInner = new ProductResponsePromotionsInner();

        productResponsePromotionsInner.setDateDebut( map( promotion.dateDebut() ) );
        productResponsePromotionsInner.setDateFin( map( promotion.dateFin() ) );

        return productResponsePromotionsInner;
    }

    protected List<ProductResponsePromotionsInner> promotionSetToProductResponsePromotionsInnerList(Set<Promotion> set) {
        if ( set == null ) {
            return null;
        }

        List<ProductResponsePromotionsInner> list = new ArrayList<ProductResponsePromotionsInner>( set.size() );
        for ( Promotion promotion : set ) {
            list.add( promotionToProductResponsePromotionsInner( promotion ) );
        }

        return list;
    }

    protected ProductResponseCategorie categorieEntityToProductResponseCategorie(CategorieEntity categorieEntity) {
        if ( categorieEntity == null ) {
            return null;
        }

        ProductResponseCategorie productResponseCategorie = new ProductResponseCategorie();

        productResponseCategorie.setId( categorieEntity.getId() );
        productResponseCategorie.setNom( categorieEntity.getNom() );
        productResponseCategorie.setDescription( categorieEntity.getDescription() );

        return productResponseCategorie;
    }

    protected ProductResponsePromotionsInner promotionEntityToProductResponsePromotionsInner(PromotionEntity promotionEntity) {
        if ( promotionEntity == null ) {
            return null;
        }

        ProductResponsePromotionsInner productResponsePromotionsInner = new ProductResponsePromotionsInner();

        productResponsePromotionsInner.setDateDebut( map( promotionEntity.getDateDebut() ) );
        productResponsePromotionsInner.setDateFin( map( promotionEntity.getDateFin() ) );

        return productResponsePromotionsInner;
    }

    protected List<ProductResponsePromotionsInner> promotionEntitySetToProductResponsePromotionsInnerList(Set<PromotionEntity> set) {
        if ( set == null ) {
            return null;
        }

        List<ProductResponsePromotionsInner> list = new ArrayList<ProductResponsePromotionsInner>( set.size() );
        for ( PromotionEntity promotionEntity : set ) {
            list.add( promotionEntityToProductResponsePromotionsInner( promotionEntity ) );
        }

        return list;
    }
}
