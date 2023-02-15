package dev.bozho.todoapp.payload;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefreshTokenResponse {

    private String token;
    private String refreshToken;



}
