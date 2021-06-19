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

    public static SpelbordController getSpelbordControllerInstance() {
        if (spelbordController == null) {
            spelbordController = new SpelbordController();
            System.out.println("nieuwe instantie van SpelbordController is aangemaakt");
        }
        return spelbordController;
    }

    public static GameModel getGameModelInstance() {
        if (gameModel == null) {
            gameModel = new GameModel(1);
            System.out.println("nieuwe instantie van GameModel is aangemaakt");
        }
        return gameModel;
    }

    public static SpelbordViewController getSpelbordViewControllerInstance() {
        if (spelbordViewController == null) {
            spelbordViewController = new SpelbordViewController();
            System.out.println("nieuwe instantie van SpelbordController is aangemaakt");
        }
        return spelbordViewController;
    }

    EventHandler<MouseEvent> eventHandler = e -> System.out.println("ER is geklikt");

//    public void armiessistener() {
//        DSettableApiFuture<List<DocumentChange>> future = SettableApiFuture.create();


//            if (documentSnapshot != null) {
//
//
//                ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) documentSnapshot.get("countries");
//
////                int count = 0;
//
//                ArrayList arraySelectedCountries = gameModel.getSelectedCountries();
//                System.out.println("dit is selected countries:   " + arraySelectedCountries);
//
//                for (HashMap armyAndCountryID : arrayCountryData) {
//                    System.out.println("De armies is : " + armyAndCountryID.get("army") + " van land " + armyAndCountryID.get("countryID"));
//
////                    if (armyAndCountryID.containsValue(arraySelectedCountries.get(0))) {
////                        System.out.println("dit is selected countries:   " + arraySelectedCountries);
////                        arrayCountryData.get(count).get("neighbor");
////                        ArrayList x = (ArrayList) arrayCountryData.get(count).get("neighbor");
////
////                        if (x.contains(arraySelectedCountries.get(1))) {
////                            System.out.println("dit is selected countries:   " + arraySelectedCountries);
////                            System.out.println("Je mag aanvallen");
////                        } else {
////                            System.out.println("nee helaas");
////                            gameModel.clearSelectedCountries();
////                        }
////                    }
////                    count += 1;
//                }
//                System.out.println("werkt" + arraySelectedCountries);
//
//            }
//            }
//        });
//    }

    public void attachlistener() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {
//                System.out.println(documentSnapshot.getData().get("gamestateTurnID"));
//                System.out.println(State.TurnID);
                int firebaseTurnID = Integer.valueOf(documentSnapshot.getData().get("gamestateTurnID").toString());
                gameModel.setTurnID(firebaseTurnID);

                try {
                    startMainLoop();
//                    armiesListener();
//                    countryListener();
                } catch (ExecutionException | InterruptedException executionException) {
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
            System.out.println("Je bent niet aan de beurt, TurnID " + gameModel.getTurnID() + " is aan de beurt");
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
            System.out.println("this shit is already made");
        } else {
            spelbordModel.CountriesAndIdMap();
            Map<String, Object> data = new HashMap<>();
            data.put("countries", spelbordModel.getCountries());

//            CountryModel countryModel = new CountryModel("NA1");
            ApiFuture<WriteResult> result = docRef.update(data);

            System.out.println("Data naar firebase: " + result);
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
//                System.out.println(armyAndCountryID.get("playerID"));
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

                if (player1Size == arrayCountryData.size()){
                    System.out.println("player 1 wins!");
                }
                else if (player2Size == arrayCountryData.size()){
                    System.out.println("player 2 wins!");
                }
                else if (player3Size == arrayCountryData.size()){
                    System.out.println("player 3 wins!");
                }
                else if (player4Size == arrayCountryData.size()){
                    System.out.println("player 4 wins!");
                }

            }
        } else {
            System.out.println("No document found!");
        }
    }


    public int getArmyFirebase(String ButtonID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata:    " + arrayCountryData);
            int count = 0;
            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(ButtonID)) {
//                        System.out.println(armyAndCountryID);
//                    System.out.println(arrayCountryData.get(count).get("army"));
                    String currentArmies = arrayCountryData.get(count).get("army").toString();
                    int firstArmy = Integer.parseInt(currentArmies);
//                    System.out.println(firstArmy);
                    return firstArmy;
                }
                count += 1;
            }

        } else {
            System.out.println("No document found!");
        }
        return 0;
    }

    public void setArmyFirebase(String ButtonID, int newArmies) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println(arrayCountryData);

            int count = 0;

            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(ButtonID)) {
//                        System.out.println(armyAndCountryID);
                    System.out.println(arrayCountryData.get(count).put("army", newArmies));
//                    System.out.println(arrayCountryData);
//                    System.out.println(count);
                    break;
                }
                count += 1;
            }
            docRef.update("countries", arrayCountryData);
        } else {
            System.out.println("No document found!");
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
                System.out.println(playerData);  //loopt door de arrays van firestore zodat je ze apart kan zien van elke player

                Map.Entry<String, Long> entry = (Map.Entry<String, Long>) playerData.entrySet().iterator().next(); //elke
                String turnIdKey = entry.getKey(); //pakt de key van elke 1e Key-Value combo
                Long turnIdValue = entry.getValue(); //pakt de bijbehorende value van die 1e key
                System.out.println(turnIdKey + " = " + turnIdValue); //print beide key en value
            }
        } else {
            System.out.println("No document found!");
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
            System.out.println("dit is selected countries:   " + arraySelectedCountries);

            for (HashMap armyAndCountryID : arrayCountryData) {

                if (armyAndCountryID.containsValue(arraySelectedCountries.get(0))) {
                    System.out.println("dit is selected countries:   " + arraySelectedCountries);

                    arrayCountryData.get(count).get("neighbor");
                    ArrayList x = (ArrayList) arrayCountryData.get(count).get("neighbor");

                    if (x.contains(arraySelectedCountries.get(1))) {
                        System.out.println("dit is selected countries:   " + arraySelectedCountries);

                        System.out.println("Je mag aanvallen");
                        return true;
                    } else {
                        System.out.println("Je mag alleen landen aanvallen dat naast je zit");
                        gameModel.clearSelectedCountries();
                        return false;
                    }
                }
                count += 1;
            }
            System.out.println("werkt" + arraySelectedCountries);
        } else {
            System.out.println("No document found!");

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
        }
    }


