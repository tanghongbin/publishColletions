package com.android_base.events;

/**
 *
 */
public class ErrorEvent {
    private String message;

    public ErrorEvent() {
    }

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
