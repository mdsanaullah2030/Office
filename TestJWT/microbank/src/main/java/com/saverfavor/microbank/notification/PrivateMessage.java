package com.saverfavor.microbank.notification;

public class PrivateMessage {
    private String to;
    private String content;

    // Constructors
    public PrivateMessage() {}

    public PrivateMessage(String to, String content) {
        this.to = to;
        this.content = content;
    }

    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
