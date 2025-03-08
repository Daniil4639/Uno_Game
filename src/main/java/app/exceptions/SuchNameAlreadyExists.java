package app.exceptions;

public class SuchNameAlreadyExists extends RuntimeException {

    public SuchNameAlreadyExists(String message) {
        super(message);
    }
}