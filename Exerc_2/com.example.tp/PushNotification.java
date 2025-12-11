package com.example.tp;

public class PushNotification implements Notification {
    private String appId;

    public PushNotification(String appId) {
        this.appId = appId;
    }

    @Override
    public void send(String recipient, String message) {
        System.out.printf("[Push] App: %s User: %s — %s%n",
                appId, recipient, message);
    }

    @Override
    public int getPriority() {
        return 0; // basse priorité
    }

    @Override
    public String getType() {
        return "Push";
    }
}
