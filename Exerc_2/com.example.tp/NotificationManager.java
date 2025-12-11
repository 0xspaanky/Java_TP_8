package com.example.tp;

import java.util.Arrays;
import java.util.Comparator;

public class NotificationManager {
    private Notification[] channels = new Notification[3];
    private int count = 0;

    public void addChannel(Notification n) {
        if (count == channels.length) {
            Notification[] tmp = new Notification[channels.length * 2];
            System.arraycopy(channels, 0, tmp, 0, channels.length);
            channels = tmp;
        }
        channels[count++] = n;
    }

    public void broadcast(String recipient, String message) {
        Notification[] copy = Arrays.copyOf(channels, count);

        // Tri par priorité décroissante
        Arrays.sort(copy, Comparator.comparingInt(Notification::getPriority).reversed());

        System.out.println("Broadcast to " + recipient + ":");

        for (Notification n : copy) {
            System.out.printf("Channel [%s, prio=%d]: ",
                    n.getType(), n.getPriority());
            n.send(recipient, message);
        }

        System.out.println();
    }
}
