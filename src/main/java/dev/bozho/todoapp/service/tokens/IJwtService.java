package dev.bozho.todoapp.service.tokens;

import dev.bozho.todoapp.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJwtService {

    Long extractId(String token);

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(Map<String, Object> claims, User user);

    String generateToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
