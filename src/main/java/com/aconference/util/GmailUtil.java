package com.aconference.util;

import com.aconference.domain.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GmailUtil {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Gmail buildGmail(User user) throws GeneralSecurityException, IOException {
        GoogleCredential credential = new GoogleCredential().setAccessToken(user.getToken().getOauthAccessToken());
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential).build();
    }

}
