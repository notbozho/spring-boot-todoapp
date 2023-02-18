package dev.bozho.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    private Instant expiry;

    public PasswordResetToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expiry = Instant.now().plusSeconds(60 * 15);
    }

}
