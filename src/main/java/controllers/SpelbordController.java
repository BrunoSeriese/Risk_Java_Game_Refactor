package controllers;

import application.Main;
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
import models.LobbyModel;
import models.SpelbordModel;
import views.SpelbordViewController;

import java.io.IOException;
import java.util.*;
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

    public ImageView[] getCountries() {
        return this.countries;
    }

    public void setCountries(ImageView[] countries) {
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
        }
        return gameModel;
    }


    EventHandler<MouseEvent> eventHandler = e -> System.out.println("ER is geklikt");


    public void attachlistener() {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {

                int firebaseTurnID = Integer.parseInt(documentSnapshot.getData().get("gamestateTurnID").toString());
                gameModel.setTurnID(firebaseTurnID);


                try {
                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(200);
                            getArmyAndCountryFromFirebase();
                            Thread.sleep(200);
                        }catch (ExecutionException executionException){
                                executionException.printStackTrace();
                        }

                        catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch(InterruptedException interruptedException){
                            interruptedException.printStackTrace();

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
            Main.stage.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            canEnd = true;

        } else {
            canEnd = false;
        }
    }

    public SpelbordController() {
        gameModel = getGameModelInstance();
        spelbordModel = SpelbordModel.getSpelbordModelInstance();
        attachlistener();
        countryListener();

    }

    public void setArmyAndCountryInFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        assert document.get("countries") != null;

        spelbordModel.CountriesAndIdMap();
        Map<String, Object> data = new HashMap<>();
        data.put("countries", spelbordModel.getCountries());

        ApiFuture<WriteResult> result = docRef.update(data);


    }

    public void getArmyAndCountryFromFirebase() throws ExecutionException, InterruptedException, IOException {


        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        int player1Size = 0;
        int player2Size = 0;
        int player3Size = 0;
        int player4Size = 0;
        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

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
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            int count = 0;
            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(ButtonID)) {
                    String currentArmies = arrayCountryData.get(count).get("army").toString();
                    return Integer.parseInt(currentArmies);
                }
                count += 1;
            }

        }
        return 0;
    }

    public void setArmyFirebase(String ButtonID, int newArmies) throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            int count = 0;
            assert arrayCountryData != null;
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
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;

            ArrayList<String> arraySelectedCountries = gameModel.getSelectedCountries();

            assert arrayCountryData != null;
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

            DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            String stringID = Objects.requireNonNull(document.get("gamestateTurnID")).toString();
            toUpdate = Integer.parseInt(stringID);
            if (stringID.equals("4")) {
                ApiFuture<WriteResult> GamestateID = docRef.update("gamestateTurnID", 1);
            } else {
                ApiFuture<WriteResult> GamestateID = docRef.update("gamestateTurnID", toUpdate + 1);
            }
            gameModel.setPhaseID(1);
        }
    }


    public boolean comparePlayerIDtoTurnIDFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        return GameModel.TurnID == Integer.parseInt(Objects.requireNonNull(document.get("gamestateTurnID")).toString());

    }


    public void setArmies(Button button, int armies) {
        button.setText(String.valueOf(armies));
    }


    public void countryListener() {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            assert documentSnapshot != null;
            Objects.requireNonNull(documentSnapshot.getData()).get("countries");
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
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;
            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.parseInt(armyAndCountryID.get("army").toString());
                    arrayCountryData.get(count).put("army", (firebaseArmies + 2));
                    docRef.update("countries", arrayCountryData);
                }
                count++;
            }
        }
    }


    public void removeSoldiersFromOwnCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;
            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.parseInt(armyAndCountryID.get("army").toString());
                    arrayCountryData.get(count).put("army", (firebaseArmies - 2));
                    docRef.update("countries", arrayCountryData);
                }
                count++;
            }
        }
    }

    public void removeArmiesFromPlayer(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;
            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.parseInt(armyAndCountryID.get("army").toString());
                    if (firebaseArmies == 1) {
                        arrayCountryData.get(count).put("playerID", GameModel.TurnID);
                        docRef.update("countries", arrayCountryData);
                        Thread.sleep(50);
                        Platform.runLater(() -> {
                            try {
                                setCountryColorStartGame();
                            } catch (ExecutionException | InterruptedException | IOException e) {
                                e.printStackTrace();
                            }
                        });
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
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.parseInt(armyAndCountryID.get("army").toString());
                    return firebaseArmies >= number;
                }
            }
        }
        return false;
    }

    public boolean checkOwnPlayerCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebasePlayerID = Integer.parseInt(armyAndCountryID.get("playerID").toString());
                    return firebasePlayerID == GameModel.TurnID;
                }
            }
        }
        return false;
    }

    public boolean checkEnemyPlayerCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            assert arrayCountryData != null;
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebasePlayerID = Integer.parseInt(armyAndCountryID.get("playerID").toString());
                    return firebasePlayerID != GameModel.TurnID;
                }
            }
        }
        return false;
    }

    public void getButtonID(ActionEvent event) throws ExecutionException, InterruptedException {


        Button buttonid = (Button) event.getSource();
        String buttonIdCode = buttonid.getId().split("c")[1];



        if (comparePlayerIDtoTurnIDFirebase()) {
            if (gameModel.getPhaseID() == 1) {
                if (checkOwnPlayerCountry(buttonIdCode)) {
                    spelbordViewController.showDeployPhase();
                    int oldArmies = getArmyFirebase(buttonIdCode);
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
                if (gameModel.getSelectedCountries() == null || gameModel.getSelectedCountries().size() < 1) {
                    gameModel.clearSelectedCountries();
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        if (checkArmiesOnCountry(buttonIdCode, 2)) {
                            gameModel.clearSelectedCountries(buttonIdCode);
                        }
                    }
                } else if (gameModel.getSelectedCountries().size() == 1) {
                    ArrayList<String> arraySelectedCountries = gameModel.getSelectedCountries();
                    if (checkEnemyPlayerCountry(buttonIdCode)) {
                        gameModel.clearSelectedCountries(buttonIdCode);
                        if (getNeighborsFirebase()) {


                            int attackersArmy = getArmyFirebase(gameModel.getSelectedCountries().get(0));
                            int defendersArmy = getArmyFirebase(gameModel.getSelectedCountries().get(1));

                            if (attackersArmy >= 2 && defendersArmy >= 1) {
                                ArrayList<Integer> dice1 = dice.roll(1);
                                ArrayList<Integer> dice2 = dice.roll(1);

                                if (dice1.get(0) > dice2.get(0)) {
                                    removeArmiesFromPlayer(gameModel.getSelectedCountries().get(1));
                                } else {
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
            } else if (gameModel.getPhaseID() == 3) {
                if (gameModel.getSelectedCountries() == null || gameModel.getSelectedCountries().size() < 1) {
                    gameModel.clearSelectedCountries();
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        if (checkArmiesOnCountry(buttonIdCode, 3)) {
                            gameModel.clearSelectedCountries(buttonIdCode);
                        }
                    }
                } else if (gameModel.getSelectedCountries().size() == 1) {
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        gameModel.clearSelectedCountries(buttonIdCode);
                        if (gameModel.getSelectedCountries().get(0).equals(gameModel.getSelectedCountries().get(1))) {
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
        }
    }


    public void endTurn() throws ExecutionException, InterruptedException {
        nextTurnIDFirebase();
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
                return GameModel.RED;
            case 2:
                return GameModel.GREEN;
            case 3:
                return GameModel.BLUE;
            case 4:
                return GameModel.ORANGE;
        }

        return GameModel.RED;
    }

    public void setCountryColorStartGame() throws ExecutionException, InterruptedException, IOException {
        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            assert arrayCountryData != null;
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
        gameModel.updatePhaseID();
        Platform.runLater(() -> {
            spelbordViewController.hideFortifyIcon();
            spelbordViewController.hideAttackPhase();
            spelbordViewController.showFortifyPhase();
        });
    }
}


