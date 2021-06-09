package models;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import controllers.LoginController;

import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;

public class GameStateModel {

    // spelbord model moet niet map zijn maar bijv private SpelbordModel map = firebase.get(currentMap)
    LoginController loginController = new LoginController();
    private SpelbordModel map;
    private int turnID;
    private boolean gameOver;
    private PlayerModel players;
    private CountryModel countries;



    public GameStateModel(int TurnID){
        this.turnID = 1;
        this.gameOver = false;

    }

    public GameStateModel() {

    }

    //if the player turnID matches the gamestate turnID. then he can start his turn
    public void checkFirebaseTurn(String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");

        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        if(document.exists()){
            System.out.println("Document data: " + document.getData().values());
        }
        else{
            System.out.println("nothing");
        }
    }

    public void nextTurn(){
        if (gameOver == true){
            //end game. this should be called by an observer?
        }else if(turnID < 4){
            this.turnID = turnID +1;
            // roept de volgende turn aan
            map.turnInProgress(map.getPlayers(), new GameStateModel(this.getTurnID()));
        }else if (turnID == 4){
            this.turnID = 1;
            // roept de volgende turn aan
            map.turnInProgress(map.getPlayers(), new GameStateModel(this.getTurnID()));
        }
    }

    public int getTurnID(){
        return this.turnID;
    }


}