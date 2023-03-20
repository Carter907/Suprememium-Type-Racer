package org.example.supremium.model;


public class Race implements Runnable {

    private TimeManager timeManager;
    private volatile boolean isRunning;

    public Race(TimeManager timeManager) {
        this.timeManager = timeManager;
        this.isRunning = false;
    }
    @Override
    public void run() {
        try {
            Integer time = timeManager.timeToInt(timeManager.getTimeQueue().take());
            time+=10;
            timeManager.getTimeQueue().transfer(timeManager.intToTime(time));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized void setRunning(boolean running) {
        this.isRunning = running;
    }
    public boolean isRunning() {
        return isRunning;
    }
}
