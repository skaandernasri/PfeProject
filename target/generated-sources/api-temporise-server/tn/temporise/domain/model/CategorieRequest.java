package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CategorieRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class CategorieRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nom;

  private String description;

  public CategorieRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CategorieRequest(String nom) {
    this.nom = nom;
  }

  public CategorieRequest nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Nom du categorie
   * @return nom
   */
  @NotNull 
  @Schema(name = "nom", description = "Nom du categorie", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public CategorieRequest description(String description) {
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
    CategorieRequest categorieRequest = (CategorieRequest) o;
    return Objects.equals(this.nom, categorieRequest.nom) &&
        Objects.equals(this.description, categorieRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nom, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategorieRequest {\n");
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

