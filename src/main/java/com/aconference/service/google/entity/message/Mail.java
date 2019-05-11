package com.aconference.service.google.entity.message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mail extends LightMail {

    private List<Attachment> attachments = new ArrayList<>();
    private Set<String> labels = new HashSet<>();
    private List<String> bodyContent = new ArrayList<>();
    private String subject;
    private String from;

    public void addLabel(String label) {
        labels.add(label);
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public void addBodyContent(String conent) {
        bodyContent.add(conent);
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public List<String> getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(List<String> bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
