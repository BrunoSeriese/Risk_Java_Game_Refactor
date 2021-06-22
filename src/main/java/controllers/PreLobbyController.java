package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.PlayerModel;

import java.io.IOException;
import java.util.Objects;

public class PreLobbyController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    LoginController loginController = new LoginController();
    PlayerModel playerModel;

    @FXML
    TextField usernameField;

    public PreLobbyController() {
        playerModel = PlayerModel.getPlayerModelInstance();
    }

    public void switchToCreatedLobby(ActionEvent event) throws IOException {
        playerModel.setUsername(usernameField.getText());


        loginController.checkCreate(usernameField.getText());
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("FXML/Lobby.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToJoinedLobby(ActionEvent event) throws IOException {
        playerModel.setUsername(usernameField.getText());

        playerModel.setUsername(usernameField.getText());


        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("FXML/JoinLobby.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
