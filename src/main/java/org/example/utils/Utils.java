package org.example.utils;

import javafx.application.Platform;


public class Utils {
    public static void delayedPrint(String message) {
        System.out.println(message);
        try {
            Thread.sleep(50);
            Platform.runLater(() -> {
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}