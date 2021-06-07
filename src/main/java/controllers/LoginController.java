package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import javafx.event.ActionEvent;
import models.PlayerModel;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginController {

    static PlayerModel playerModel;

    public void testMessage(String username){
        System.out.println("de username is: " + username);
    }

    public static PlayerModel getInstance() {
        if (playerModel == null) {
            playerModel = new PlayerModel();
            System.out.println("nieuwe instantie is aangemaakt");
        }
        return playerModel;
    }

    public String createLobbyCode() {
        int min = 100000;
        int max = 999999;

        int lobbycode = (int) Math.floor(Math.random() * (max - min + 1) + min);
        System.out.println(lobbycode);

        String lobbyCodeString = Integer.toString(lobbycode);
        return lobbyCodeString;
    }

    public void createLobby(String username, String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        // Add document data  with id "alovelace" using a hashmap
        Map<String, String> playerData = new HashMap<>();
        playerData.put("type", "host");

        Map<String, Object> data = new HashMap<>();
        data.put(username, playerData);
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public void checkCreate(String username){
        try{
            if (username.equals("")) {
                System.out.println("Username is leeg");
            } else {
                String lobbycode = createLobbyCode();
                createLobby(username, lobbycode);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
        System.out.println("de username is: " + username);
    }

    public boolean readLobby(String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // future.get() blocks on response
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            System.out.println("Document data: " + document.getData());
            if (document.getData().size() < 4){
                System.out.println(document.getData().size());
                return true;
            } else{
                return false;
            }
        } else {
            System.out.println("No such document!");
            return false;
        }
    }

    public void joinLobby(String lobbycode, String username) throws ExecutionException, InterruptedException {

        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        // Add document data  with id "alovelace" using a hashmap
        Map<String, String> playerData = new HashMap<>();
        playerData.put("type", "player");

        Map<String, Object> data = new HashMap<>();
        data.put(username, playerData);
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.update(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public void checkJoin(String username, String code){
        if (username.equals("")){
            System.out.println("Username is leeg");
        } else {
            try {
                if (readLobby(code)){
                    joinLobby(code, username);
                } else {
                    System.out.println("lobby is vol");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("Lobby not found");
            }
            System.out.println("de lobbycode is " + code);
        }
    }

    public boolean emptyUsername(String textfield){
        if (textfield.equals("")){
            return true;
        } else {
            return false;
        }
    }

    public boolean emptyLobbycode(String code){
        if (code.equals("")){
            return true;
        } else {
            return false;
        }
    }
}
