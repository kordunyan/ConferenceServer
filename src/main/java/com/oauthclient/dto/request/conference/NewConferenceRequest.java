package com.oauthclient.dto.request.conference;

import javax.validation.constraints.NotBlank;

public class NewConferenceRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String subject;

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
}
