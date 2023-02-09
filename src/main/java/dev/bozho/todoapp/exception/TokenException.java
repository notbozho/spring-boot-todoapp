package dev.bozho.todoapp.exception;

public class TokenException extends Exception {
    private static final long serialVersionUID = 1L;

    public TokenException() {
        super();
    }

    public TokenException(String message) {
        super(message);
    }
}
