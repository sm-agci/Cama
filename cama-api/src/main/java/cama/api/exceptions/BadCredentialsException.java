package cama.api.exceptions;

public class BadCredentialsException extends RuntimeException{
    public BadCredentialsException(String message) {
        super(message);
    }
}
