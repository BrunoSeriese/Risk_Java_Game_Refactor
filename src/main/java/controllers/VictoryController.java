package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.VictoryViewController;

import java.io.IOException;
import java.util.Objects;

public class VictoryController {
    @FXML
    VictoryViewController victoryViewController = new VictoryViewController();

    public void switchToVictoryScreen(String username) throws IOException {
        Platform.runLater(() ->
                victoryViewController.setPlayerNameWinner(username));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("FXML/Victory.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}