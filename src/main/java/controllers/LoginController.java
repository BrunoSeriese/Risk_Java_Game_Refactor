package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firestore.v1.Document;
import javafx.event.ActionEvent;
import models.PlayerModel;

import javax.xml.crypto.Data;
import java.util.*;
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
        PlayerModel playerModel1 = new PlayerModel(username, 0);

        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        // Add document data  with id "alovelace" using a hashmap

        Map<String, Object> playerData = new HashMap<>();
        playerData.put("TurnID", 1);
        playerData.put("TurnArmies", 3);
        playerData.put("username", username);


        Map<String, Object> data = new HashMap<>();
        data.put("players", Arrays.asList(playerModel1));
        data.put("gameIsRunning", false);

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
                System.out.println("De lobby is vol");
                return false;
            }
        } else {
            System.out.println("Lobby not found");
            return false;
        }
    }


    public void joinLobby(String lobbycode, String username) throws ExecutionException, InterruptedException {

        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // future.get() blocks on response
        DocumentSnapshot document = future.get();
        System.out.println(document.get("players"));

        //TO DO aanmaken van van playerTurnID door eerst te kijken hoe groot de array is en dan +1. Andere opties zijn mogelijk
//        String playerTurnID = document.getString("players");

        Map<String, Object> playerData = new HashMap<>();
//        playerData.put("TurnID", playerTurnID.length() + 1);
        playerData.put("isHost", false);
        playerData.put("username", username);

        ApiFuture<WriteResult> arrayUnion = docRef.update("players", FieldValue.arrayUnion(playerData));

//        Map<String, Object> data = new HashMap<>();
//        data.put("players", arrayUnion);

        //asynchronously write data
//        ApiFuture<WriteResult> result = docRef.update(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + arrayUnion.get().getUpdateTime());
    }

    public boolean checkJoin(String username, String code){
        if (username.equals("")){
            System.out.println("Username is leeg");
            return false;
        } else {
            try {
                if (readLobby(code)){
                    joinLobby(code, username);
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("Lobby not found");
                return false;
            }
        }
        return false;
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

    public boolean validateLobby(String code){
        if (code.equals("")){
            return false;
        } else {
            code = code.toLowerCase();
            char [] charArray = code.toCharArray();
            for (int i = 0; i < charArray.length; i++){
                char ch = charArray[i];
                if (ch >= 'a' && ch <= 'z'){
                    System.out.println("Ingevulde lobbycode bevat letters");
                    return false;
                }
            }
            return true;
        }
    }
}
