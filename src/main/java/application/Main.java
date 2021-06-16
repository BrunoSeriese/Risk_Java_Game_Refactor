package application;

import controllers.SpelbordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    SpelbordController spelbordController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        State.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/mainMenuScreen.fxml"));
        primaryStage.setTitle("Risk");
        primaryStage.getIcons().add(new Image( "images/risklogo.png"));
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

    //TODO VERY IMPORTANT! TO DOO DOO REMOVE ALL GETINSTANCES  AND REPLACE BY X x = NEW X();

//        BasicConfigurator.configure();
//        remove comment van State.TurnID en State.TurnID om GameModel te testen
//        State.lobbycode = "762447";
//        State.TurnID = 1;
//        GameModel gameStateModel = new GameModel();


//        gameStateModel.getPlayersFirebaseTurnID("869211");
//        gameStateModel.getGamestateTurnIDFirestore("869211");
//        gameStateModel.comparePlayerIDtoTurnID("762447","2");
//        gameStateModel.nextTurnIDFirebase("869211");


//        test hiermee de countries
//        SpelbordModel tester = new SpelbordModel();
//        tester.CountriesAndIdMap      ();
//        tester.getCountries();
//

        State.lobbycode = "337789";
        //Uncomment om in firebase de countries in te zetten
        spelbordController = spelbordController.getSpelbordControllerInstance();
//        SpelbordController spelbordController = new SpelbordController();

//        spelbordController.setArmyAndCountryInFirebase();
//        spelbordController.getArmyAndCountryFromFirebase();
//
        spelbordController.getNeighborsFirebase();

//        spelbordController.setArmyFirebase("NA6", 345);
//        spelbordController.getArmyFirebase(1);


        //dit was voor de turn id ophalen uit firestore, momenteel gehardcode, moeten we veranderen
        //UNCOMMENT BOVENSTE STUK CODE ALS JE TURNID VAN FIRESTORE WIL TESTEN
        //ANDERS WERKT DE CODE NIET


    }

    public static void main(String[] args) {
        launch(args);


    }
}