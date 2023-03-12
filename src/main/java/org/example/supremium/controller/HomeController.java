package org.example.supremium.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.example.supremium.model.Player;
import org.example.supremium.model.Race;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController extends Controller {

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
    private Label bestTime;

    private Player player;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        beginRaceButton.setOnAction(this::beginRaceButtonPressed);
        endRaceButton.setOnAction(this::endRaceButtonPressed);




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
        typingPrompt.getChildren().clear();

    }

    public void startRace() {
        endRace(false);
        timer.textProperty().bind(Race.INSTANCE.timeTextProperty());
        typingPrompt.getChildren().addAll(
                Arrays.stream(Race.INSTANCE.getPrompt().split(""))
                        .map(Text::new)
                        .toList()

        );
        Race.INSTANCE.begin(this);
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
