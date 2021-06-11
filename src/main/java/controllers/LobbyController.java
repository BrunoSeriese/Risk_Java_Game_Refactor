package controllers;

import application.State;
import com.google.cloud.firestore.DocumentReference;
import com.sun.javafx.sg.prism.NGExternalNode;
import javafx.application.Platform;
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

    private Stage stage;
    private Scene scene;
    private Parent root;

    LoginController loginController = new LoginController();

    public void attachlistener(ActionEvent event) {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            System.out.println(documentSnapshot.getData().get("gameIsRunning"));
            try {
                if (loginController.genoegSpelers()) {
                    loginController.gameRunning();

                    if ((boolean) documentSnapshot.getData().get("gameIsRunning")) {
                        System.out.println("De game is running");

                        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
                        System.out.println("1");
                        stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
                        System.out.println("2");
                        System.out.println(stage);
                        scene = new Scene(root);
                        System.out.println("3");
                        System.out.println(scene);
                        Platform.runLater(() -> {
                            stage.setScene(scene);
                        });
                        System.out.println("4");

                        System.out.println("De scene is uitgevoerd");

                        //TODO Hier moet de nieuwe scene komen
                        //Je kan een lobby maken die op FALSE staat, (lees console, print false).
                        // In firebase kan je de gameIsRunning op True zetten, lees dan console, print true)

                    }
                }
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

//    public LobbyController(){
//        attachlistener();
//    }


    public void startGame(ActionEvent event) throws IOException, ExecutionException, InterruptedException {


        //TODO Deze scene moet naar de listener/ observer boven in attachListener()
        //De starter/host zet de gameIsRunning op True en de listener ziet dat en veranderd bij iedereen de scene.

//            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/GameMap.fxml"));
//            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();


        //hier komt de variable GameState die naar true gezet moet worden zodat de server weet dat de game is gestart
        //gameIsRunning = true bijvoorbeeld

    }

    //gamestate wordt init op 1
    GameStateModel gameState = new GameStateModel(1);
    // update gamestate naar firebase
    //Todo Gamestate moet firebase

    // let the game init here
    SpelbordModel hostedGame = new SpelbordModel();
    //Todo populate the hostedgame with a ArrayList<PlayerModel> and ArrayList<CountryModel>s
    //Todo then start off the game with a SpelbordModel.turnInProgress()

}


