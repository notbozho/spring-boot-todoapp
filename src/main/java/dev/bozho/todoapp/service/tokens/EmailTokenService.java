package dev.bozho.todoapp.service.tokens;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.model.EmailToken;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.repository.EmailTokenRepository;
import dev.bozho.todoapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailTokenService implements IEmailTokenService {

    private final EmailTokenRepository emailTokenRepository;

    private final UserRepository userRepository;

    @Override
    public void saveEmailToken(EmailToken token) {
        emailTokenRepository.save(token);
    }

    @Override
    public Optional<EmailToken> getToken(String token) {
        return emailTokenRepository.findByToken(token);
    }

    @Override
    public String generateToken(User user) throws TokenException {
        String token = UUID.randomUUID().toString();

        if (user.getEnabled()) {
            throw new TokenException("User already has email confirmed");
        }

        EmailToken emailToken = new EmailToken(
                token,
                Instant.now(),
                Instant.now().plusSeconds(15 * 60),
                user
        );

        saveEmailToken(emailToken);

        return token;
    }

    @Override
    @Transactional
    public String confirmToken(String token) throws TokenException {
        EmailToken emailToken = emailTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException("Token not found"));

        if (emailToken.getConfirmedAt() != null || emailToken.getUser().getEnabled()) {
            throw new IllegalStateException("Email is already confirmed");
        }

        Instant expiredAt = emailToken.getExpiresAt();

        if (expiredAt.isBefore(Instant.now())) {
            throw new TokenException("Token expired");
        }

        setConfirmedAt(token);

        userRepository.enableUser(emailToken.getUser().getEmail());

        return "Account " + emailToken.getUser().getEmail() + " confirmed";
    }

    @Override
    public void setConfirmedAt(String token) {
        emailTokenRepository.updateConfirmedAt(
                token, Instant.now());
    }
}
