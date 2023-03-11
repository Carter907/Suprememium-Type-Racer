package org.example.supremium.controller;

import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.supremium.AppStart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TitleController extends Controller {

    @FXML
    private VBox background;

    @FXML
    private Button contin_button;

    @FXML
    private Label title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTitleAnimationAndEffects(title);
    }

    private void addTitleAnimationAndEffects(Label title) {

        final int ROTATE_AMOUNT = 7;

        RotateTransition rotTrans = new RotateTransition();
        rotTrans.setNode(title);
        rotTrans.setDuration(Duration.seconds(5));
        rotTrans.setCycleCount(Timeline.INDEFINITE);
        rotTrans.setFromAngle(-ROTATE_AMOUNT);
        rotTrans.setToAngle(ROTATE_AMOUNT);
        rotTrans.setAxis(new Point3D(0,0,1));
        rotTrans.setAutoReverse(true);
        rotTrans.play();
    }

    public void onContinuePressed(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(AppStart.class.getResource("home-view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
