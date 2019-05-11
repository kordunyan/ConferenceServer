package com.aconference.service.google.entity.message;

public class MimeType {
    public static final String TEXT_HTML = "text/html";

    public static boolean isHtml(String type) {
        return TEXT_HTML.equalsIgnoreCase(type);
    }

}
