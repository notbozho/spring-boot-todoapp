package dev.bozho.todoapp.task;

import dev.bozho.todoapp.repository.EmailTokenRepository;
import dev.bozho.todoapp.repository.PasswordResetTokenRepository;
import dev.bozho.todoapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class PurgeExpiredTokensTask {

    private final static Logger LOGGER = LoggerFactory.getLogger(PurgeExpiredTokensTask.class.getName());

    private final EmailTokenRepository emailTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    // every day
    @Scheduled(cron = "0 0 * * *")
    public void purgeExpiredTokens() {
        emailTokenRepository.deleteAllExpiredSince(Instant.now());
        refreshTokenRepository.deleteAllExpiredSince(Instant.now());
        passwordResetTokenRepository.deleteAllExpiredSince(Instant.now());
        LOGGER.info("Purged expired tokens");
    }

}
