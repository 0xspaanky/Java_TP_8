package com.example.tp;

public interface Notification {
    void send(String recipient, String message);
    int getPriority();
    String getType();
}
