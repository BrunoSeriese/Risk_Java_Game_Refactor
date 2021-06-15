package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.GameModel;
import models.SpelbordModel;
import views.LobbyView;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LobbyController {


    private Stage stage;
    private Scene scene;
    private Parent root;
    boolean isInGame = false;

    SpelbordController spelbordController;
    static GameModel gameModel;
    LoginController loginController = new LoginController();
    @FXML
    Label username1;
    @FXML
    Label username2;
    @FXML
    Label username3;
    @FXML
    Label username4;
    @FXML
    Label lobbyCode;

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

                        spelbordController.getGameModelInstance();
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

//        System.out.println("run method");
//        LobbyView lobbyView = new LobbyView();
//        lobbyView.getFirebaseUsernames("110720");


        /////
    }

    public void startGame(ActionEvent event) throws IOException, ExecutionException, InterruptedException {

        if (loginController.enoughPlayers()) {
            loginController.gameRunning();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    //gamestate wordt init op 1
//    GameModel gameState = new GameModel(1);
    // update gamestate naar firebase
    //Todo Gamestate moet firebase

    // let the game init here
//    SpelbordModel hostedGame = new SpelbordModel();
    //Todo populate the hostedgame with a ArrayList<PlayerModel> and ArrayList<CountryModel>s

    @FXML
    public void initialize() {
        lobbyCode.setText(State.lobbycode);
    }

}

//        if (lobbyView.getFirebaseUsernames(State.lobbycode).size() == 1) {
//            username1.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(0)));
//        } else if (lobbyView.getFirebaseUsernames(State.lobbycode).size() == 2) {
//            username1.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(0)));
//            username2.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(1)));
//        } else if (lobbyView.getFirebaseUsernames(State.lobbycode).size() == 3) {
//            username1.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(0)));
//            username2.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(1)));
//            username3.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(2)));
//        } else if (lobbyView.getFirebaseUsernames(State.lobbycode).size() == 4) {
//            username1.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(0)));
//            username2.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(1)));
//            username3.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(2)));
//            username4.setText(String.valueOf(lobbyView.getFirebaseUsernames(State.lobbycode).get(3)));
//        } else {
//            System.out.println("er is iets fout gegaan met de namen aan het spel toevoegen");
//        }
//TODO: code in een loop zetten dat als een 2e speler joint de username2 word uitgevoerd enzv
//    //TODO: Initialize DRY maken
//    //TODO: ook de lobbycode pakken en die displayen



