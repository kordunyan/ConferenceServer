package com.aconference.dto.response;

import com.aconference.domain.EmailTo;
import com.aconference.domain.InvitationFile;

import java.time.LocalDate;
import java.util.List;

public class ConferenceResponse {

    public Long id;
    private String title;
    private String subject;
    private String emailContent;
    private LocalDate dateCreated;
    private boolean emailSent;
    private List<EmailTo> emailsTo;
    private List<InvitationFile> invitationFiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<EmailTo> getEmailsTo() {
        return emailsTo;
    }

    public void setEmailsTo(List<EmailTo> emailsTo) {
        this.emailsTo = emailsTo;
    }

    public List<InvitationFile> getInvitationFiles() {
        return invitationFiles;
    }

    public void setInvitationFiles(List<InvitationFile> invitationFiles) {
        this.invitationFiles = invitationFiles;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }
}