//    TODO zorg ervoor dat de lokale playerID wordt aangesproken hier als playerLocalID, maybe met final String?

    public boolean comparePlayerIDtoTurnIDFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (State.TurnID == Integer.valueOf(document.get("gamestateTurnID").toString())) {
            System.out.println("Jij bent aan de beurt " + State.TurnID);
            return true;
        } else {
            System.out.println("nah fam, not your turn");
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
//            System.out.println("IK BESTAAAAAAAAAAAAAAAA");
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
            docRef.update("countries", arrayCountryData);
        }
    }

    public boolean checkArmiesOnCountry(String country) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");

            //Pak alle dingen in de countries field in firebase
            for (HashMap armyAndCountryID : arrayCountryData) {
                if (armyAndCountryID.containsValue(country)) {
                    int firebaseArmies = Integer.valueOf(armyAndCountryID.get("army").toString());
                    if (firebaseArmies >= 2) {
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

            //Pak alle dingen in de countries field in firebase
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

            //Pak alle dingen in de countries field in firebase
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

        getArmyAndCountryFromFirebase();
        Button buttonid = (Button) event.getSource();
        System.out.println(event.getSource() + "dit is de source");
        System.out.println(buttonid.getId().split("c")[1]);
        String buttonIdCode = buttonid.getId().split("c")[1];


        // if observerItem phaseID == 1 clicking on a country will add armies
        if (comparePlayerIDtoTurnIDFirebase()) {
            if (gameModel.getPhaseID() == 1) {
                if (checkOwnPlayerCountry(buttonIdCode)) {
                    int oldArmies = getArmyFirebase(buttonIdCode);
                    //TODO NIET VERGETEN TE VERANDEREN NAAR 4
                    setArmyFirebase(buttonIdCode, oldArmies + 10);
                    buttonid.setText(String.valueOf(oldArmies + 4));
                    gameModel.updatePhaseID();
                }
            } else if (gameModel.getPhaseID() == 2) {
                System.out.println("now you cant update armies, only attack scrub");
                if (gameModel.getSelectedCountries() == null || gameModel.getSelectedCountries().size() < 1) {
                    gameModel.clearSelectedCountries();
                    System.out.println("check if exists");
                    if (checkOwnPlayerCountry(buttonIdCode)) {
                        if (checkArmiesOnCountry(buttonIdCode)) {
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
//                                if (dice1.get(1) > dice2.get(1)) {
//                                    System.out.println("defender loses an army");
//                                } else {
//                                    System.out.println("attacker loses an army");
//                                }

                                gameModel.clearSelectedCountries();
                            }
                        }
                    }
                }
                System.out.println("In de selectedcountries zitten : " + gameModel.getSelectedCountries());
            }
        } else {
            System.out.println("Je bent niet aan de beurt");
        }
    }


    // if observerItem phaseID == 2 clicking on a country will try to stage an attack


    public void handleClicky() {
        System.out.println("CLICKYYY MOFO");
    }

    public void showCards() {
        System.out.println("showcard");
    }

    public void showPlayers() {
        System.out.println("showplayer");
    }

    public void rollDice() {
        System.out.println("rolldice");
    }

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
            System.out.println("Ding bestaat");

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("test1" + arrayCountryData);
            for (HashMap armyAndCountryID : arrayCountryData) {
//                System.out.println("test2" + armyAndCountryID);
                if (armyAndCountryID.containsKey("playerID")) {
//                    System.out.println("PLAYER ID IS: " + armyAndCountryID.get("playerID"));

                    String countryID = (String) armyAndCountryID.get("countryID");
                    Long playerID = (Long) armyAndCountryID.get("playerID");

//                    System.out.println("COUNTRY ID IS" + countryID);
                    System.out.println("In de countries zitten " + countries);

                    for (ImageView country : spelbordViewController.getCountriesArray()) {
//                        System.out.println("COUNTRY (IMAGE) ID IS" + country.getId());

                        if (country.getId().equals(countryID)) {


                            double color = getPlayerColor(playerID.intValue());
                            setColorCountry(country, color);
                        }
                    }
                }
            }
        }


