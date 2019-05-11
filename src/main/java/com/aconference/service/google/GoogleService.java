package com.aconference.service.google;

import com.aconference.domain.Token;
import com.aconference.domain.User;
import com.aconference.service.google.entity.GoogleException;
import com.aconference.service.google.entity.RefreshTokenResponse;
import com.aconference.service.http.GoogleHttpService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class GoogleService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Autowired
    private GoogleHttpService googleHttpService;

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }

    public GoogleCredential createGoogleCredentials(User user) {
        return new GoogleCredential().setAccessToken(user.getToken().getOauthAccessToken());
    }

    public void refreshUserToken(User user) throws GoogleException {
        Token token = user.getToken();
        try {
            RefreshTokenResponse response = googleHttpService.refreshToken(token.getOauthRefreshToken());
            if (StringUtils.isEmpty(response.getAccessToken())) {
                throw new GoogleException(String.format("Failed to get access token during refreshing token for user: %s",
                        user.getEmail()));
            }
            token.setOauthAccessToken(response.getAccessToken());
            token.setOauthExpiresAt(response.getExpiresAt());
        } catch (IOException e) {
            throw new GoogleException(String.format("Failed to refresh token for user, %s", user.getEmail()), e);
        }
    }


}
