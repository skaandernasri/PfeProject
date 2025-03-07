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
 * ArticleRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class ArticleRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String titre;

  private String contenu;

  private String auteur;

  public ArticleRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ArticleRequest(String titre, String contenu, String auteur) {
    this.titre = titre;
    this.contenu = contenu;
    this.auteur = auteur;
  }

  public ArticleRequest titre(String titre) {
    this.titre = titre;
    return this;
  }

  /**
   * Le titre de l'article
   * @return titre
   */
  @NotNull 
  @Schema(name = "titre", description = "Le titre de l'article", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("titre")
  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public ArticleRequest contenu(String contenu) {
    this.contenu = contenu;
    return this;
  }

  /**
   * Le contenu de l'article
   * @return contenu
   */
  @NotNull 
  @Schema(name = "contenu", description = "Le contenu de l'article", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contenu")
  public String getContenu() {
    return contenu;
  }

  public void setContenu(String contenu) {
    this.contenu = contenu;
  }

  public ArticleRequest auteur(String auteur) {
    this.auteur = auteur;
    return this;
  }

  /**
   * L'auteur de l'article
   * @return auteur
   */
  @NotNull 
  @Schema(name = "auteur", description = "L'auteur de l'article", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("auteur")
  public String getAuteur() {
    return auteur;
  }

  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleRequest articleRequest = (ArticleRequest) o;
    return Objects.equals(this.titre, articleRequest.titre) &&
        Objects.equals(this.contenu, articleRequest.contenu) &&
        Objects.equals(this.auteur, articleRequest.auteur);
  }

  @Override
  public int hashCode() {
    return Objects.hash(titre, contenu, auteur);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArticleRequest {\n");
    sb.append("    titre: ").append(toIndentedString(titre)).append("\n");
    sb.append("    contenu: ").append(toIndentedString(contenu)).append("\n");
    sb.append("    auteur: ").append(toIndentedString(auteur)).append("\n");
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

