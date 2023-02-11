package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.model.UserRole;
import dev.bozho.todoapp.payload.CredentialsDTO;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.IAuthService;
import dev.bozho.todoapp.util.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final EmailValidator emailValidator;

    private final EmailTokenService emailTokenService;

    private final EmailService emailService;

    @Override
    public String register(CredentialsDTO credentials) throws UserException, TokenException {
        boolean isEmailValid = emailValidator.test(credentials.getEmail());

        if (!isEmailValid) {
            throw new UserException("Email " + credentials.getEmail() + " is not valid");
        }

        Optional<User> opt = userRepository.findUserByEmail(credentials.getEmail());

        if (opt.isPresent()) {
            throw new UserException("User with email " + credentials.getEmail() + " already exists");
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

        return jwtService.generateToken(user);
    }

    @Override
    public String authenticate(CredentialsDTO credentials) throws UserException {
        User user = userRepository.findUserByEmail(credentials.getEmail())
                                  .orElseThrow(() -> new UserException("User with email " + credentials.getEmail() + " does not exist"));

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new UserException("Invalid password");
        }

        return jwtService.generateToken(user);
    }
}
