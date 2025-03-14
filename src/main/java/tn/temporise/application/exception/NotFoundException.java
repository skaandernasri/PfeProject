package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String code;
    public NotFoundException(String message) {
        super(message);
        this.code="5004";
    }
}
