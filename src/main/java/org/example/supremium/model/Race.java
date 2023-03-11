package org.example.supremium.model;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.example.supremium.controller.HomeController;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public enum Race {

    INSTANCE;

    private volatile StringProperty timeText;
    private volatile IntegerProperty time;
    private final String LABEL_TEXT = "Timer: ";
    private ScheduledExecutorService timer;
    private HomeController homeController;
    private PromptManager manager;
    public volatile boolean isRunning;

    Race() {
        timeText = new SimpleStringProperty();
        time = new SimpleIntegerProperty();
        timer = Executors.newSingleThreadScheduledExecutor();
        isRunning = false;
        manager = new PromptManager();
    }

    public synchronized void begin(HomeController controller) {
        if (isRunning) {
            return;
        }

        homeController = controller;


        timer = Executors.newSingleThreadScheduledExecutor();
        timeText.set(LABEL_TEXT);
        time.set(0);
        timer.scheduleAtFixedRate(getRaceTask(), 0, 10, TimeUnit.MILLISECONDS);
        isRunning = true;
    }
    public synchronized void end(boolean isPromptCompleted) {
        if (!isRunning) {
            return;
        }
        timer.shutdown();
        try {
            if (timer.awaitTermination(1000, TimeUnit.MILLISECONDS))
                timer.shutdownNow();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (homeController.getBestTime().get() < time.get() && isPromptCompleted) {
                homeController.setBestTime(time);
            }
        }


        isRunning = false;
    }
    public String convertToTime(IntegerProperty time) {
        return (time.get()/1000)/60+":"+ (time.get()/1000)%60+ ":" +time.get() % 1000;
    }
    private Runnable getRaceTask() {
        return () -> {
            Platform.runLater(() -> {
                time.set(time.get() + 10);
                timeText.set(LABEL_TEXT + convertToTime(time));

                if (homeController.getTypingTextArea().getText()
                        .equals(homeController.getPromptTextArea().getPromptText())) {

                    homeController.endRace(true);
                }
            });

        };
    }
    public ObservableValue<String> timeTextProperty() {
        return this.timeText;
    }

    public String getPrompt() {

        return manager.getNewPrompt();
    }

    public int timeToInt(String text) {

        if (text.equals("0"))
            return 0;


        //(time.get()/1000)/60+":"+ (time.get()/1000)%60+ ":" +time.get() % 1000;
        int firstColon = text.indexOf(":"),
                secondColon =  text.indexOf(":", firstColon+1),
                thirdColon = text.indexOf(":", secondColon);

        int minutes = Integer.parseInt(text.substring(0, firstColon));
        int seconds = Integer.parseInt(text.substring(firstColon+1, secondColon));
        int milliseconds = Integer.parseInt(text.substring(secondColon+1));

        return minutes * 60000 + seconds * 1000 + milliseconds;
    }
}
