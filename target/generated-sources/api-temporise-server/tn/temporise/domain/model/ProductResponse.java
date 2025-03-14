package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tn.temporise.domain.model.ProductResponseCategorie;
import tn.temporise.domain.model.ProductResponsePromotionsInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class ProductResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String nom;

  private String description;

  private Double prix;

  private Integer stock;

  private ProductResponseCategorie categorie;

  @Valid
  private List<@Valid ProductResponsePromotionsInner> promotions = new ArrayList<>();

  public ProductResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductResponse(Long id, String nom, Double prix, Integer stock, ProductResponseCategorie categorie) {
    this.id = id;
    this.nom = nom;
    this.prix = prix;
    this.stock = stock;
    this.categorie = categorie;
  }

  public ProductResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * L'ID unique du produit
   * @return id
   */
  @NotNull 
  @Schema(name = "id", description = "L'ID unique du produit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProductResponse nom(String nom) {
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

  public ProductResponse description(String description) {
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

  public ProductResponse prix(Double prix) {
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

  public ProductResponse stock(Integer stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Quantité du produit en stock
   * @return stock
   */
  @NotNull 
  @Schema(name = "stock", description = "Quantité du produit en stock", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stock")
  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public ProductResponse categorie(ProductResponseCategorie categorie) {
    this.categorie = categorie;
    return this;
  }

  /**
   * Get categorie
   * @return categorie
   */
  @NotNull @Valid 
  @Schema(name = "categorie", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("categorie")
  public ProductResponseCategorie getCategorie() {
    return categorie;
  }

  public void setCategorie(ProductResponseCategorie categorie) {
    this.categorie = categorie;
  }

  public ProductResponse promotions(List<@Valid ProductResponsePromotionsInner> promotions) {
    this.promotions = promotions;
    return this;
  }

  public ProductResponse addPromotionsItem(ProductResponsePromotionsInner promotionsItem) {
    if (this.promotions == null) {
      this.promotions = new ArrayList<>();
    }
    this.promotions.add(promotionsItem);
    return this;
  }

  /**
   * Liste des promotions appliquées au produit
   * @return promotions
   */
  @Valid 
  @Schema(name = "promotions", description = "Liste des promotions appliquées au produit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("promotions")
  public List<@Valid ProductResponsePromotionsInner> getPromotions() {
    return promotions;
  }

  public void setPromotions(List<@Valid ProductResponsePromotionsInner> promotions) {
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
    ProductResponse productResponse = (ProductResponse) o;
    return Objects.equals(this.id, productResponse.id) &&
        Objects.equals(this.nom, productResponse.nom) &&
        Objects.equals(this.description, productResponse.description) &&
        Objects.equals(this.prix, productResponse.prix) &&
        Objects.equals(this.stock, productResponse.stock) &&
        Objects.equals(this.categorie, productResponse.categorie) &&
        Objects.equals(this.promotions, productResponse.promotions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, description, prix, stock, categorie, promotions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

