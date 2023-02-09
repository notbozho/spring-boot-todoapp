package dev.bozho.todoapp.service;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.model.EmailToken;
import dev.bozho.todoapp.model.User;

import java.util.Optional;

public interface IEmailTokenService {

    void saveEmailToken(EmailToken token);

    Optional<EmailToken> getToken(String token);

    String generateToken(User user) throws TokenException;

    String confirmToken(String token) throws TokenException;

    void setConfirmedAt(String token);

}
