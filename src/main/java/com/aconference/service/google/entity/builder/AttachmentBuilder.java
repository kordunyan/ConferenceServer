package com.aconference.service.google.entity.builder;

import com.aconference.service.google.entity.message.Attachment;
import com.google.api.services.gmail.model.MessagePart;

public final class AttachmentBuilder {

    private AttachmentBuilder() {
    }

    public static Attachment buildFromMessagePart(MessagePart attachmentPart) {
        Attachment attachment = new Attachment();
        attachment.setName(attachmentPart.getFilename());
        attachment.setId(attachmentPart.getBody().getAttachmentId());
        return attachment;
    }

}
