package com.aconference.service;

import com.aconference.domain.Conference;
import com.aconference.domain.EmailTo;
import com.aconference.domain.InvitationFile;
import com.aconference.domain.User;
import com.aconference.service.builder.EmailMessageBuilder;
import com.aconference.service.google.GmailService;
import com.google.api.services.gmail.Gmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class EmailService {

    @Autowired
    private GmailService gmailService;


    public void sendInvitationEmails(Conference conference, User user) throws MessagingException, IOException, GeneralSecurityException {
        EmailMessageBuilder emailBuilder = new EmailMessageBuilder()
                .from(user.getEmail())
                .subject(conference.getSubject())
                .html(conference.getEmailContent());

        for (InvitationFile invitationFile : conference.getInvitationFiles()) {
            emailBuilder.attach(invitationFile.getSystemFileName(), invitationFile.getOriginalFileName());
        }

        Gmail gmail = gmailService.buildGmail(user);

        for (EmailTo emailTo : conference.getEmailsTo()) {
            emailBuilder.setTo(emailTo.getEmail());
            gmailService.sendMail(gmail, emailBuilder.build());
        }
    }



}
