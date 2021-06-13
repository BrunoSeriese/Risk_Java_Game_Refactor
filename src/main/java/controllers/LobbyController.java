package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.sun.javafx.sg.prism.NGExternalNode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.GameStateModel;
import models.PlayerModel;
import models.SpelbordModel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LobbyController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    boolean isInGame = false;


    static GameStateModel gameStateModel;
    LoginController loginController = new LoginController();

    public boolean checkIfInGame() {
        if (!isInGame) {
            isInGame = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkGameIsRunning() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if ((boolean) document.getData().get("gameIsRunning")) {
            return true;
        } else {
            return false;
        }

    }

    public void attachlistener() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {

            try {
                if (checkGameIsRunning()) {
                    System.out.println("2");
                    if (checkIfInGame()) {
                        System.out.println("3");
                        System.out.println("De game is running");
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
                        scene = new Scene(root);

                        Platform.runLater(() -> {
                            State.stage.setScene(scene);
                        });

                        loginController.getGameStateModelInstance();
                        System.out.println("De scene is uitgevoerd");
                    }
                }

            } catch (ExecutionException | InterruptedException | IOException executionException) {
                executionException.printStackTrace();
            }

        });
    }

    public LobbyController() {
        attachlistener();
    }

    public void startGame(ActionEvent event) throws IOException, ExecutionException, InterruptedException {

        if (loginController.enoughPlayers())
            loginController.gameRunning();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    //gamestate wordt init op 1
    GameStateModel gameState = new GameStateModel(1);
    // update gamestate naar firebase
    //Todo Gamestate moet firebase

    // let the game init here
    SpelbordModel hostedGame = new SpelbordModel();
    //Todo populate the hostedgame with a ArrayList<PlayerModel> and ArrayList<CountryModel>s


}


