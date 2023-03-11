package org.example.supremium.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.example.supremium.model.Player;
import org.example.supremium.model.Race;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Controller {
    @FXML
    private TextArea typingArea;
    @FXML
    private ButtonBar buttons;

    @FXML
    private TextArea promptText;

    @FXML
    private Button beginRaceButton;

    @FXML
    private Button endRaceButton;
    @FXML
    private Label timer;

    @FXML
    private Label bestTime;

    private Player player;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        beginRaceButton.setOnAction(this::beginRaceButtonPressed);
        endRaceButton.setOnAction(this::endRaceButtonPressed);
        typingArea.setWrapText(true);
        typingArea.setPromptText("");
        promptText.setWrapText(true);



        player = new Player();

    }

    private void endRaceButtonPressed(ActionEvent event) {

        endRace(false);


    }

    private void beginRaceButtonPressed(ActionEvent event) {

        startRace();

    }

    public void endRace(boolean isPromptCompleted) {
        Race.INSTANCE.end(isPromptCompleted);
        timer.textProperty().unbind();
        timer.setText("Timer:");
        typingArea.clear();
        typingArea.setEditable(false);

        promptText.clear();
    }

    public void startRace() {
        endRace(false);
        typingArea.requestFocus();
        timer.textProperty().bind(Race.INSTANCE.timeTextProperty());
        promptText.setPromptText(Race.INSTANCE.getPrompt());
        Race.INSTANCE.begin(this);

        typingArea.setEditable(true);
    }

    public TextArea getTypingTextArea() {
        return typingArea;
    }

    public TextArea getPromptTextArea() {
        return promptText;
    }
    public void setBestTime(IntegerProperty bestTime) {
        String bestTimeText = Race.INSTANCE.convertToTime(bestTime);
        player.setBestTime(bestTimeText);
        this.bestTime.setText("Best Time: " + bestTimeText);
    }
    public IntegerProperty getBestTime() {

        return new SimpleIntegerProperty(Race.INSTANCE.timeToInt(player.getBestTime()));
    }

    public Player getPlayer() {
        return player;
    }
}
