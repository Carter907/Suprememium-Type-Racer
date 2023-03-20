package org.example.supremium.controller;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.example.supremium.model.RaceService;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomeController extends Controller {
    @FXML
    private TextField inputText;
    @FXML
    private ButtonBar buttons;
    @FXML
    private TextFlow typingPrompt;
    @FXML
    private Button beginRaceButton;
    @FXML
    private Button endRaceButton;
    @FXML
    private Label timer;
    @FXML
    private Label wpm;
    private RaceService raceService;
    private ExecutorService executorService;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        beginRaceButton.setOnAction(this::beginRaceButtonPressed);
        endRaceButton.setOnAction(this::endRaceButtonPressed);
        raceService = new RaceService();
        executorService = Executors.newSingleThreadExecutor();

    }

    private void beginRaceButtonPressed(ActionEvent event) {
        if (raceService.inUse()) {
            inputText.requestFocus();
            return;
        }
        reset();
        setTypingPrompt();
        raceService.start();
        inputText.requestFocus();
        executorService.execute(() -> {

            while (raceService.inUse()) {
                try {
                    String time = raceService.getRaceTimeTransfer().take();
                    raceService.getRaceTimeTransfer().transfer(time);

                    Platform.runLater(() -> {
                        timer.setText("Time: " + time);
                        raceService.setFinished(!updateText(inputText.getText()));
                        wpm.setText("WPM: " + (int)((inputText.getText().length()/4.7)/(raceService.getTimeManager().timeToInt(time) / 60000d))
                        );
                    });

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        });
    }

    private void reset() {
        wpm.setText("WPM:");
        inputText.clear();
        timer.setText("Timer:");
        executorService.shutdown();
        try {

            if (executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService = Executors.newSingleThreadExecutor();
    }

    public boolean updateText(String newVal) {
        if (!raceService.inUse())
            return true;
        typingPrompt.getChildren().stream().map(t -> (Text)t).forEach(t -> {
            t.setFill(Color.WHITE);
        });
        List<Text> letters = getTypedLetters(newVal);
        boolean incorrect = false;
        for (int i = 0; i < letters.size(); i++) {
            if (letters.get(i).getText().equals(newVal.substring(i, i+1)))
                letters.get(i).setFill(Color.LIME);
            else {
                letters.get(i).setFill(Color.RED);
                incorrect = true;
            }
        }

        if (letters.size() == typingPrompt.getChildren().size() && !incorrect)
            return true;
        return false;
    }

    private List<Text> getCorrectLetters(List<Text> letters, String newVal) {


        return getTypedLetters(newVal).stream().filter(t -> t.getFill() == Color.LIME).toList();
    }

    private List<Text> getTypedLetters(String newVal) {
        return typingPrompt.getChildren().stream()
                .map(t -> (Text) t)
                .limit(newVal.length())
                .toList();
    }


    private void setTypingPrompt() {
        typingPrompt.getChildren().setAll(
                Arrays.stream(raceService.getPrompt().split(""))
                        .map(c -> {
                            Text t = new Text(c);
                            t.setFill(Color.WHITE);
                            return t;
                        }).collect(Collectors.toList())
        );
    }

    private void endRaceButtonPressed(ActionEvent event) {


    }


}
