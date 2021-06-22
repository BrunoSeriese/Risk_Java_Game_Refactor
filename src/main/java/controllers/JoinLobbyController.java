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


    public JoinLobbyController() {

        playerModel = PlayerModel.getPlayerModelInstance();
    }

    public void switchToInsertLobbycode(ActionEvent event) throws IOException {


        if (loginController.validateLobby(codeField.getText())) {


            if (loginController.checkJoin(playerModel.getUsername(), codeField.getText())) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("FXML/Lobby.fxml")));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            invalidCode.setVisible(true);
        }
    }


}
