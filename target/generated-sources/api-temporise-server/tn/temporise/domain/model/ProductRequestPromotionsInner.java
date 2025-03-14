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
 * Promotion appliquée au produit
 */

@Schema(name = "ProductRequest_promotions_inner", description = "Promotion appliquée au produit")
@JsonTypeName("ProductRequest_promotions_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class ProductRequestPromotionsInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long promotionId;

  public ProductRequestPromotionsInner promotionId(Long promotionId) {
    this.promotionId = promotionId;
    return this;
  }

  /**
   * L'ID de la promotion liée au produit
   * @return promotionId
   */
  
  @Schema(name = "promotionId", description = "L'ID de la promotion liée au produit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("promotionId")
  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductRequestPromotionsInner productRequestPromotionsInner = (ProductRequestPromotionsInner) o;
    return Objects.equals(this.promotionId, productRequestPromotionsInner.promotionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(promotionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductRequestPromotionsInner {\n");
    sb.append("    promotionId: ").append(toIndentedString(promotionId)).append("\n");
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

