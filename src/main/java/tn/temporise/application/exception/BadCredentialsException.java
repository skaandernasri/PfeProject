package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class BadCredentialsException extends RuntimeException {
    private final String code;
    public BadCredentialsException(String message,String code) {
        super(message);
        this.code=code;
    }


}
