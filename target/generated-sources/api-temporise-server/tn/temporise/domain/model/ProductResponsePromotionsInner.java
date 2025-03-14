package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductResponsePromotionsInner
 */

@JsonTypeName("ProductResponse_promotions_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class ProductResponsePromotionsInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long promotionId;

  private String nomPromotion;

  private Double reduction;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dateDebut;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dateFin;

  public ProductResponsePromotionsInner promotionId(Long promotionId) {
    this.promotionId = promotionId;
    return this;
  }

  /**
   * L'ID de la promotion
   * @return promotionId
   */
  
  @Schema(name = "promotionId", description = "L'ID de la promotion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("promotionId")
  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public ProductResponsePromotionsInner nomPromotion(String nomPromotion) {
    this.nomPromotion = nomPromotion;
    return this;
  }

  /**
   * Nom de la promotion
   * @return nomPromotion
   */
  
  @Schema(name = "nomPromotion", description = "Nom de la promotion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomPromotion")
  public String getNomPromotion() {
    return nomPromotion;
  }

  public void setNomPromotion(String nomPromotion) {
    this.nomPromotion = nomPromotion;
  }

  public ProductResponsePromotionsInner reduction(Double reduction) {
    this.reduction = reduction;
    return this;
  }

  /**
   * Pourcentage ou montant de la réduction
   * @return reduction
   */
  
  @Schema(name = "reduction", description = "Pourcentage ou montant de la réduction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reduction")
  public Double getReduction() {
    return reduction;
  }

  public void setReduction(Double reduction) {
    this.reduction = reduction;
  }

  public ProductResponsePromotionsInner dateDebut(OffsetDateTime dateDebut) {
    this.dateDebut = dateDebut;
    return this;
  }

  /**
   * Date début du promotion
   * @return dateDebut
   */
  @Valid 
  @Schema(name = "dateDebut", description = "Date début du promotion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateDebut")
  public OffsetDateTime getDateDebut() {
    return dateDebut;
  }

  public void setDateDebut(OffsetDateTime dateDebut) {
    this.dateDebut = dateDebut;
  }

  public ProductResponsePromotionsInner dateFin(OffsetDateTime dateFin) {
    this.dateFin = dateFin;
    return this;
  }

  /**
   * Date fin du promotion
   * @return dateFin
   */
  @Valid 
  @Schema(name = "dateFin", description = "Date fin du promotion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateFin")
  public OffsetDateTime getDateFin() {
    return dateFin;
  }

  public void setDateFin(OffsetDateTime dateFin) {
    this.dateFin = dateFin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductResponsePromotionsInner productResponsePromotionsInner = (ProductResponsePromotionsInner) o;
    return Objects.equals(this.promotionId, productResponsePromotionsInner.promotionId) &&
        Objects.equals(this.nomPromotion, productResponsePromotionsInner.nomPromotion) &&
        Objects.equals(this.reduction, productResponsePromotionsInner.reduction) &&
        Objects.equals(this.dateDebut, productResponsePromotionsInner.dateDebut) &&
        Objects.equals(this.dateFin, productResponsePromotionsInner.dateFin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(promotionId, nomPromotion, reduction, dateDebut, dateFin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductResponsePromotionsInner {\n");
    sb.append("    promotionId: ").append(toIndentedString(promotionId)).append("\n");
    sb.append("    nomPromotion: ").append(toIndentedString(nomPromotion)).append("\n");
    sb.append("    reduction: ").append(toIndentedString(reduction)).append("\n");
    sb.append("    dateDebut: ").append(toIndentedString(dateDebut)).append("\n");
    sb.append("    dateFin: ").append(toIndentedString(dateFin)).append("\n");
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

