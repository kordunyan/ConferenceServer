package com.aconference.service.google;


import com.aconference.domain.User;
import com.aconference.repository.TokenRepository;
import com.aconference.service.google.entity.GoogleException;
import com.aconference.service.google.entity.builder.MailBuilder;
import com.aconference.service.google.entity.message.LightMail;
import com.aconference.service.google.entity.message.Mail;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GmailService extends GoogleService {

    private static final int UNAOTHORIZED_STATUS_CODE = 401;
    private static final String OWNER_USER_ID = "me";
    private static final long MESSAGES_PER_PAGE = 10;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ModelMapper mapper;

    public Gmail buildGmail(User user) throws GeneralSecurityException, IOException {
        return new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                getJsonFactory(), createGoogleCredentials(user)).build();
    }

    public boolean isAuthorized(Gmail gmail) throws IOException {
        try {
            gmail.users().getProfile(OWNER_USER_ID).execute();
            return true;
        } catch (GoogleJsonResponseException exception) {
            if (exception.getStatusCode() == UNAOTHORIZED_STATUS_CODE) {
                return false;
            }
            throw exception;
        }
    }

    public Gmail buildGmailAndRefreshToken(User user) throws GoogleException {
        try {
            Gmail gmail = buildGmail(user);
            if (!isAuthorized(gmail)) {
                refreshUserToken(user);
                gmail = buildGmail(user);
                if (!isAuthorized(gmail)) {
                    throw new GoogleException(String.format("Failed to authorize user: %s", user.getEmail()));
                }
                tokenRepository.save(user.getToken());
            }
            return gmail;
        } catch (GoogleException e) {
            throw e;
        } catch (Exception e) {
            throw new GoogleException(String.format("Failed to build gmail for user: %s", user.getEmail()), e);
        }
    }

    @Async("threadPoolEmailSenderExecutor")
    public void sendMail(Gmail gmail, Message message) throws IOException {
        gmail.users().messages().send("me", message).execute();
    }

    public List<LightMail> getNewLightMessages(User user) throws GoogleException {
        try {
            Gmail gmail = buildGmailAndRefreshToken(user);
            List<LightMail> result = new ArrayList<>();
            String pageToken = null;

            while (true) {
                ListMessagesResponse response = getMessages(gmail, pageToken);
                if (CollectionUtils.isEmpty(response.getMessages()) || StringUtils.isEmpty(response.getNextPageToken())) {
                    break;
                }
                pageToken = response.getNextPageToken();
                if (StringUtils.isEmpty(user.getLastMessageId())) {
                    result.addAll(mapper.map(response.getMessages(), LightMail.LIST_TYPE));
                    continue;
                }
                for (Message message : response.getMessages()) {
                    if (message.getId().equals(user.getLastMessageId())) {
                        return result;
                    }
                    result.add(mapper.map(message, LightMail.class));
                }
            }
            return result;
        } catch (IOException e) {
            throw new GoogleException(String.format("Failed go get new messages for user: %s", user.getEmail()), e);
        }
    }

    public List<Mail> getFullMessages(List<LightMail> lightMails, User user) throws GoogleException {
        Gmail gmail = buildGmailAndRefreshToken(user);
        List<Mail> result = new ArrayList<>();
        for (LightMail lightMail : lightMails) {
            try {
                Message fullMessage = gmail.users().messages().get(OWNER_USER_ID, lightMail.getId()).execute();
                result.add(MailBuilder.buildFromMessage(fullMessage));
            } catch (IOException e) {
                throw new GoogleException(String.format("Failed to get message with id: %s, for user: %s",
                        lightMail.getId(), user.getEmail()), e);
            }
        }
        return result;
    }

    private ListMessagesResponse getMessages(Gmail gmail, String pageToken) throws IOException {
        return gmail
                .users()
                .messages()
                .list(OWNER_USER_ID)
                .setMaxResults(MESSAGES_PER_PAGE)
                .setPageToken(pageToken)
                .execute();
    }

    public Optional<LightMail> getLastLightMessage(User user) throws GoogleException {
        try {
            Gmail gmail = buildGmailAndRefreshToken(user);
            ListMessagesResponse response = gmail.users().messages().list(OWNER_USER_ID).setMaxResults(1l).execute();
            if (CollectionUtils.isEmpty(response.getMessages())) {
                return Optional.empty();
            }
            return Optional.of(mapper.map(response.getMessages().get(0), LightMail.class));
        } catch (IOException e) {
            throw new GoogleException(String.format("Failed go get last message for user: %s", user.getEmail()), e);
        }
    }

}
