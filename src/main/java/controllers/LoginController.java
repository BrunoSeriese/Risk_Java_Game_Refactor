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
        PlayerModel playerModel1 = new PlayerModel(username, 1);
        System.out.println("playermodel instantie is gemaakt");


        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");

//        Map<String, Object> playerData = new HashMap<>();
//        playerData.put("TurnID", playerModel1.getTurnID());
//        playerData.put("TurnArmies", playerModel1.getTurnArmies());
//        playerData.put("username", playerModel1.getUsername());
//        playerData.put("hasTurn", playerModel1.getHasTurn());
//        playerData.put("countries", playerModel1.getCountries());


        Map<String, Object> data = new HashMap<>();
        data.put("players", Arrays.asList(playerModel1));
        data.put("gameIsRunning", false);

        ApiFuture<WriteResult> result = docRef.set(data);

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

        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        if (document.exists()) {
            System.out.println("Document data: " + document.getData());
            List<String> arrayValue = (List<String>)document.get("players");
            System.out.println(arrayValue.size());

            if (arrayValue.size() < 4){
                System.out.println("er zitten in array: " + arrayValue.size());
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

    public PlayerModel generateInstance(String username, String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");

        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        List<String> arrayValue = (List<String>)document.get("players");
        PlayerModel playerModel2 = new PlayerModel(username, arrayValue.size() + 1);
        playerModel2.setTurnID(arrayValue.size() + 1);

        return playerModel2;
    }

    public void joinLobby(String lobbycode, String username) throws ExecutionException, InterruptedException {

        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // future.get() blocks on response
        DocumentSnapshot document = future.get();
        System.out.println(document.get("players"));

        Map<String, Object> playerData = new HashMap<>();
        playerData.put("isHost", false);
        playerData.put("username", username);

        PlayerModel playerModel2 = generateInstance(username, lobbycode);

        ApiFuture<WriteResult> arrayUnion = docRef.update("players", FieldValue.arrayUnion(playerModel2));
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
