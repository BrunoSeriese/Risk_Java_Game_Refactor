package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.PlayerModel;

import java.io.IOException;
import java.util.Objects;

public class JoinLobbyController {


    LoginController loginController = new LoginController();

    PlayerModel playerModel;

    @FXML
    TextField codeField;
    public Text invalidCode;
    ContentController contentController = new ContentController();


    public JoinLobbyController() {

        playerModel = PlayerModel.getPlayerModelInstance();
    }

    public void switchToInsertLobbycode(ActionEvent event) throws IOException {


        if (loginController.validateLobby(codeField.getText())) {


            if (loginController.checkJoin(playerModel.getUsername(), codeField.getText())) {
                contentController.changeScene("FXML/Lobby.fxml", event);
            }
        } else {
            invalidCode.setVisible(true);
        }
    }


}