//        for (ImageView country : countries) {
//            if (country.getId().equals("NA1")) {
//                setColorCountry(country, getPlayerColor((int) document.get("playerID")));
//                System.out.println("GELUKT");
//            }
//        }

//        DocumentSnapshot test = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players").get().get();
//        SpelbordViewController spelbordViewController = new SpelbordViewController();
//        System.out.println("array "+ spelbordViewController.addImageViewArray());


//        if (document.exists()) {
//
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//
//            for (HashMap armyAndCountryID : arrayCountryData) {
//                System.out.println("kleurentest: " + armyAndCountryID); //gets alle nodige info
//
////                if (armyAndCountryID.containsKey("countryID")) {
////                    System.out.println(armyAndCountryID.get("countryID"));//prints country id
////                }
//                if (armyAndCountryID.containsKey("playerID")) {
//                    System.out.println(armyAndCountryID.get("playerID")); //prints player id
//
//                    if ((Long) armyAndCountryID.get("playerID") == 1) {
//                        armyAndCountryID.get("countryID");
//                        setColorCountry((ImageView) armyAndCountryID.get("countryID") , State.RED); //TODO fix dat ik armyAndCountryID.get("countryID") kan casten naar ImageView zodat deze method werkt
//                    }
//                    else if ((Long) armyAndCountryID.get("playerID") == 2) {
//                        armyAndCountryID.get("countryID");
//                        setColorCountry((ImageView) armyAndCountryID.get("countryID"), State.BLUE);//TODO fix dat ik armyAndCountryID.get("countryID") kan casten naar ImageView zodat deze method werkt
//                    }
//                    else if ((Long) armyAndCountryID.get("playerID") == 3) {
//                        armyAndCountryID.get("countryID");
//                        setColorCountry((ImageView) armyAndCountryID.get("countryID"), State.GREEN);//TODO fix dat ik armyAndCountryID.get("countryID") kan casten naar ImageView zodat deze method werkt
//                    }
//                    else if ((Long) armyAndCountryID.get("playerID") == 4) {
//                        armyAndCountryID.get("countryID");
//                        setColorCountry((ImageView) armyAndCountryID.get("countryID"), State.ORANGE);//TODO fix dat ik armyAndCountryID.get("countryID") kan casten naar ImageView zodat deze method werkt
//                    }
//                }
//            }
//        }
//    }
    }
}


