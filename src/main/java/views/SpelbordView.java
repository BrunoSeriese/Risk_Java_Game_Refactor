package views;

import application.State;
import controllers.SpelbordController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import observers.SpelbordObservable;
import observers.SpelbordObserver;

import java.util.concurrent.ExecutionException;

public class SpelbordView implements SpelbordObserver {

    @FXML
    public static ImageView endTurnIcon;
    @FXML
    public static ImageView cardIcon;
    @FXML
    public static ImageView diceIcon;
    @FXML
    public static ImageView playerIcon;

    SpelbordController spelbordController = SpelbordController.getSpelbordControllerInstance();

    public SpelbordView() {
        spelbordController.registerObserver(this);
    }

    public void garrison(){
        //Todo catch mouseEvent
        // catch clickedCountry
        // Check if country has the same playerID as the player

    }

    public void handleClicky() {}

    public void showCards() {}

    public void showPlayers() {}

    public void rollDice() {}

    public void endTurn(){}

    @Override
    public void update(SpelbordObservable sb) {

    }
}
