package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLobby(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Pre-Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void openRuleSet(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/rules1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quitProgram(){
        Platform.exit();
    }
}
