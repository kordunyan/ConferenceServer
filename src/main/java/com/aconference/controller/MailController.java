package com.aconference.controller;

import com.aconference.domain.Token;
import com.aconference.domain.User;
import com.aconference.service.UserService;
import com.aconference.service.google.entity.GoogleException;
import com.aconference.service.mail.UserMailProcessor;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Autowired
    private UserService userService;

    @Autowired
    private UserMailProcessor userMailProcessor;

    @GetMapping("/connect")
    public void testConnection(@RequestParam("token") String token) {

        try {
            userMailProcessor.processUsersMails();
        } catch (GoogleException e) {
            e.printStackTrace();
        }

//        try {
//            GoogleCredential credential = new GoogleCredential().setAccessToken(token);
//            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//
//            Gmail gmail = new Gmail.Builder(httpTransport, JSON_FACTORY, credential).build();
//
//            ListMessagesResponse messages = gmail.users().messages().list("me").execute();
//
//            List<Message> messagesList = new ArrayList<>(messages.getMessages());
//
////            while (messages.getNextPageToken() != null) {
////                messages = gmail.users().messages().list("me").setPageToken(messages.getNextPageToken()).execute();
////                messagesList.addAll(messages.getMessages());
////            }
//
//            for (Message message : messagesList) {
//                System.out.println(message.toPrettyString());
//            }
//
//
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}