//    //TODO NIET AAN DEZE 4 METHODS KOMEN
//


//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata: " + arrayCountryData);
//
//            arrayCountryData.
//                    HashMap newData = new HashMap();
//            newData.put("army", 4);
//
//            ArrayList<HashMap> testArray = new ArrayList<>();
//            testArray.add(newData);
////            long newValue = (long) oldValue.put("army", 4);
//
//
//            System.out.println("test new value: " + newValue);
//            docRef.update("countries", newValue);


//
//    public void setArmyFirebase(int arrayNumber, int newArmies) throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        if (document.exists()) {
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata: " + arrayCountryData);
//
////            ArrayList<HashMap> data = new ArrayList<HashMap>();
////            data.add(0, ;
//
//            Map<String, Integer> dataMap = new HashMap<>();
//            dataMap.put("army", newArmies);
////
////            ArrayList<String> countries = new ArrayList<>();
////            countries.add(arrayNumber, "test");
//
//
//            docRef.update("countries", dataMap);
//        }
//        else {
//            System.out.println("No document found!");
//        }
//    }
//
//
//
//    public void setPlayerIDtoCountry() throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//        if (document.exists()) {
//
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata:    "+arrayCountryData);
//            for (HashMap armyAndCountryID : arrayCountryData) {
//                System.out.println(armyAndCountryID);
//
//            }
//        } else {
//            System.out.println("No document found!");
//        }
//    }
//
//    public void getPlayerIDtoCountry() throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//        if (document.exists()) {
//
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata:    "+arrayCountryData);
//            for (HashMap armyAndCountryID : arrayCountryData) {
//                System.out.println(armyAndCountryID);
//
//            }
//        } else {
//            System.out.println("No document found!");
//        }
//    }
//    //TODO NIET AANKOMEN IS MINE ^^^^^^^^


//    IK WEET BTW NIET OF DIT IN DE CONTROLLER MOET OF IN DE MODEL!!!


//maak aantal spelers gelijk aan hoeveel mensen in lobby, dus 4 nieuwe spelers.
// de usernames kan je met .getUserName fzo pakken, kijk ff in de rest van de classes van jansen.

// de code kiest een random kleur en assigned die tot de speler OFFFFFF we kunnen 4 standaard kleuren kiezen bijvoorbeeld, rood blauw groen oranje.


//als het spel is gestart heeft elke player een kleur en speler attributes, zoals regions cards etc...
//ik maak nog een knopje met END TURN, dan als je erop klikt dat de beurt dan overgaat naar de volgende,


// deze knop staat in de map Images en die mag alleen visible worden als degene heeft aangevallen/reinforced. maar dat komt later wel


//if player has all regions, set hasWon = true, stop de turn loop >>> go to results screen


//als een speler die 3 cards heeft dan schakelt de canExchageCards naar = true en dan verschijnt er een knopje die we nog moeten maken en die hele interface nog met, 'Trade in' bijvoorbeeld
//dan verschijnt er een scherm met het aantal troepen dat diegene krijgt en dan klikt ie op "OK" en dan ontvangt hij de troepen en gaan die kaarten weg.



