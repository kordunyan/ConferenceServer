package com.aconference.domain;

import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String title;

    private String subject;

    @Column(columnDefinition = "text")
    private String emailContent;

    private Boolean emailSent = false;

    private LocalDate dateCreated;

    @ManyToOne()
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailTo> emailsTo = new ArrayList<>();

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitationFile> invitationFiles = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public void addEmailTo(EmailTo emailTo) {
        emailsTo.add(emailTo);
        emailTo.setConference(this);
    }

    public void setAllInvitationFiles(Collection<InvitationFile> invitationFiles) {
        if (CollectionUtils.isEmpty(invitationFiles)) {
            return;
        }
        this.invitationFiles.clear();
        addAllInvitationFiles(invitationFiles);
    }

    public void addAllInvitationFiles(Collection<InvitationFile> invitationFiles) {
        invitationFiles.forEach(this::addInvitationFile);
    }

    public void setAllEmailsTo(Collection<EmailTo> emailsTo) {
        if (CollectionUtils.isEmpty(emailsTo)) {
            return;
        }
        this.emailsTo.clear();
        addAllEmailsTo(emailsTo);
    }

    public void addAllEmailsTo(Collection<EmailTo> emailsTo) {
        emailsTo.forEach(this::addEmailTo);
    }

    public void addInvitationFile(InvitationFile invitationFile) {
        this.invitationFiles.add(invitationFile);
        invitationFile.setConference(this);
    }

    public boolean hasEmailTo(EmailTo email) {
        if (CollectionUtils.isEmpty(emailsTo)) {
            return false;
        }
        return emailsTo.stream().anyMatch(emailTo -> emailTo.getEmail().equals(email.getEmail()));
    }

    public boolean hasNotEmailTo(EmailTo email) {
        return !hasEmailTo(email);
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }
}
