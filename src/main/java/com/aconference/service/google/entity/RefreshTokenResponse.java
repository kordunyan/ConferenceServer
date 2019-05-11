package com.aconference.service.google.entity;

import java.time.Instant;

public class RefreshTokenResponse {

    private String accessToken;

    private Instant expiresAt;

    public RefreshTokenResponse() {
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public RefreshTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
