package dev.bozho.todoapp.service;

public interface IEmailService {

    void send(String to, String email);

    String buildVerificationEmail(String token);

}
