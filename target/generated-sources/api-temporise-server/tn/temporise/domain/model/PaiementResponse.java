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
 * PaiementResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class PaiementResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long commandeId;

  private Float montant;

  private String methode;

  public PaiementResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * L'ID unique du paiement
   * @return id
   */
  
  @Schema(name = "id", description = "L'ID unique du paiement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PaiementResponse commandeId(Long commandeId) {
    this.commandeId = commandeId;
    return this;
  }

  /**
   * L'ID de la commande associée
   * @return commandeId
   */
  
  @Schema(name = "commandeId", description = "L'ID de la commande associée", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commandeId")
  public Long getCommandeId() {
    return commandeId;
  }

  public void setCommandeId(Long commandeId) {
    this.commandeId = commandeId;
  }

  public PaiementResponse montant(Float montant) {
    this.montant = montant;
    return this;
  }

  /**
   * Le montant du paiement
   * @return montant
   */
  
  @Schema(name = "montant", description = "Le montant du paiement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("montant")
  public Float getMontant() {
    return montant;
  }

  public void setMontant(Float montant) {
    this.montant = montant;
  }

  public PaiementResponse methode(String methode) {
    this.methode = methode;
    return this;
  }

  /**
   * La méthode de paiement utilisée
   * @return methode
   */
  
  @Schema(name = "methode", description = "La méthode de paiement utilisée", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("methode")
  public String getMethode() {
    return methode;
  }

  public void setMethode(String methode) {
    this.methode = methode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaiementResponse paiementResponse = (PaiementResponse) o;
    return Objects.equals(this.id, paiementResponse.id) &&
        Objects.equals(this.commandeId, paiementResponse.commandeId) &&
        Objects.equals(this.montant, paiementResponse.montant) &&
        Objects.equals(this.methode, paiementResponse.methode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, commandeId, montant, methode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaiementResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    commandeId: ").append(toIndentedString(commandeId)).append("\n");
    sb.append("    montant: ").append(toIndentedString(montant)).append("\n");
    sb.append("    methode: ").append(toIndentedString(methode)).append("\n");
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

