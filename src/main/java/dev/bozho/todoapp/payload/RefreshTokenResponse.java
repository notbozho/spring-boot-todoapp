package dev.bozho.todoapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenResponse {

    private String token;
    private String refreshToken;

}
