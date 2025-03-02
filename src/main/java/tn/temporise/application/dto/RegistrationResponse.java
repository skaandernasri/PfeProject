package tn.temporise.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponse {
    private String status;
    private String message;

    public RegistrationResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}