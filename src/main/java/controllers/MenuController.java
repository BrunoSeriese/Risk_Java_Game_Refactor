package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import views.LobbyView;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button endGame;
    @FXML
    private Button startGame;


    @FXML
    void handleClick(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();

        if (sourceButton.getId().equals("playGame")){

//            LobbyView lobby = new LobbyView();




        }

       System.out.println(sourceButton.getId());
        System.out.println("playGame");



    }
}
