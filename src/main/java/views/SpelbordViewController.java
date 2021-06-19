package views;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import controllers.LoginController;
import controllers.SpelbordController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import models.GameModel;
import models.SpelbordModel;
import observers.SpelbordObservable;
import observers.SpelbordObserver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class SpelbordViewController implements Initializable {


    ArrayList<Button> buttonsArray = new ArrayList<>();
    ArrayList<ImageView> imageViewArray = new ArrayList<>();

    @FXML
    public ImageView endTurnIcon;
    @FXML
    public ImageView cardIcon;
    @FXML
    public ImageView diceIcon;
    @FXML
    public ImageView playerIcon;
    @FXML
    public ImageView NA1, NA2, NA3, NA4, NA5, NA6, NA7, NA8, NA9, NA10, AFRICA1, AFRICA2, AFRICA3, AFRICA4,
            AFRICA5, AFRICA6, EU1, EU2, EU3, EU4, EU5, EU6, EU7, SA1, SA2, SA3, SA4, OCE1, OCE2, OCE3, OCE4, ASIA1, ASIA2, ASIA3, ASIA4, ASIA5, ASIA6, ASIA7, ASIA8, ASIA9, ASIA10, ASIA11, ASIA12;

    public ImageView[] countries;

    @FXML
    public Button cNA1, cNA2, cNA3, cNA4, cNA5, cNA6, cNA7, cNA8, cNA9, cNA10, cAFRICA1, cAFRICA2, cAFRICA3, cAFRICA4,
            cAFRICA5, cAFRICA6, cEU1, cEU2, cEU3, cEU4, cEU5, cEU6, cEU7, cSA1, cSA2, cSA3, cSA4, cOCE1, cOCE2, cOCE3, cOCE4,
            cASIA1, cASIA2, cASIA3, cASIA4, cASIA5, cASIA6, cASIA7, cASIA8, cASIA9, cASIA10, cASIA11, cASIA12;

    public Button[] buttons;


    SpelbordController spelbordController = new SpelbordController();
    static SpelbordModel spelbordModel;
    GameModel gameModel;
    LoginController loginController = new LoginController();

//    public SpelbordViewController() {
//        spelbordController.registerObserver(this);
//    }


    public void getButtonID(ActionEvent event) throws ExecutionException, InterruptedException, IOException {
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
        hideHUD();
    }

    public void rollDice() {
        spelbordController.rollDice();
    }

    public void endTurn() throws ExecutionException, InterruptedException {
        spelbordController.endTurn();
    }

//    public void setColorCountry(ImageView imageLand, double playerColor) {
//        spelbordController.setColorCountry(imageLand, playerColor);
//    }

//    public void setCountryColorStartGame() throws ExecutionException, InterruptedException {
//        spelbordController.setCountryColorStartGame();
//    }

    public void setArmies(Button button, int armies) {
//        button = "c" + button;
//        button = (Button) button;
        spelbordController.setArmies(button, armies);
    }

    public void initialize() {
        countries = new ImageView[]{NA1, NA2, NA3, NA4, NA5, NA6, NA7, NA8, NA9, NA10, AFRICA1, AFRICA2, AFRICA3, AFRICA4,
                AFRICA5, AFRICA6, EU1, EU2, EU3, EU4, EU5, EU6, EU7, SA1, SA2, SA3, SA4, OCE1, OCE2, OCE3, OCE4, ASIA1, ASIA2, ASIA3, ASIA4, ASIA5, ASIA6, ASIA7, ASIA8, ASIA9, ASIA10, ASIA11, ASIA12};

        buttons = new Button[]{cNA1, cNA2, cNA3, cNA4, cNA5, cNA6, cNA7, cNA8, cNA9, cNA10, cAFRICA1, cAFRICA2, cAFRICA3, cAFRICA4,
                cAFRICA5, cAFRICA6, cEU1, cEU2, cEU3, cEU4, cEU5, cEU6, cEU7, cSA1, cSA2, cSA3, cSA4, cOCE1, cOCE2, cOCE3, cOCE4,
                cASIA1, cASIA2, cASIA3, cASIA4, cASIA5, cASIA6, cASIA7, cASIA8, cASIA9, cASIA10, cASIA11, cASIA12};
    }

    public ImageView[] getCountriesArray() {
        return countries;
    }

    public Button[] getButtonsArray() {
        return buttons;
    }


    //TODO FIX HUD


    public void hideHUD() {
        System.out.println("NOG NIET WEG");
        cardIcon.setVisible(false);
        diceIcon.setVisible(false);
        playerIcon.setVisible(false);
        endTurnIcon.setVisible(false);
        for (Button button : buttons) {
            button.setVisible(false);
        }
        System.out.println("IS WEG WOOOOO");
    }

    public void showHUD() {
        System.out.println("NOG NIET WEG");
        cardIcon.setVisible(true);
        diceIcon.setVisible(true);
        playerIcon.setVisible(true);
        endTurnIcon.setVisible(true);
        for (Button button : buttons) {
            button.setVisible(true);
        }
        System.out.println("IS WEG WOOOOO");
    }

    public void HUD() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (State.TurnID == Integer.parseInt(document.get("gamestateTurnID").toString())) {
            showHUD();
        } else {
            hideHUD();
        }
    }


    public void garrison() {
        //Todo catch mouseEvent
        // catch clickedCountry
        // Check if country has the same playerID as the player

    }

//    @Override
//    public void update(SpelbordObservable sb) {
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
