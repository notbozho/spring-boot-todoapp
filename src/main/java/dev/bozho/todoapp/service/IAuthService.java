package dev.bozho.todoapp.service;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.payload.CredentialsDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    ResponseEntity<?> register(CredentialsDTO credentials) throws UserException, TokenException;

    ResponseEntity<?> authenticate(CredentialsDTO credentials) throws UserException;

    ResponseEntity<?> refreshToken(String token);

    ResponseEntity<?> logout(String token);

    ResponseEntity<?> resetPassword(String email) throws UserException, TokenException;

    ResponseEntity<?> changePassword(String token, String password) throws UserException, TokenException;
}
