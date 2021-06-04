package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
    private Button endGame;
    @FXML
    private Button startGame;


    @FXML
    void handleClick(ActionEvent event){
        Button sourceButton = (Button) event.getSource();

        if (sourceButton.getId().equals("playGame")){
            System.out.println("volgende Scherm");
        }

       System.out.println(sourceButton.getId());
        System.out.println("playGame");



    }
}
