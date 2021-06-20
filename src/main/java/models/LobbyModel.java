package models;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LobbyModel {


    public LobbyModel() {

    }


    public ArrayList<String> getFirebaseUsernames(String lobbyCode) throws ExecutionException, InterruptedException {
        //get benodigde stuff van firestore
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbyCode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        ArrayList<String> mijnUsernamesList = new ArrayList<>();
        if (document.exists()) {

            ArrayList<HashMap> arrayPlayerData = (ArrayList<HashMap>) document.get("players"); //zet alle data van 'players' in array wat hashmaps bevatten

            for (HashMap playerData : arrayPlayerData) {
                System.out.println("playerdata player" + playerData);  //loopt door de arrays van firestore zodat je ze apart kan zien van elke player
                mijnUsernamesList.add((String) playerData.get("username"));
                System.out.println(mijnUsernamesList);


            }
        } else {
            System.out.println("niks");
        }


        return mijnUsernamesList;
    }
//
//
//


}
