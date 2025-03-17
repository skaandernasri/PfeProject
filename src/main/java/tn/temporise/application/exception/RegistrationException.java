package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class RegistrationException extends RuntimeException {
    private final String code = "5647";
    public RegistrationException(String message) {
        super(message);
    }
}
