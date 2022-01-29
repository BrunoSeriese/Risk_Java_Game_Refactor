package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    ContentController contentController = new ContentController();

    public void switchToLobby(ActionEvent event) throws IOException {
        contentController.changeScene("FXML/Pre-Lobby.fxml", event);
    }


    public void openRuleSet(ActionEvent event) throws IOException {
        contentController.changeScene("FXML/rules1.fxml", event);
    }

    public void quitProgram() {
        Platform.exit();
    }
}
