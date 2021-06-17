package views;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import controllers.LoginController;
import controllers.SpelbordController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import models.GameModel;
import models.SpelbordModel;
import observers.SpelbordObservable;
import observers.SpelbordObserver;

import java.util.concurrent.ExecutionException;

public class SpelbordViewController implements SpelbordObserver {

    @FXML
    public static ImageView endTurnIcon;
    @FXML
    public static ImageView cardIcon;
    @FXML
    public static ImageView diceIcon;
    @FXML
    public static ImageView playerIcon;
    @FXML
    public ImageView NA1;
    public ImageView NA5;

    public Button cNA1;
    public Button cNA2;
    public Button cNA3;
    public Button cNA4;
    public Button cNA5;
    public Button cNA6;
    public Button cNA7;
    public Button cNA8;
    public Button cNA9;
    public Button cNA10;
    public Button cAFRICA1;
    public Button cAFRICA2;
    public Button cAFRICA3;
    public Button cAFRICA4;
    public Button cAFRICA5;
    public Button cAFRICA6;
    public Button cEU1;
    public Button cEU2;
    public Button cEU3;
    public Button cEU4;
    public Button cEU5;
    public Button cEU6;
    public Button cEU7;
    public Button cSA1;
    public Button cSA2;
    public Button cSA3;
    public Button cSA4;
    public Button cOCE1;
    public Button cOCE2;
    public Button cOCE3;
    public Button cOCE4;
    public Button cASIA1;
    public Button cASIA2;
    public Button cASIA3;
    public Button cASIA4;
    public Button cASIA5;
    public Button cASIA6;
    public Button cASIA7;
    public Button cASIA8;
    public Button cASIA9;
    public Button cASIA10;
    public Button cASIA11;
    public Button cASIA12;


    SpelbordController spelbordController = SpelbordController.getSpelbordControllerInstance();
    static SpelbordModel spelbordModel;
    GameModel gameModel;
    LoginController loginController = new LoginController();


    public SpelbordViewController() {
        spelbordController.registerObserver(this);
    }


    public void getButtonID(ActionEvent event) throws ExecutionException, InterruptedException {
        spelbordController.getButtonID(event);
    }

    public void handleClicky() {
        spelbordController.handleClicky();
    }

    public void showCards() {
        spelbordController.showCards();
    }

    public void showPlayers() {
        spelbordController.showPlayers();
    }

    public void rollDice() {
        spelbordController.rollDice();
        setColorCountry(NA5, State.GREEN);
        setColorCountry(NA1, State.BLUE);
    }

    public void endTurn() throws ExecutionException, InterruptedException {
        spelbordController.endTurn();
    }

    public void setColorCountry(ImageView imageLand, double playerColor){
        spelbordController.setColorCountry(imageLand, playerColor);
    }

//    public void setCountryColorStartGame() throws ExecutionException, InterruptedException {
//        spelbordController.setCountryColorStartGame();
//    }




    //TODO FIX HUD
//    public void hideHUD() {
//        cardIcon.setVisible(false);
//        diceIcon.setVisible(false);
//        playerIcon.setVisible(false);
//    }
//
//    public void showHUD() {
//        cardIcon.setVisible(true);
//        diceIcon.setVisible(true);
//        playerIcon.setVisible(true);
//    }
//
//    public void HUD() throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        if (State.TurnID == Integer.parseInt(document.get("gamestateTurnID").toString())) {
//            showHUD();
//        } else {
//            hideHUD();
//        }
//    }


    public void garrison(){
        //Todo catch mouseEvent
        // catch clickedCountry
        // Check if country has the same playerID as the player

    }
    @Override
    public void update(SpelbordObservable sb) {

    }
}
