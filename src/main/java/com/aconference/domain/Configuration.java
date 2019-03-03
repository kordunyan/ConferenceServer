package com.aconference.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invitationFilesPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitationFilesPath() {
        return invitationFilesPath;
    }

    public void setInvitationFilesPath(String invitationFilesPath) {
        this.invitationFilesPath = invitationFilesPath;
    }
}
