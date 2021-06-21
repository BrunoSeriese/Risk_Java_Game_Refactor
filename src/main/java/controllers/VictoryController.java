package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VictoryController {

    @FXML
    public Label playerNameWinner;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToVictoryScreen(String username) throws IOException {
        Platform.runLater(() -> playerNameWinner.setText(username));
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Victory.fxml"));
        scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}