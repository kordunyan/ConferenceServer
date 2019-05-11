package com.aconference.dto.request.conference;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class SendConferenceEmailRequest {

    @NotNull
    private Long id;
    @NotBlank
    private String emailContent;
    @NotBlank
    private String subject;
    @NotEmpty
    private Set<String> emailsTo;

    private List<MultipartFile> inviteFiles;

    private Set<Long> savedInvitationFilesIds;

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

    public Set<String> getEmailsTo() {
        return emailsTo;
    }

    public void setEmailsTo(Set<String> emailsTo) {
        this.emailsTo = emailsTo;
    }

    public List<MultipartFile> getInviteFiles() {
        return inviteFiles;
    }

    public void setInviteFiles(List<MultipartFile> inviteFiles) {
        this.inviteFiles = inviteFiles;
    }

    public Set<Long> getSavedInvitationFilesIds() {
        return savedInvitationFilesIds;
    }

    public void setSavedInvitationFilesIds(Set<Long> savedInvitationFilesIds) {
        this.savedInvitationFilesIds = savedInvitationFilesIds;
    }
}
