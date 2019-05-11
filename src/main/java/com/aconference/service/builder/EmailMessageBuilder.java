package com.aconference.service.builder;

import com.aconference.domain.InvitationFile;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.model.Message;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class EmailMessageBuilder {

    private static final String CONTENT_TYPE_HTML = "text/html; charset=utf-8";
    private static final String CONTENT_TYPE_TEXT = "text/plain; charset=utf-8";

    private String from;
    private String subject;
    private String content;
    private String contentType;
    private List<String> to = new ArrayList<>();
    private List<MimeBodyPart> attachments = new ArrayList<>();


    public EmailMessageBuilder from(String from) {
        this.from = from;
        return this;
    }

    public EmailMessageBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailMessageBuilder text(String text) {
        return this.content(text, CONTENT_TYPE_TEXT);
    }

    public EmailMessageBuilder html(String html) {
        return this.content(html, CONTENT_TYPE_HTML);
    }

    public EmailMessageBuilder content(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
        return this;
    }

    public EmailMessageBuilder addTo(String emlilTo) {
        this.to.add(emlilTo);
        return this;
    }

    public EmailMessageBuilder addTo(Collection<String> toList) {
        this.to.addAll(toList);
        return this;
    }

    public EmailMessageBuilder setTo(String to) {
        this.to = new ArrayList<>();
        this.to.add(to);
        return this;
    }

    public EmailMessageBuilder setTo(Collection<String> toList) {
        this.to = new ArrayList<>();
        this.to.addAll(toList);
        return this;
    }

    public EmailMessageBuilder attach(File file, String fileName) throws MessagingException {
        this.attachments.add(createAttachmentPart(file, fileName));
        return this;
    }

    public EmailMessageBuilder attach(String filePath, String fileName) throws MessagingException {
        this.attachments.add(createAttachmentPart(new File(filePath), fileName));
        return this;
    }

    private MimeBodyPart createAttachmentPart(File file, String fileName) throws MessagingException {
        MimeBodyPart result = new MimeBodyPart();
        DataSource source = new FileDataSource(file);
        result.setDataHandler(new DataHandler(source));
        result.setFileName(fileName);
        return result;
    }

    public Message build() throws IOException, MessagingException {
        return new Builder().build();
    }


    private class Builder {

        public Message build() throws MessagingException, IOException {
            return createMessageWithEmail(createEmail());
        }

        private Message createMessageWithEmail(MimeMessage email) throws IOException, MessagingException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
            Message message = new Message();
            message.setRaw(encodedEmail);
            return message;
        }

        private MimeMessage createEmail() throws MessagingException {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(from));
            email.setSubject(subject);
            for (String emailTo : to) {
                email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(emailTo));
            }

            MimeBodyPart bodyContent = new MimeBodyPart();
            bodyContent.setContent(content, contentType);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyContent);

            for (MimeBodyPart attachemnt : attachments) {
                multipart.addBodyPart(attachemnt);
            }

            email.setContent(multipart);
            return email;
        }

    }

}
