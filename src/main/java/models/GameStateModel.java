package models;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.io.Writer;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class GameStateModel {

    // spelbord model moet niet map zijn maar bijv private SpelbordModel map = firebase.get(currentMap)
    private SpelbordModel map;
    private int turnID;
    private boolean gameOver;
    private PlayerModel players;
    private CountryModel countries;
    private boolean canEnd;



    public GameStateModel(int TurnID) {
        this.turnID = 1;
        this.gameOver = false;
    }

    public void attachlistener(){
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null) {
                System.out.println(documentSnapshot.getData().get("gamestateTurnID"));
                System.out.println(State.TurnID);
                int firebaseTurnID = Integer.valueOf(documentSnapshot.getData().get("gamestateTurnID").toString());
                if (firebaseTurnID == State.TurnID){
                    System.out.println("Jij bent aan de beurt " + firebaseTurnID);
                    canEnd = true;
                    //TODO hier komt de zetten en aanvallen van de game. Als laatst nextTurn()


                    //ToDo zorg ervoor dat hier een mouse event listeren

                    //functie garrison(current playerID)


                } else {
                    System.out.println("Je bent niet aan de beurt, TurnID " + firebaseTurnID + " is aan de beurt");
                    canEnd = false;
                }
            }
        });
    }

    public GameStateModel() {
        attachlistener();
    }

    //if the player turnID matches the gamestate turnID. then he can start his turn
    public void getPlayersFirebaseTurnID(String lobbycode) throws ExecutionException, InterruptedException {

        //get benodigde stuff van firestore
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
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


    public void nextTurnIDFirebase(String lobbycode) throws ExecutionException, InterruptedException {

        if (canEnd) {
            int toUpdate;
            //get benodigde stuff van firestore
            DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");

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

    public long comparePlayerIDtoTurnID(String lobbycode, String playerLocalID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (playerLocalID.equals(document.get("gamestateTurnID").toString())){
            System.out.println("this is your turn!");
        } else{
            System.out.println("nah fam, not your turn");
        }

        return (long) document.getData().get("gamestateTurnID");
    }


    //TODO matchen met code hierboven
    public void nextTurn() {
        if (gameOver == true) {
            //end game. this should be called by an observer?
        } else if (turnID < 4) {
            this.turnID = turnID + 1;
            // roept de volgende turn aan
            //nextTurnIDFirebase(lobbycode);
            map.turnInProgress(map.getPlayers(), new GameStateModel(this.getTurnID()));
        } else if (turnID == 4) {
            this.turnID = 1;
            // roept de volgende turn aan
            //nextTurnIDFirebase(lobbycode);
            map.turnInProgress(map.getPlayers(), new GameStateModel(this.getTurnID()));
        }
    }

    public int getTurnID() {
        return this.turnID;
    }


}