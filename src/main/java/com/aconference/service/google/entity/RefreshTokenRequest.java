package com.aconference.service.google.entity;

import com.aconference.service.http.RequestParams;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.HashSet;
import java.util.Set;

public class RefreshTokenRequest extends RequestParams {

    public static final String GRAND_TYPE_REFRESH = "refresh_token";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String GRANT_TYPE = "grant_type";

    public static RefreshTokenRequest withDefaultGrantType() {
        RefreshTokenRequest result = new RefreshTokenRequest();
        result.setGrantType(GRAND_TYPE_REFRESH);
        return result;
    }

    public RefreshTokenRequest setClientId(String clientId) {
        return addParameter(CLIENT_ID, clientId);
    }

    public RefreshTokenRequest setClientSecret(String clientSecret) {
        return addParameter(CLIENT_SECRET, clientSecret);
    }

    public RefreshTokenRequest setRefreshToken(String refreshToken) {
        return addParameter(REFRESH_TOKEN, refreshToken);
    }

    public RefreshTokenRequest setGrantType(String grantType) {
        return addParameter(GRANT_TYPE, grantType);
    }

    public RefreshTokenRequest addParameter(String name, String value) {
        super.addParameter(name, value);
        return this;
    }
}
