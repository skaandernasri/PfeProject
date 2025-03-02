package tn.temporise.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private String status;
    private String message;
    private String token;
    private String email;

    public AuthenticationResponse(String status, String message, String token, String email) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.email = email;
    }
}