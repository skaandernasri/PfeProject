package tn.temporise.application.exception;

public class NonLocalProviderException extends RuntimeException {
    public NonLocalProviderException(String message) {
        super(message);
    }
}