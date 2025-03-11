package tn.temporise.application.exception;

public class GoogleException extends RuntimeException {
    private final String code;
    public GoogleException(String message) {
        super(message);
        this.code = "3000";
    }
    public GoogleException(String message,String code) {
        super(message);
         this.code = code;
    }

}
