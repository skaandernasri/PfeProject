package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class ProductNotFound extends RuntimeException {
    private final String code;
    public ProductNotFound(String message) {
        super(message);
        this.code="4004";
    }
}
