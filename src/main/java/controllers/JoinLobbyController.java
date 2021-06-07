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
import java.util.concurrent.ExecutionException;

public class JoinLobbyController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    LoginController loginController = new LoginController();
    PlayerModel playerModel;

    @FXML
    TextField codeField;


    public JoinLobbyController(){
        System.out.println("I am alive");
        playerModel = loginController.getInstance();
    }

    public void switchToInsertLobbycode(ActionEvent event) throws IOException{
        System.out.println("de lobbycode is: " + codeField.getText());
        if (loginController.emptyLobbycode(codeField.getText())) {
            System.out.println("Lobbycode is leeg");
        } else {
            System.out.println("de usernamefield is: " + playerModel.getUsername());
            loginController.checkJoin(playerModel.getUsername(), codeField.getText());
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


}
