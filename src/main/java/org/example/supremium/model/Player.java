package org.example.supremium.model;

public class Player {

    private String bestTime;

    {
        bestTime = "0";
    }

    public String getBestTime() {
        return this.bestTime;

    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }
}
