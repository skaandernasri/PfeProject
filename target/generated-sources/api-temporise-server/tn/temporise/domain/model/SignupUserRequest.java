package tn.temporise.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SignupUserRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.9.0")
public class SignupUserRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String email;

  private String password;

  private String nom;

  @Valid
  private List<String> roles = new ArrayList<>();

  public SignupUserRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SignupUserRequest(String email, String password, String nom, List<String> roles) {
    this.email = email;
    this.password = password;
    this.nom = nom;
    this.roles = roles;
  }

  public SignupUserRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @NotNull @jakarta.validation.constraints.Email 
  @Schema(name = "email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public SignupUserRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  @NotNull 
  @Schema(name = "password", example = "MotDePasseSecure123!", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public SignupUserRequest nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Get nom
   * @return nom
   */
  @NotNull 
  @Schema(name = "nom", example = "Jean Dupont", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public SignupUserRequest roles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  public SignupUserRequest addRolesItem(String rolesItem) {
    if (this.roles == null) {
      this.roles = new ArrayList<>();
    }
    this.roles.add(rolesItem);
    return this;
  }

  /**
   * Get roles
   * @return roles
   */
  @NotNull 
  @Schema(name = "roles", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("roles")
  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SignupUserRequest signupUserRequest = (SignupUserRequest) o;
    return Objects.equals(this.email, signupUserRequest.email) &&
        Objects.equals(this.password, signupUserRequest.password) &&
        Objects.equals(this.nom, signupUserRequest.nom) &&
        Objects.equals(this.roles, signupUserRequest.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, nom, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SignupUserRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append("*").append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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

