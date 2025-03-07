package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * CommandeResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class CommandeResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long utilisateurId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dateCommande;

  private String statut;

  public CommandeResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * L'ID unique de la commande
   * @return id
   */
  
  @Schema(name = "id", description = "L'ID unique de la commande", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CommandeResponse utilisateurId(Long utilisateurId) {
    this.utilisateurId = utilisateurId;
    return this;
  }

  /**
   * L'ID de l'utilisateur ayant passé la commande
   * @return utilisateurId
   */
  
  @Schema(name = "utilisateurId", description = "L'ID de l'utilisateur ayant passé la commande", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("utilisateurId")
  public Long getUtilisateurId() {
    return utilisateurId;
  }

  public void setUtilisateurId(Long utilisateurId) {
    this.utilisateurId = utilisateurId;
  }

  public CommandeResponse dateCommande(OffsetDateTime dateCommande) {
    this.dateCommande = dateCommande;
    return this;
  }

  /**
   * La date de la commande
   * @return dateCommande
   */
  @Valid 
  @Schema(name = "dateCommande", description = "La date de la commande", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateCommande")
  public OffsetDateTime getDateCommande() {
    return dateCommande;
  }

  public void setDateCommande(OffsetDateTime dateCommande) {
    this.dateCommande = dateCommande;
  }

  public CommandeResponse statut(String statut) {
    this.statut = statut;
    return this;
  }

  /**
   * Le statut de la commande
   * @return statut
   */
  
  @Schema(name = "statut", description = "Le statut de la commande", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statut")
  public String getStatut() {
    return statut;
  }

  public void setStatut(String statut) {
    this.statut = statut;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandeResponse commandeResponse = (CommandeResponse) o;
    return Objects.equals(this.id, commandeResponse.id) &&
        Objects.equals(this.utilisateurId, commandeResponse.utilisateurId) &&
        Objects.equals(this.dateCommande, commandeResponse.dateCommande) &&
        Objects.equals(this.statut, commandeResponse.statut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, utilisateurId, dateCommande, statut);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommandeResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    utilisateurId: ").append(toIndentedString(utilisateurId)).append("\n");
    sb.append("    dateCommande: ").append(toIndentedString(dateCommande)).append("\n");
    sb.append("    statut: ").append(toIndentedString(statut)).append("\n");
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

