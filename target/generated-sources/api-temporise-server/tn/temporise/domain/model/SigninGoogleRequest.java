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
 * SigninGoogleRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class SigninGoogleRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String idToken;

  public SigninGoogleRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SigninGoogleRequest(String idToken) {
    this.idToken = idToken;
  }

  public SigninGoogleRequest idToken(String idToken) {
    this.idToken = idToken;
    return this;
  }

  /**
   * Get idToken
   * @return idToken
   */
  @NotNull 
  @Schema(name = "idToken", example = "google_token_example", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("idToken")
  public String getIdToken() {
    return idToken;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SigninGoogleRequest signinGoogleRequest = (SigninGoogleRequest) o;
    return Objects.equals(this.idToken, signinGoogleRequest.idToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SigninGoogleRequest {\n");
    sb.append("    idToken: ").append(toIndentedString(idToken)).append("\n");
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

