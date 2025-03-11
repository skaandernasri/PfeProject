package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {
    private final String code;
    public InternalServerErrorException(String message,String code) {
        super(message);
        this.code=code;
    }
    public InternalServerErrorException(String message) {
        super(message);
        this.code="5000";
    }
}

