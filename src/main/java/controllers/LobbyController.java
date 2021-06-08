package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.SpelbordModel;

import java.io.IOException;

public class LobbyController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void startGame(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //hier komt de variable GameState die naar true gezet moet worden zodat de server weet dat de game is gestart
        //gameIsRunning = true bijvoorbeeld



        // let the game init here
//        SpelbordModel hostedGame = new SpelbordModel()

        // then start off the game with a SpelbordModel.turnInProgress()



        }
    }

