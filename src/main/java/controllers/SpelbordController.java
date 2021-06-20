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

    //    static SpelbordViewController spelbordViewController = getSpelbordViewControllerInstance();
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

    public static SpelbordController getSpelbordControllerInstance() {
        if (spelbordController == null) {
            spelbordController = new SpelbordController();
        }
        return spelbordController;
    }

    public static GameModel getGameModelInstance() {
        if (gameModel == null) {
            gameModel = new GameModel(1);
        }
        return gameModel;
    }


    EventHandler<MouseEvent> eventHandler;


    public void attachlistener() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {

                int firebaseTurnID = Integer.parseInt(documentSnapshot.getData().get("gamestateTurnID").toString());
                gameModel.setTurnID(firebaseTurnID);


                try {
                    Platform.runLater(() -> {
                        try {
                            getArmyAndCountryFromFirebase();
                        } catch (ExecutionException | InterruptedException executionException) {
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
//                    armiesListener();
//                    countryListener();

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
            //TODO hier komt de zetten en aanvallen van de game. Als laatst nextTurn()

            //ToDo zorg ervoor dat hier een mouse event listeren

            //functie viewer.garrison(current playerID)

        } else {
            canEnd = false;
        }
    }

    public SpelbordController() {
        gameModel = getGameModelInstance();
        spelbordModel = spelbordModel.getSpelbordModelInstance();

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
        if (document.get("countries") != null) {
        } else {
            spelbordModel.CountriesAndIdMap();
            Map<String, Object> data = new HashMap<>();
            data.put("countries", spelbordModel.getCountries());

//            CountryModel countryModel = new CountryModel("NA1");
            ApiFuture<WriteResult> result = docRef.update(data);

        }
    }

    public void getArmyAndCountryFromFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
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

                if (currentID.equals("1")) {
                    player1Size += 1;

                } else if (currentID.equals("2")) {
                    player2Size += 1;

                } else if (currentID.equals("3")) {
                    player3Size += 1;

                } else if (currentID.equals("4")) {
                    player4Size += 1;

                }

                if (player1Size == arrayCountryData.size()) {
                } else if (player2Size == arrayCountryData.size()) {
                } else if (player3Size == arrayCountryData.size()) {
                } else if (player4Size == arrayCountryData.size()) {
                }


            }
        } else {
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

        } else {
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
                    break;
                }
                count += 1;
            }
            docRef.update("countries", arrayCountryData);
        } else {
        }
    }


    //if the player turnID matches the gamestate turnID. then he can start his turn
    public void getPlayersFirebaseTurnID() throws ExecutionException, InterruptedException {

        //get benodigde stuff van firestore
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        //if lobbycode/collection van players bestaat ->
        if (document.exists()) {

            ArrayList<HashMap> arrayPlayerData = (ArrayList<HashMap>) document.get("players"); //zet alle data van 'players' in array wat hashmaps bevatten

            for (HashMap playerData : arrayPlayerData) {

                Map.Entry<String, Long> entry = (Map.Entry<String, Long>) playerData.entrySet().iterator().next(); //elke
                String turnIdKey = entry.getKey(); //pakt de key van elke 1e Key-Value combo
                Long turnIdValue = entry.getValue(); //pakt de bijbehorende value van die 1e key
            }
        } else {
        }
    }

    public boolean getNeighborsFirebase() throws ExecutionException, InterruptedException { //TODO BRUNO parameters geven van de buttonID fzo? als je klikt
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            int count = 0;

            ArrayList arraySelectedCountries = gameModel.getSelectedCountries();

            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(arraySelectedCountries.get(0))) {

                    arrayCountryData.get(count).get("neighbor");
                    ArrayList x = (ArrayList) arrayCountryData.get(count).get("neighbor");

                    if (x.contains(arraySelectedCountries.get(1))) {

                        return true;
                    } else {
                        if (gameModel.getPhaseID() == 2) {
                        } else if (gameModel.getPhaseID() == 3) {
                        }
                        gameModel.clearSelectedCountries();
                        return false;
                    }
                }
                count += 1;
            }
        } else {

        }
        return false;
    }

    public void nextTurnIDFirebase() throws ExecutionException, InterruptedException {

        if (canEnd) {
            int toUpdate;
            //get benodigde stuff van firestore
            DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");

            // haal de info van doc players op
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            // haal de info van gamestateTurnID op
            Object stringID = document.get("gamestateTurnID").toString();

            // maak toUpdate een int die gelijk staat aan de turnID uit firebase
            toUpdate = Integer.parseInt(stringID.toString());

            // als de stringID gelijk is aan 4 dan wordt de value naar 1 gezet. anders wordt toUpdate + 1 gebruikt
            if (stringID.equals("4")) {
                ApiFuture<WriteResult> GamestateID = docRef.update("gamestateTurnID", 1);
            } else {
                ApiFuture<WriteResult> GamestateID = docRef.update("gamestateTurnID", toUpdate + 1);
            }
            gameModel.setPhaseID(1);
        }
    }


//    TODO zorg ervoor dat de lokale playerID wordt aangesproken hier als playerLocalID, maybe met final String?

    public boolean comparePlayerIDtoTurnIDFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (State.TurnID == Integer.valueOf(document.get("gamestateTurnID").toString())) {
            return true;
        } else {
            return false;
        }

    }

    public void nextTurn() {
        if (gameModel.isGameOver()) {
            //end game. this should be called by an observer?
        } else if (gameModel.getTurnID() < 4) {
            gameModel.setTurnID(gameModel.getTurnID() + 1);
            // roept de volgende turn aan
            //nextTurnIDFirebase(lobbycode);
            map.turnInProgress(map.getPlayers(), new GameModel(gameModel.getTurnID()));
        } else if (gameModel.getTurnID() == 4) {
            gameModel.setTurnID(1);
            // roept de volgende turn aan
            //nextTurnIDFirebase(lobbycode);
            map.turnInProgress(map.getPlayers(), new GameModel(gameModel.getTurnID()));
        }
    }


    //Todo zorg ervoor dat via de map de 2 countryID's worden meegegeven
    public void attackPlayer(String countryCodeAttacker, String countryCodeDefender) {

    }

    public void setArmies(Button button, int armies) {
        button.setText(String.valueOf(armies));
    }

    public void incrementActionsTaken() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();


        docRef.update("actionsTaken", Integer.valueOf(document.getData().get("actionsTaken").toString()) + 1);
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
                        return true;
                    } else {
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
                        return true;
                    } else {
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
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void getButtonID(ActionEvent event) throws ExecutionException, InterruptedException, IOException {


        Button buttonid = (Button) event.getSource();
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

                if (gameModel.getSelectedCountries() == null || gameModel.getSelectedCountries().size() < 1) {
                    gameModel.clearSelectedCountries();
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
                                }
                                for (int num : dice2) {
                                }

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
//                spelbordViewController.hideFortifyIcon();
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
                        if (gameModel.getSelectedCountries().get(0) == gameModel.getSelectedCountries().get(1)) {
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
        gameModel.updatePhaseID();
        Platform.runLater(() -> {
            spelbordViewController.hideFortifyIcon();
            spelbordViewController.hideAttackPhase();
            spelbordViewController.showFortifyPhase();
        });
    }


}



