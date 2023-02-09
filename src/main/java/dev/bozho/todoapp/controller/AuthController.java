package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.payload.CredentialsDTO;
import dev.bozho.todoapp.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "register")
    public ResponseEntity<String> register(@Valid @RequestBody CredentialsDTO user) throws UserException, TokenException {
        String token = authService.register(user);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping(path = "login")
    public ResponseEntity<String> login(@Valid @RequestBody CredentialsDTO user) throws UserException {
        String token = authService.authenticate(user);

        return ResponseEntity.ok(token);
    }
}
