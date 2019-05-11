package com.aconference.service.google.entity.message;

import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LightMail {

    public static final Type LIST_TYPE = new TypeToken<List<LightMail>>() {}.getType();

    private String id;
    private String threadId;

    public LightMail() {
    }

    public LightMail(String id, String threadId) {
        this.id = id;
        this.threadId = threadId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}
