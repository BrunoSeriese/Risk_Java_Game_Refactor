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
    public Button buttonNA1;

    SpelbordController spelbordController = SpelbordController.getSpelbordControllerInstance();
    static SpelbordModel spelbordModel;
    GameModel gameModel;
    LoginController loginController = new LoginController();


    public SpelbordViewController() {
        spelbordController.registerObserver(this);
    }


    public void getButtonID(ActionEvent event) {
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
    }

    public void endTurn() throws ExecutionException, InterruptedException {
        spelbordController.endTurn();
    }




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
