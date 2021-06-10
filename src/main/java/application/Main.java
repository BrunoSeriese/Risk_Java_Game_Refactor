package application;

import controllers.SpelbordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.GameStateModel;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/mainMenuScreen.fxml"));
        primaryStage.setTitle("Risk Game");
        primaryStage.setScene(new Scene(root,1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();


        GameStateModel gameStateModel = new GameStateModel();
//        gameStateModel.getPlayersFirebaseTurnID("869211");

//        gameStateModel.getGamestateTurnIDFirestore("869211");
        gameStateModel.comparePlayerIDtoTurnID("869211","2");
        gameStateModel.nextTurnIDFirebase("869211");



        //dit was voor de turn id ophalen uit firestore, momenteel gehardcode, moeten we veranderen
        //UNCOMMENT BOVENSTE STUK CODE ALS JE TURNID VAN FIRESTORE WIL TESTEN
        //ANDERS WERKT DE CODE NIET


    }

    public static void main(String[] args) {
        launch(args);


    }
}