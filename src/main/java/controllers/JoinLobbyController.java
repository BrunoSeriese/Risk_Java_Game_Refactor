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

public class JoinLobbyController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    LoginController loginController = new LoginController();

    PlayerModel playerModel;

    @FXML
    TextField codeField;



    public JoinLobbyController(){
        System.out.println("I am joinlobby alive");
        playerModel = playerModel.getPlayerModelInstance();
    }

    public void switchToInsertLobbycode(ActionEvent event) throws IOException{
        System.out.println("Ingevulde lobbycode is: " + codeField.getText());

        if (loginController.validateLobby(codeField.getText())) {
            System.out.println("de usernamefield is: " + playerModel.getUsername());

            if (loginController.checkJoin(playerModel.getUsername(), codeField.getText())){
                root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            System.out.println("lobbycode is ongeldig, probeer het nog eens");

        }
    }


}
