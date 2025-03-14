package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tn.temporise.domain.model.PanierRequestArticlesInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PanierResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class PanierResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long utilisateurId;

  @Valid
  private List<@Valid PanierRequestArticlesInner> articles = new ArrayList<>();

  public PanierResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * L'ID unique du panier
   * @return id
   */
  
  @Schema(name = "id", description = "L'ID unique du panier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PanierResponse utilisateurId(Long utilisateurId) {
    this.utilisateurId = utilisateurId;
    return this;
  }

  /**
   * L'ID de l'utilisateur propriétaire du panier
   * @return utilisateurId
   */
  
  @Schema(name = "utilisateurId", description = "L'ID de l'utilisateur propriétaire du panier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("utilisateurId")
  public Long getUtilisateurId() {
    return utilisateurId;
  }

  public void setUtilisateurId(Long utilisateurId) {
    this.utilisateurId = utilisateurId;
  }

  public PanierResponse articles(List<@Valid PanierRequestArticlesInner> articles) {
    this.articles = articles;
    return this;
  }

  public PanierResponse addArticlesItem(PanierRequestArticlesInner articlesItem) {
    if (this.articles == null) {
      this.articles = new ArrayList<>();
    }
    this.articles.add(articlesItem);
    return this;
  }

  /**
   * Get articles
   * @return articles
   */
  @Valid 
  @Schema(name = "articles", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("articles")
  public List<@Valid PanierRequestArticlesInner> getArticles() {
    return articles;
  }

  public void setArticles(List<@Valid PanierRequestArticlesInner> articles) {
    this.articles = articles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PanierResponse panierResponse = (PanierResponse) o;
    return Objects.equals(this.id, panierResponse.id) &&
        Objects.equals(this.utilisateurId, panierResponse.utilisateurId) &&
        Objects.equals(this.articles, panierResponse.articles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, utilisateurId, articles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PanierResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    utilisateurId: ").append(toIndentedString(utilisateurId)).append("\n");
    sb.append("    articles: ").append(toIndentedString(articles)).append("\n");
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

