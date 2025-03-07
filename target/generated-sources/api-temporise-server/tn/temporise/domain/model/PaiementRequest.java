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
 * PaiementRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class PaiementRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long commandeId;

  private Float montant;

  private String methode;

  public PaiementRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PaiementRequest(Long commandeId, Float montant, String methode) {
    this.commandeId = commandeId;
    this.montant = montant;
    this.methode = methode;
  }

  public PaiementRequest commandeId(Long commandeId) {
    this.commandeId = commandeId;
    return this;
  }

  /**
   * L'ID de la commande associée
   * @return commandeId
   */
  @NotNull 
  @Schema(name = "commandeId", description = "L'ID de la commande associée", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commandeId")
  public Long getCommandeId() {
    return commandeId;
  }

  public void setCommandeId(Long commandeId) {
    this.commandeId = commandeId;
  }

  public PaiementRequest montant(Float montant) {
    this.montant = montant;
    return this;
  }

  /**
   * Le montant du paiement
   * @return montant
   */
  @NotNull 
  @Schema(name = "montant", description = "Le montant du paiement", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("montant")
  public Float getMontant() {
    return montant;
  }

  public void setMontant(Float montant) {
    this.montant = montant;
  }

  public PaiementRequest methode(String methode) {
    this.methode = methode;
    return this;
  }

  /**
   * La méthode de paiement utilisée
   * @return methode
   */
  @NotNull 
  @Schema(name = "methode", description = "La méthode de paiement utilisée", requiredMode = Schema.RequiredMode.REQUIRED)
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
    PaiementRequest paiementRequest = (PaiementRequest) o;
    return Objects.equals(this.commandeId, paiementRequest.commandeId) &&
        Objects.equals(this.montant, paiementRequest.montant) &&
        Objects.equals(this.methode, paiementRequest.methode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commandeId, montant, methode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaiementRequest {\n");
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

