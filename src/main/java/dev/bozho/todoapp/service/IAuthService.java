package dev.bozho.todoapp.service;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.payload.CredentialsDTO;

public interface IAuthService {

    String register(CredentialsDTO credentials) throws UserException, TokenException;

    String authenticate(CredentialsDTO credentials) throws UserException;

}
