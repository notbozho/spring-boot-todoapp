package dev.bozho.todoapp.exception;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException() {
        super("Refresh token is invalid or expired");
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
