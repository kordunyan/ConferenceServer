package com.aconference.service.google.entity.builder;

import com.aconference.service.google.entity.RefreshTokenResponse;

import java.time.Instant;
import java.util.Map;

public final class RefreshTokenResponseBuilder {

    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String EXPIRES_IN_KEY = "expires_in";

    private RefreshTokenResponseBuilder() {
    }

    public static RefreshTokenResponse buildFromMap(Map<String, String> valuesMap) {
        RefreshTokenResponse result = new RefreshTokenResponse();

        if (valuesMap.containsKey(ACCESS_TOKEN_KEY)) {
            result.setAccessToken(valuesMap.get(ACCESS_TOKEN_KEY));
        }

        if (valuesMap.containsKey(EXPIRES_IN_KEY)) {
            int expiresIn = Integer.parseInt(valuesMap.get(EXPIRES_IN_KEY));
            result.setExpiresAt(Instant.now().plusSeconds(expiresIn));
        }
        return result;
    }

}
