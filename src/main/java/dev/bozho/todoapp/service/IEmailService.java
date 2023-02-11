package dev.bozho.todoapp.service;

public interface IEmailService {

    void send(String to, String subject, String content);

    void sendEmailConfirmation(String to, String token);

}
