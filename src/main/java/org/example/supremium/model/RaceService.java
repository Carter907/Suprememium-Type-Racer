package org.example.supremium.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RaceService {
    private PromptManager promptManager;
    private TimeManager timeManager;
    private ScheduledExecutorService timer;
    private boolean inUse;

    public RaceService() {
        promptManager = new PromptManager();
        timer = Executors.newSingleThreadScheduledExecutor();
        timeManager = new TimeManager();
    }

    public void start() {
        reset();
        Race race = new Race(timeManager);
        timer.scheduleAtFixedRate(race, 0, 10, TimeUnit.MILLISECONDS);

        race.setRunning(true);
        setInUse(true);

    }

    public synchronized LinkedTransferQueue<String> getRaceTimeTransfer() {
        return timeManager.getTimeQueue();
    }
    private void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public String getPrompt() {

        return promptManager.getNewPrompt();
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public boolean inUse() {
        return this.inUse;
    }

    public void setTextListener(ChangeListener<StringProperty> listener) {
    }

    public void setFinished(boolean finished) {
        this.inUse = finished;
    }

    public void reset() {
        timeManager.reset();
    }
}
