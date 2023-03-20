package org.example.supremium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.supremium.data.Database;

import java.io.IOException;
import java.sql.SQLException;

public class AppStart extends Application {

    final String APP_NAME = "Suprememium Type Racer";
    final int WIDTH = 600, HEIGHT = 400;

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        Database.INSTANCE.getConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(AppStart.class.getResource("title-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(AppStart.class.getResource("css/Application.css").toExternalForm());
        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}