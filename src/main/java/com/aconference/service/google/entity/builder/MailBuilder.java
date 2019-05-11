package com.aconference.service.google.entity.builder;

import com.aconference.service.google.entity.message.Mail;
import com.aconference.service.google.entity.message.MessageHeader;
import com.aconference.service.google.entity.message.MimeType;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import com.google.api.services.gmail.model.MessagePartHeader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MailBuilder {

    private MailBuilder() {
    }


    public static Mail buildFromMessage(Message message) {
        Mail mail = new Mail();
        mail.setId(message.getId());
        mail.setThreadId(message.getThreadId());
        MessagePart payload = message.getPayload();
        message.getLabelIds().forEach(mail::addLabel);
        fillFromHeadrs(mail, payload);
        fillFromParts(mail, payload);
        return mail;
    }

    private static void fillFromParts(Mail mail, MessagePart payload) {
        if (StringUtils.isNotEmpty(payload.getBody().getData())) {
            mail.addBodyContent(decodeString(payload.getBody()));
        }
        if (CollectionUtils.isEmpty(payload.getParts())) {
            return;
        }
        for (MessagePart part : payload.getParts()) {
            if (StringUtils.isNotEmpty(part.getFilename())) {
                fillAttachment(mail, part);
            } else {
                fillTextPlainBodyContent(mail, part);
            }
        }
    }

    private static void fillTextPlainBodyContent(Mail mail, MessagePart contentPart) {
        if (MimeType.isHtml(contentPart.getMimeType())) {
            return;
        }
        if (StringUtils.isNotEmpty(contentPart.getBody().getData())) {
            mail.addBodyContent(decodeString(contentPart.getBody()));
        }
        if (CollectionUtils.isNotEmpty(contentPart.getParts())) {
            for (MessagePart part : contentPart.getParts()) {
                fillTextPlainBodyContent(mail, part);
            }
        }
    }

    private static String decodeString(MessagePartBody body) {
        return new String(Base64.decodeBase64(body.getData().getBytes()));
    }

    private static void fillAttachment(Mail mail, MessagePart attachmentPart) {
        mail.addAttachment(AttachmentBuilder.buildFromMessagePart(attachmentPart));
    }

    private static void fillFromHeadrs(Mail mail, MessagePart payload) {
        Map<String, MessagePartHeader> headers = toHeadersMap(payload.getHeaders());
        if (headers.containsKey(MessageHeader.FROM)) {
            mail.setFrom(headers.get(MessageHeader.FROM).getValue());
        }
        if (headers.containsKey(MessageHeader.SUBJECT)) {
            mail.setSubject(headers.get(MessageHeader.SUBJECT).getValue());
        }
    }

    private static Map<String, MessagePartHeader> toHeadersMap(List<MessagePartHeader> headers) {
        Map<String, MessagePartHeader> result = new HashMap<>();
        for (MessagePartHeader header : headers) {
            result.put(header.getName(), header);
        }
        return result;
    }

}
