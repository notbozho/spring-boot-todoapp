package dev.bozho.todoapp.repository;

import dev.bozho.todoapp.model.RefreshToken;
import dev.bozho.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);

    @Modifying
    @Query("DELETE FROM RefreshToken t WHERE t.expiry <= ?1")
    void deleteAllExpiredSince(Instant now);
}
