package cama.api.exceptions;

public class ExternalSystemException extends RuntimeException{
    public ExternalSystemException(String message, Throwable t) {
        super(message, t);
    }
}
