package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.PlayerModel;

import java.io.IOException;

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


            if (loginController.isPlayerInLobby(playerModel.getUsername(), codeField.getText())) {
                contentController.changeScene("FXML/Lobby.fxml", event);
            }
        } else {
            invalidCode.setVisible(true);
        }
    }


}
