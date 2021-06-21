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
import models.LobbyModel;
import views.SpelbordViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LobbyController {


    private static LobbyModel lobbymodel;
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
                    if (checkIfInGame()) {

                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
                        root = loader.load();
                        scene = new Scene(root);

                        SpelbordViewController spelbordViewController = loader.getController();

                        Platform.runLater(() -> State.stage.setScene(scene));
                        SpelbordController spelbordController = new SpelbordController();
                        spelbordController.setArmyAndCountryInFirebase();
                        Thread.sleep(50);
                        spelbordController.setSpelbordViewController(spelbordViewController);
                        spelbordController.setCountries(spelbordViewController.getCountriesArray());
                        spelbordController.setButtons(spelbordViewController.getButtonsArray());
                        Thread.sleep(50);
                        spelbordController.setCountryColorStartGame();
                    }
                }

            } catch (ExecutionException | InterruptedException | IOException executionException) {
                executionException.printStackTrace();
            }

        });


    }


    public LobbyController() {
        lobbymodel = getLobbyModelInstance();
        attachlistener();
        attachlistener2();
    }

    public static LobbyModel getLobbyModelInstance() {
        if (lobbymodel == null) {
            lobbymodel = new LobbyModel();
        }
        return lobbymodel;
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

    public void attachlistener2() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {

                Platform.runLater(() -> {
                    try {
                        ArrayList<String> userNames = lobbymodel.getFirebaseUsernames(State.lobbycode);
                        username1.setText(userNames.get(0));
                        if (userNames.size() >= 2) {
                            username2.setText(userNames.get(1));
                        }
                        if (userNames.size() >= 3) {
                            username3.setText(userNames.get(2));
                        }
                        if (userNames.size() == 4) {
                            username4.setText(userNames.get(3));
                        }


                    } catch (ExecutionException | InterruptedException executionException) {
                        executionException.printStackTrace();
                    }
                });


            }
        });
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


//        }
//TODO: code in een loop zetten dat als een 2e speler joint de username2 word uitgevoerd enzv
//    //TODO: Initialize DRY maken
//    //TODO: ook de lobbycode pakken en die displayen



