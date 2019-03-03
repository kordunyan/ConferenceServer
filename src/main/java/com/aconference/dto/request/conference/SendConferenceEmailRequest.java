package com.aconference.dto.request.conference;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SendConferenceEmailRequest {

    @NotNull
    private Long id;
    @NotBlank
    private String emailContent;
    @NotBlank
    private String subject;
    @NotEmpty
    private List<String> emailsTo;
    @NotEmpty
    private List<MultipartFile> inviteFiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getEmailsTo() {
        return emailsTo;
    }

    public void setEmailsTo(List<String> emailsTo) {
        this.emailsTo = emailsTo;
    }

    public List<MultipartFile> getInviteFiles() {
        return inviteFiles;
    }

    public void setInviteFiles(List<MultipartFile> inviteFiles) {
        this.inviteFiles = inviteFiles;
    }
}
