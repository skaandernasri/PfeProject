package tn.temporise.application.exception;

public class LoadKeyException extends RuntimeException {
    private final String code;
    public LoadKeyException(String message,String code) {
        super(message);
        this.code=code;
    }
}
