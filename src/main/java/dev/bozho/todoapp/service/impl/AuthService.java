package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.exception.RefreshTokenException;
import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.PasswordResetToken;
import dev.bozho.todoapp.model.RefreshToken;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.model.UserRole;
import dev.bozho.todoapp.payload.CredentialsDTO;
import dev.bozho.todoapp.payload.JwtResponse;
import dev.bozho.todoapp.payload.RefreshTokenResponse;
import dev.bozho.todoapp.repository.PasswordResetTokenRepository;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.IAuthService;
import dev.bozho.todoapp.service.tokens.EmailTokenService;
import dev.bozho.todoapp.service.tokens.JwtService;
import dev.bozho.todoapp.service.tokens.RefreshTokenService;
import dev.bozho.todoapp.util.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final EmailValidator emailValidator;

    private final EmailTokenService emailTokenService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    @Override
    public ResponseEntity<?> register(CredentialsDTO credentials) throws UserException, TokenException {
        boolean isEmailValid = emailValidator.test(credentials.getEmail());

        if (!isEmailValid) {
            return ResponseEntity.badRequest().body("Error: Email is not valid");
        }

        Optional<User> opt = userRepository.findUserByEmail(credentials.getEmail());

        if (opt.isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already taken");
        }

        User user = new User();

        user.setEmail(credentials.getEmail());
        String encodedPassword = passwordEncoder.encode(credentials.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);

        String token = emailTokenService.generateToken(user);

        emailService.sendEmailConfirmation(
                credentials.getEmail(),
                token
        );

        return ResponseEntity.ok().body("User registered successfully. Please check your email for confirmation.");
    }

    @Override
    public ResponseEntity<?> authenticate(CredentialsDTO credentials) throws UserException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String jwt = jwtService.generateToken(user);

        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok().body(new JwtResponse(jwt, refreshToken.getToken(), user.getId(),
                user.getEmail(), roles));

//        User user = userRepository.findUserByEmail(credentials.getEmail())
//                                  .orElseThrow(() -> new UserException("User with email " + credentials.getEmail() + " does not exist"));
//
//        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
//            throw new UserException("Invalid password");
//        }
//
//        return jwtService.generateToken(user);
    }

    @Override
    public ResponseEntity<?> refreshToken(String token) {
        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String jwt = jwtService.generateToken(user);
                    return ResponseEntity.ok(new RefreshTokenResponse(jwt, token));
                })
                .orElseThrow(() -> new RefreshTokenException("Refresh token " + token + " does not exist"));
    }

    @Override
    public ResponseEntity<?> logout(String token) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok("Logged out successfully!");
    }

    @Override
    public ResponseEntity<String> resetPassword(String email){
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, user.get());
            passwordResetTokenRepository.save(passwordResetToken);
            emailService.sendPasswordResetEmail(email, token);
        }

        return ResponseEntity.ok("If the email exists in our database, you will receive an email with instructions on how to reset your password.");
    }

    @Override
    public ResponseEntity<String> changePassword(String token, String password) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        if (passwordResetToken.getExpiry().isBefore(Instant.now()) ) {
            return ResponseEntity.badRequest().body("Token expired");
        }

        User user = passwordResetToken.getUser();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);

        return ResponseEntity.ok("Password changed successfully");
    }
}
