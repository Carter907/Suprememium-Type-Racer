package org.example.supremium.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class TimeManager {

    private volatile LinkedTransferQueue<String> timeQueue;

    public TimeManager() {
        timeQueue = new LinkedTransferQueue<>(List.of("0"));
    }
    public String intToTime(Integer time) {
        return (time / 1000) / 60 + ":" + (time / 1000) % 60 + ":" + time % 1000;
    }

    public Integer timeToInt(String text) {

        if (text.equals("0"))
            return 0;


        //(time.get()/1000)/60+":"+ (time.get()/1000)%60+ ":" +time.get() % 1000;
        int firstColon = text.indexOf(":"),
                secondColon = text.indexOf(":", firstColon + 1),
                thirdColon = text.indexOf(":", secondColon);

        int minutes = Integer.parseInt(text.substring(0, firstColon));
        int seconds = Integer.parseInt(text.substring(firstColon + 1, secondColon));
        int milliseconds = Integer.parseInt(text.substring(secondColon + 1));

        return minutes * 60000 + seconds * 1000 + milliseconds;
    }


    public LinkedTransferQueue<String> getTimeQueue() {
        return timeQueue;
    }

    public void reset() {
        timeQueue = new LinkedTransferQueue<>(List.of("0"));
    }
}
