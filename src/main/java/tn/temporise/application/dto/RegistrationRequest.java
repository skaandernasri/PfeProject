package tn.temporise.application.dto;

import lombok.Getter;
import lombok.Setter;
import tn.temporise.domain.model.Role;

@Getter
@Setter
public class RegistrationRequest {
    private String nom;
    private String email;
    private String password;
    private Role role;

}
