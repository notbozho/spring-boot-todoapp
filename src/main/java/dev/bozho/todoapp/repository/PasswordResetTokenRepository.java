package dev.bozho.todoapp.repository;

import dev.bozho.todoapp.model.PasswordResetToken;
import dev.bozho.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryLessThan(Instant now);

    void deleteByExpiryLessThan(Instant now);

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiry <= ?1")
    void deleteAllExpiredSince(Instant now);
}
