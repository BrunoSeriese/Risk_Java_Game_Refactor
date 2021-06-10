package controllers;

import com.sun.javafx.sg.prism.NGExternalNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.GameStateModel;
import models.SpelbordModel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LobbyController {

    public Label sheeshLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    LoginController loginController = new LoginController();


    public void startGame(ActionEvent event) throws IOException, ExecutionException, InterruptedException {

        if (loginController.genoegSpelers()) {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();



            //hier komt de variable GameState die naar true gezet moet worden zodat de server weet dat de game is gestart
            //gameIsRunning = true bijvoorbeeld
            loginController.gameRunning();
        }

            //gamestate wordt init op 1
            GameStateModel gameState = new GameStateModel(1);
            // update gamestate naar firebase


            // let the game init here
            SpelbordModel hostedGame = new SpelbordModel();

            //Todo then start off the game with a SpelbordModel.turnInProgress()

        }
    }

