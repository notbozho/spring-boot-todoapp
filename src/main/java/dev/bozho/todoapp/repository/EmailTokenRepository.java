package dev.bozho.todoapp.repository;

import dev.bozho.todoapp.model.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

    Optional<EmailToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE EmailToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          Instant confirmedAt);


    @Modifying
    @Query("DELETE FROM EmailToken c WHERE c.expiresAt <= ?1")
    void deleteAllExpiredSince(Instant now);
}
