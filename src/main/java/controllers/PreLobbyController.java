package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PreLobbyController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    LoginController loginController = new LoginController();

    @FXML
    TextField usernameField;

    public void switchToLobby(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Pre-Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreatedLobby(ActionEvent event) throws IOException {
        loginController.testMessage(usernameField.getText());
        loginController.checkCreate(usernameField.getText());
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToJoinedLobby(ActionEvent event) throws IOException {
        loginController.testMessage(usernameField.getText());
        loginController.checkCreate(usernameField.getText());
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/JoinLobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}