package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class NonLocalProviderException extends RuntimeException {
    private final String code;
    public NonLocalProviderException(String message,String code) {
        super(message);
        this.code=code;
    }
}