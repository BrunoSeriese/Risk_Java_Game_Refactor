package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.DiceModel;
import models.GameModel;
import models.SpelbordModel;
import observers.SpelbordObserver;
import views.SpelbordViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SpelbordController {


    private SpelbordViewController spelbordViewController;
    static GameModel gameModel;
    static SpelbordModel spelbordModel;
    private SpelbordModel map;
    private boolean canEnd;
    private int turnID;
    private boolean gameOver;
    static SpelbordController spelbordController;
    private DiceModel dice = new DiceModel();
    private ImageView[] countries;
    private Button[] buttons;
    //    gameModel = loginController.getGameModelInstance();
//    LoginController loginController = new LoginController();

    public ImageView[] getCountries() {
        System.out.println("Countries worden opgevraagd");
        System.out.println(Arrays.toString(this.countries));

        return this.countries;
    }

    public void setCountries(ImageView[] countries) {
        System.out.println("Countries worden aangepast");
        this.countries = countries;
    }

    public void setSpelbordViewController(SpelbordViewController spelbordViewController) {
        this.spelbordViewController = spelbordViewController;
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public static GameModel getGameModelInstance() {
        if (gameModel == null) {
            gameModel = new GameModel(1);
            System.out.println("nieuwe instantie van GameModel is aangemaakt");
        }
        return gameModel;
    }


    EventHandler<MouseEvent> eventHandler = e -> System.out.println("ER is geklikt");


    public void attachlistener() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {

                int firebaseTurnID = Integer.parseInt(documentSnapshot.getData().get("gamestateTurnID").toString());
                gameModel.setTurnID(firebaseTurnID);


                try {
                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(100);
                            getArmyAndCountryFromFirebase();
                        } catch (ExecutionException | InterruptedException | IOException executionException) {
                            executionException.printStackTrace();
                        }
                    });

                    if (gameModel.getPhaseID() == 1 && comparePlayerIDtoTurnIDFirebase()) {
                        spelbordViewController.showCardIcon();
                    } else {
                        spelbordViewController.hideCardIcon();
                    }
                    startMainLoop();
                    setCountryColorStartGame();

                    spelbordViewController.HUD();
                } catch (ExecutionException | InterruptedException | IOException executionException) {
                    executionException.printStackTrace();
                }
            }
        });
    }

    public void startMainLoop() throws ExecutionException, InterruptedException {
        if (comparePlayerIDtoTurnIDFirebase()) {
            State.stage.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            canEnd = true;

        } else {
            canEnd = false;
        }
    }

    public SpelbordController() {
        gameModel = getGameModelInstance();
        spelbordModel = SpelbordModel.getSpelbordModelInstance();

        //todo verder uitwerken
//        SpelbordObserver phaseID = null;
//        spelbordModel.register(phaseID);
//        phaseID.update(1);
        attachlistener();
        countryListener();

    }

    public void setArmyAndCountryInFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        assert document.get("countries") != null;

        spelbordModel.CountriesAndIdMap();
        Map<String, Object> data = new HashMap<>();
        data.put("countries", spelbordModel.getCountries());

        ApiFuture<WriteResult> result = docRef.update(data);


    }

    public void getArmyAndCountryFromFirebase() throws ExecutionException, InterruptedException, IOException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        int player1Size = 0;
        int player2Size = 0;
        int player3Size = 0;
        int player4Size = 0;
        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {

                String currentID = String.valueOf(armyAndCountryID.get("playerID"));

                switch (currentID) {
                    case "1":
                        player1Size += 1;

                        break;
                    case "2":
                        player2Size += 1;

                        break;
                    case "3":
                        player3Size += 1;

                        break;
                    case "4":
                        player4Size += 1;

                        break;
                }

                if (player1Size == arrayCountryData.size()) {
                    VictoryController victoryController = new VictoryController();
                    victoryController.switchToVictoryScreen("RED");
                } else if (player2Size == arrayCountryData.size()) {
                    VictoryController victoryController = new VictoryController();
                    victoryController.switchToVictoryScreen("GREEN");
                } else if (player3Size == arrayCountryData.size()) {
                    VictoryController victoryController = new VictoryController();
                    victoryController.switchToVictoryScreen("BLUE");
                } else if (player4Size == arrayCountryData.size()) {
                    VictoryController victoryController = new VictoryController();
                    victoryController.switchToVictoryScreen("ORANGE");
                }


            }
        }
    }


    public int getArmyFirebase(String ButtonID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            int count = 0;
            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(ButtonID)) {
                    String currentArmies = arrayCountryData.get(count).get("army").toString();
                    int firstArmy = Integer.parseInt(currentArmies);
                    return firstArmy;
                }
                count += 1;
            }

        }
        return 0;
    }

    public void setArmyFirebase(String ButtonID, int newArmies) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            int count = 0;
            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(ButtonID)) {
                    System.out.println(arrayCountryData.get(count).put("army", newArmies));
                    break;
                }
                count += 1;
            }
            docRef.update("countries", arrayCountryData);
        }
    }


    public boolean getNeighborsFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;

            ArrayList<String> arraySelectedCountries = gameModel.getSelectedCountries();

            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(arraySelectedCountries.get(0))) {

                    arrayCountryData.get(count).get("neighbor");
                    ArrayList x = (ArrayList) arrayCountryData.get(count).get("neighbor");

                    if (x.contains(arraySelectedCountries.get(1))) {
                        return true;
                    } else {
                        gameModel.clearSelectedCountries();
                        return false;
                    }
                }
                count += 1;
            }

        }


        return false;
    }

    public void nextTurnIDFirebase() throws ExecutionException, InterruptedException {

        if (canEnd) {
            int toUpdate;

            DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            String stringID = document.get("gamestateTurnID").toString();
            toUpdate = Integer.parseInt(stringID.toString());
            if (stringID.equals("4")) {
                ApiFuture<WriteResult> GamestateID = docRef.update("gamestateTurnID", 1);
            } else {
                ApiFuture<WriteResult> GamestateID = docRef.update("gamestateTurnID", toUpdate + 1);
            }
            gameModel.setPhaseID(1);
        }
    }


    public boolean comparePlayerIDtoTurnIDFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (State.TurnID == Integer.parseInt(document.get("gamestateTurnID").toString())) {
            System.out.println("Jij bent aan de beurt " + State.TurnID);
            return true;
        } else {
            return false;
        }

    }


    //Todo zorg ervoor dat via de map de 2 countryID's worden meegegeven
    public void attackPlayer(String countryCodeAttacker, String countryCodeDefender) {

    }

    public void setArmies(Button button, int armies) {
        button.setText(String.valueOf(armies));
    }


    public void countryListener() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            documentSnapshot.getData().get("countries");
            for (Button button : buttons) {
                Platform.runLater(() -> {
                    try {
                        button.setText(String.valueOf(getArmyFirebase(button.getId().split("c")[1])));
                    } catch (ExecutionException | InterruptedException executionException) {
                        executionException.printStackTrace();
                    }
                });
            }
        });
    }

    public void addSoldiersToOwnCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;
            //Pak alle dingen in de countries field in firebase
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.valueOf(armyAndCountryID.get("army").toString());
                    arrayCountryData.get(count).put("army", (firebaseArmies + 2));
                    docRef.update("countries", arrayCountryData);
                }
                count++;
            }
        }
    }


    public void removeSoldiersFromOwnCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;
            //Pak alle dingen in de countries field in firebase
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.valueOf(armyAndCountryID.get("army").toString());
                    System.out.println("De firebase - 2 is : " + (firebaseArmies - 2));
                    arrayCountryData.get(count).put("army", (firebaseArmies - 2));
                    docRef.update("countries", arrayCountryData);
                }
                count++;
            }
        }
    }

    public void removeArmiesFromPlayer(String country) throws ExecutionException, InterruptedException, IOException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;
            //Pak alle dingen in de countries field in firebase
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.valueOf(armyAndCountryID.get("army").toString());
                    if (firebaseArmies == 1) {
                        arrayCountryData.get(count).put("playerID", State.TurnID);
                        docRef.update("countries", arrayCountryData);
                        Thread.sleep(50);
                        //TODO verander kleur op map
                        Platform.runLater(() -> {
                            try {
                                setCountryColorStartGame();
                            } catch (ExecutionException | InterruptedException | IOException e) {
                                e.printStackTrace();
                            }

                        });
                        System.out.println("gelukt!!");
//                        System.out.println("Countries: " + countries);
                    } else {
                        arrayCountryData.get(count).put("army", firebaseArmies - 1);
                        docRef.update("countries", arrayCountryData);
                    }
                }
                count++;
            }

        }
    }

    public boolean checkArmiesOnCountry(String country, int number) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            //Pak alle dingen in de countries field in firebase
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.valueOf(armyAndCountryID.get("army").toString());
                    if (firebaseArmies >= number) {
                        System.out.println("Je hebt genoeg armies om aan te vallen");
                        return true;
                    } else {
                        System.out.println("Je hebt niet genoeg armies om aan te vallen");
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkOwnPlayerCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebasePlayerID = Integer.valueOf(armyAndCountryID.get("playerID").toString());
                    if (firebasePlayerID == State.TurnID) {
                        System.out.println("Het is je eigen land");
                        return true;
                    } else {
                        System.out.println("Dit is niet je land");
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkEnemyPlayerCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebasePlayerID = Integer.valueOf(armyAndCountryID.get("playerID").toString());
                    if (firebasePlayerID == State.TurnID) {
                        System.out.println("Het is je eigen land");
                        return false;
                    } else {
                        System.out.println("Je mag hem aanvallen");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void getButtonID(ActionEvent event) throws ExecutionException, InterruptedException, IOException {


        Button buttonid = (Button) event.getSource();
        System.out.println(event.getSource() + "dit is de source");
        System.out.println(buttonid.getId().split("c")[1]);
        String buttonIdCode = buttonid.getId().split("c")[1];


        // if observerItem phaseID == 1 clicking on a country will add armies
        if (comparePlayerIDtoTurnIDFirebase()) {
            if (gameModel.getPhaseID() == 1) {
                if (checkOwnPlayerCountry(buttonIdCode)) {
                    spelbordViewController.showDeployPhase();
                    int oldArmies = getArmyFirebase(buttonIdCode);
                    //TODO NIET VERGETEN BEIDEN TE VERANDEREN NAAR 4
                    setArmyFirebase(buttonIdCode, oldArmies + 10);
                    buttonid.setText(String.valueOf(oldArmies + 10));
                    gameModel.updatePhaseID();
                    Platform.runLater(() -> {
                        spelbordViewController.hideDeployPhase();
                        spelbordViewController.showAttackPhase();
                        spelbordViewController.hideCardIcon();
                    });
                }
            } else if (gameModel.getPhaseID() == 2) {

                System.out.println("now you cant update armies, only attack scrub");
                if (gameModel.getSelectedCountries() == null || gameModel.getSelectedCountries().size() < 1) {
                    gameModel.clearSelectedCountries();
                    System.out.println("check if exists");
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        if (checkArmiesOnCountry(buttonIdCode, 2)) {
                            gameModel.clearSelectedCountries(buttonIdCode);
                        }
                    }
                } else if (gameModel.getSelectedCountries().size() == 1) {
                    // Checken van eigen land of enemy land
                    ArrayList<String> arraySelectedCountries = gameModel.getSelectedCountries();
                    //TODO deze functie (checkEnemyPlayerCountry)kan in principe in de getNeighborsFirebase() functie
                    if (checkEnemyPlayerCountry(buttonIdCode)) {
                        gameModel.clearSelectedCountries(buttonIdCode);
                        if (getNeighborsFirebase()) {

                            //todo check if both players have at least 2 armies
                            int attackersArmy = getArmyFirebase(gameModel.getSelectedCountries().get(0));
                            int defendersArmy = getArmyFirebase(gameModel.getSelectedCountries().get(1));

                            if (attackersArmy >= 2 && defendersArmy >= 1) {
                                ArrayList<Integer> dice1 = dice.roll(1);
                                ArrayList<Integer> dice2 = dice.roll(1);

                                for (int num : dice1) {
                                    System.out.println("Aanvallende dobbelsteen is " + num);
                                }
                                for (int num : dice2) {
                                    System.out.println("Verdedigende dobbelsteen is " + num);
                                }

                                if (dice1.get(0) > dice2.get(0)) {
                                    System.out.println("defender loses an army");
                                    removeArmiesFromPlayer(gameModel.getSelectedCountries().get(1));
                                } else {
                                    System.out.println("attacker loses an army");
                                    removeArmiesFromPlayer(gameModel.getSelectedCountries().get(0));
                                }
                                dice1.clear();
                                dice2.clear();

                                gameModel.clearSelectedCountries();
                                Platform.runLater(() -> spelbordViewController.showFortifyIcon());
                            }
                        }
                    }
                }
                System.out.println("In de selectedcountries zitten : " + gameModel.getSelectedCountries());
            } else if (gameModel.getPhaseID() == 3) {
//                spelbordViewController.hideFortifyIcon();
                System.out.println("HET IS PHASE 3");
                if (gameModel.getSelectedCountries() == null || gameModel.getSelectedCountries().size() < 1) {
                    gameModel.clearSelectedCountries();
                    System.out.println("check if exists");
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        if (checkArmiesOnCountry(buttonIdCode, 3)) {
                            gameModel.clearSelectedCountries(buttonIdCode);
                        }
                    }
                } else if (gameModel.getSelectedCountries().size() == 1) {
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        gameModel.clearSelectedCountries(buttonIdCode);
                        if (gameModel.getSelectedCountries().get(0) == gameModel.getSelectedCountries().get(1)) {
                            System.out.println("Je kan niet op je geklikte land zetten");
                            gameModel.clearSelectedCountries();
                        } else if (getNeighborsFirebase()) {
                            removeSoldiersFromOwnCountry(gameModel.getSelectedCountries().get(0));
                            Thread.sleep(50);
                            addSoldiersToOwnCountry(gameModel.getSelectedCountries().get(1));
                            gameModel.clearSelectedCountries();
                        }
                    }
                }
            }
        } else {
            System.out.println("Je bent niet aan de beurt");
        }
    }


    // if observerItem phaseID == 2 clicking on a country will try to stage an attack

    public void endTurn() throws ExecutionException, InterruptedException {
        nextTurnIDFirebase();
    }

    public void registerObserver(SpelbordObserver sbv) {
        spelbordModel.register(sbv);
    }

    public void setColorCountry(ImageView imageLand, double playerColor) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(playerColor);
        colorAdjust.setSaturation(0.8);

        imageLand.setEffect(colorAdjust);
    }


    private double getPlayerColor(int id) {
        switch (id) {
            case 1:
                return State.RED;
            case 2:
                return State.GREEN;
            case 3:
                return State.BLUE;
            case 4:
                return State.ORANGE;
        }

        return State.RED;
    }

    public void setCountryColorStartGame() throws ExecutionException, InterruptedException, IOException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsKey("playerID")) {
                    String countryID = (String) armyAndCountryID.get("countryID");
                    Long playerID = (Long) armyAndCountryID.get("playerID");
                    for (ImageView country : spelbordViewController.getCountriesArray()) {
                        if (country.getId().equals(countryID)) {
                            double color = getPlayerColor(playerID.intValue());
                            setColorCountry(country, color);
                        }
                    }
                }
            }
        }
    }

    public void fortifyButton() {
        System.out.println(gameModel.getPhaseID());
        gameModel.updatePhaseID();
        Platform.runLater(() -> {
            spelbordViewController.hideFortifyIcon();
            spelbordViewController.hideAttackPhase();
            spelbordViewController.showFortifyPhase();
        });
        System.out.println("NU is het " + gameModel.getPhaseID());
    }
}


