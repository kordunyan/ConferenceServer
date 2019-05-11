package com.aconference.service.google.entity;

public class GoogleException extends Exception {

    public GoogleException() {
    }

    public GoogleException(String message) {
        super(message);
    }

    public GoogleException(String message, Throwable cause) {
        super(message, cause);
    }
}
