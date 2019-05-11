package com.aconference.service.http;

import com.aconference.config.security.oauth2.GoogleProperties;
import com.aconference.service.google.entity.RefreshTokenRequest;
import com.aconference.service.google.entity.RefreshTokenResponse;
import com.aconference.service.google.entity.builder.RefreshTokenResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class GoogleHttpService extends HttpService {

    @Autowired
    private GoogleProperties googleProperties;

    public RefreshTokenResponse refreshToken(String refreshToken) throws IOException {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.withDefaultGrantType()
                .setClientId(googleProperties.getClientId())
                .setClientSecret(googleProperties.getClientSecret())
                .setRefreshToken(refreshToken);

        Map<String, String> resultMap = postJsonReponse(refreshTokenRequest.toList(),
                googleProperties.getRefreshTokenUri());
        return RefreshTokenResponseBuilder.buildFromMap(resultMap);
    }

}
