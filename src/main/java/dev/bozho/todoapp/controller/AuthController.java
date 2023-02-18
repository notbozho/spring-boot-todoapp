package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.payload.CredentialsDTO;
import dev.bozho.todoapp.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@Valid @RequestBody CredentialsDTO user) throws UserException, TokenException {

        return authService.register(user);
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@Valid @RequestBody CredentialsDTO user) throws UserException {
        return authService.authenticate(user);
    }

    @PostMapping(path = "refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody String token) {
        return authService.refreshToken(token);
    }

    @PostMapping(path = "resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {
        return authService.resetPassword(email);
    }

    @PostMapping(path = "changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        return authService.changePassword(token, password);
    }
}
