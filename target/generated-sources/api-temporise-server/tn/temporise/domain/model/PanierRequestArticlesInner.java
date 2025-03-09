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
 * PanierRequestArticlesInner
 */

@JsonTypeName("PanierRequest_articles_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class PanierRequestArticlesInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long articleId;

  private Integer quantite;

  public PanierRequestArticlesInner articleId(Long articleId) {
    this.articleId = articleId;
    return this;
  }

  /**
   * L'ID de l'article dans le panier
   * @return articleId
   */
  
  @Schema(name = "articleId", description = "L'ID de l'article dans le panier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("articleId")
  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }

  public PanierRequestArticlesInner quantite(Integer quantite) {
    this.quantite = quantite;
    return this;
  }

  /**
   * La quantité de l'article dans le panier
   * @return quantite
   */
  
  @Schema(name = "quantite", description = "La quantité de l'article dans le panier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantite")
  public Integer getQuantite() {
    return quantite;
  }

  public void setQuantite(Integer quantite) {
    this.quantite = quantite;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PanierRequestArticlesInner panierRequestArticlesInner = (PanierRequestArticlesInner) o;
    return Objects.equals(this.articleId, panierRequestArticlesInner.articleId) &&
        Objects.equals(this.quantite, panierRequestArticlesInner.quantite);
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleId, quantite);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PanierRequestArticlesInner {\n");
    sb.append("    articleId: ").append(toIndentedString(articleId)).append("\n");
    sb.append("    quantite: ").append(toIndentedString(quantite)).append("\n");
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

