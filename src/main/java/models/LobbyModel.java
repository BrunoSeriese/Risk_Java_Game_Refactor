package models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import controllers.LoginController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LobbyModel {


    public static String lobbycode;

    public LobbyModel() {

    }


    public ArrayList<String> getFirebaseUsernames(String lobbyCode) throws ExecutionException, InterruptedException {

        DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(lobbyCode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        ArrayList<String> mijnUsernamesList = new ArrayList<>();
        if (document.exists()) {

            ArrayList<HashMap> arrayPlayerData = (ArrayList<HashMap>) document.get("players");

            assert arrayPlayerData != null;
            for (HashMap playerData : arrayPlayerData) {
                mijnUsernamesList.add((String) playerData.get("username"));


            }
        }


        return mijnUsernamesList;
    }

}
