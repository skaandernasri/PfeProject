package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Détails de la catégorie du produit
 */

@Schema(name = "ProductResponse_categorie", description = "Détails de la catégorie du produit")
@JsonTypeName("ProductResponse_categorie")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class ProductResponseCategorie implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String nom;

  private String description;

  public ProductResponseCategorie id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * L'ID de la catégorie
   * @return id
   */
  
  @Schema(name = "id", description = "L'ID de la catégorie", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProductResponseCategorie nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Nom de la catégorie
   * @return nom
   */
  
  @Schema(name = "nom", description = "Nom de la catégorie", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public ProductResponseCategorie description(String description) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductResponseCategorie productResponseCategorie = (ProductResponseCategorie) o;
    return Objects.equals(this.id, productResponseCategorie.id) &&
        Objects.equals(this.nom, productResponseCategorie.nom) &&
        Objects.equals(this.description, productResponseCategorie.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductResponseCategorie {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

