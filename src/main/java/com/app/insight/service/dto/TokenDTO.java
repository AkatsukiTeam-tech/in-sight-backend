package com.app.insight.service.dto;

public class TokenDTO {

    public TokenDTO() {}

    public TokenDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenDTO(String accessToken, String refreshToken, SecureUserDto user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    private String accessToken;

    private String refreshToken;

    private SecureUserDto user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public SecureUserDto getUser() {
        return user;
    }

    public void setUser(SecureUserDto user) {
        this.user = user;
    }
}
