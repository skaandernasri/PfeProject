package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tn.temporise.domain.model.ProductRequestPromotionsInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class ProductRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nom;

  private String description;

  private Double prix;

  private Long stock;

  private Long categorie;

  @Valid
  private List<@Valid ProductRequestPromotionsInner> promotions = new ArrayList<>();

  public ProductRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductRequest(String nom, Double prix, Long stock) {
    this.nom = nom;
    this.prix = prix;
    this.stock = stock;
  }

  public ProductRequest nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Nom du produit
   * @return nom
   */
  @NotNull 
  @Schema(name = "nom", description = "Nom du produit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public ProductRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description du produit
   * @return description
   */
  
  @Schema(name = "description", description = "Description du produit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ProductRequest prix(Double prix) {
    this.prix = prix;
    return this;
  }

  /**
   * Prix du produit
   * @return prix
   */
  @NotNull 
  @Schema(name = "prix", description = "Prix du produit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("prix")
  public Double getPrix() {
    return prix;
  }

  public void setPrix(Double prix) {
    this.prix = prix;
  }

  public ProductRequest stock(Long stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Quantité du produit
   * @return stock
   */
  @NotNull 
  @Schema(name = "stock", description = "Quantité du produit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stock")
  public Long getStock() {
    return stock;
  }

  public void setStock(Long stock) {
    this.stock = stock;
  }

  public ProductRequest categorie(Long categorie) {
    this.categorie = categorie;
    return this;
  }

  /**
   * Catégorie du produit
   * @return categorie
   */
  
  @Schema(name = "categorie", description = "Catégorie du produit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categorie")
  public Long getCategorie() {
    return categorie;
  }

  public void setCategorie(Long categorie) {
    this.categorie = categorie;
  }

  public ProductRequest promotions(List<@Valid ProductRequestPromotionsInner> promotions) {
    this.promotions = promotions;
    return this;
  }

  public ProductRequest addPromotionsItem(ProductRequestPromotionsInner promotionsItem) {
    if (this.promotions == null) {
      this.promotions = new ArrayList<>();
    }
    this.promotions.add(promotionsItem);
    return this;
  }

  /**
   * Get promotions
   * @return promotions
   */
  @Valid 
  @Schema(name = "promotions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("promotions")
  public List<@Valid ProductRequestPromotionsInner> getPromotions() {
    return promotions;
  }

  public void setPromotions(List<@Valid ProductRequestPromotionsInner> promotions) {
    this.promotions = promotions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductRequest productRequest = (ProductRequest) o;
    return Objects.equals(this.nom, productRequest.nom) &&
        Objects.equals(this.description, productRequest.description) &&
        Objects.equals(this.prix, productRequest.prix) &&
        Objects.equals(this.stock, productRequest.stock) &&
        Objects.equals(this.categorie, productRequest.categorie) &&
        Objects.equals(this.promotions, productRequest.promotions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nom, description, prix, stock, categorie, promotions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductRequest {\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    prix: ").append(toIndentedString(prix)).append("\n");
    sb.append("    stock: ").append(toIndentedString(stock)).append("\n");
    sb.append("    categorie: ").append(toIndentedString(categorie)).append("\n");
    sb.append("    promotions: ").append(toIndentedString(promotions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

