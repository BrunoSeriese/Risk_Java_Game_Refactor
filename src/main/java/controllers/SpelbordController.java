package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import models.GameModel;
import models.PlayerModel;
import models.SpelbordModel;
import observers.SpelbordObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SpelbordController {

    static GameModel gameModel;
    static SpelbordModel spelbordModel;
    private SpelbordModel map;
    private boolean canEnd;
    private int turnID;
    private boolean gameOver;
    static SpelbordController spelbordController;
    //    gameModel = loginController.getGameModelInstance();
//    LoginController loginController = new LoginController();


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

    EventHandler<MouseEvent> eventHandler = e -> System.out.println("ER is geklikt");

    public void attachlistener() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {
                System.out.println(documentSnapshot.getData().get("gamestateTurnID"));
                System.out.println(State.TurnID);
                int firebaseTurnID = Integer.valueOf(documentSnapshot.getData().get("gamestateTurnID").toString());
                System.out.println("hij is niet gezet");
                gameModel.setTurnID(firebaseTurnID);
                System.out.println("hij is gezet");
                startMainLoop();
            }
        });
    }

    public void startMainLoop(){
        if (gameModel.getTurnID() == State.TurnID) {
            System.out.println("Jij bent aan de beurt " + gameModel.getTurnID());
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
        attachlistener();
    }

    public void setArmyAndCountryInFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
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
        }
    }

    public void getArmyAndCountryFromFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            for (HashMap armyAndCountryID : arrayCountryData) {
                System.out.println(armyAndCountryID);
            }
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

    public long comparePlayerIDtoTurnIDFirebase(String playerLocalID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (playerLocalID.equals(document.get("gamestateTurnID").toString())) {
            System.out.println("this is your turn!");
        } else {
            System.out.println("nah fam, not your turn");
        }

        return (long) document.getData().get("gamestateTurnID");
    }

    //TODO matchen met code hierboven
    public void nextTurn() {
        if (gameModel.isGameOver() == true) {
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

    public void getButtonID(ActionEvent event) {
        System.out.println("clickedAAAAAAAAAA!!!");
        Button buttonid = (Button) event.getSource();
        System.out.println(buttonid);
    }

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

//    //TODO NIET AAN DEZE 4 METHODS KOMEN
//
//    public void setArmyFirebase() throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        if (document.exists()) {
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata: " + arrayCountryData);
//
//            arrayCountryData.
//
//
//
//
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
//        }
//        else {
//            System.out.println("No document found!");
//        }
//    }
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
//    public void getArmyFirebase(int arrayNumber) throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//        if (document.exists()) {
//
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata:    "+arrayCountryData);
//            System.out.println(arrayCountryData.get(arrayNumber).get("army"));
//
//        } else {
//            System.out.println("No document found!");
//        }
//    }
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


}
