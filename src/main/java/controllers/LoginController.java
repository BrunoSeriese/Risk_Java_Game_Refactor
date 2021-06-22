package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import models.PlayerModel;
import models.SpelbordModel;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class LoginController {

    static SpelbordModel spelbordModel;


    public void testMessage(String username) {
        System.out.println("de username is: " + username);
    }

    public String createLobbyCode() {
        int min = 100000;
        int max = 999999;

        int lobbycode = (int) Math.floor(Math.random() * (max - min + 1) + min);


        return Integer.toString(lobbycode);
    }

    public void createLobby(String username, String lobbycode) throws ExecutionException, InterruptedException {
        PlayerModel playerModel1 = new PlayerModel(username, 1, 0.0);
        State.TurnID = 1;

        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");

        Map<String, Object> data = new HashMap<>();
        data.put("players", Collections.singletonList(playerModel1));
        data.put("gameIsRunning", false);
        data.put("gamestateTurnID", 1);
        data.put("actionsTaken", 0);

        ApiFuture<WriteResult> result = docRef.set(data);

        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public void checkCreate(String username) {
        try {

            String lobbycode = createLobbyCode();
            createLobby(username, lobbycode);
            State.lobbycode = lobbycode;


        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean readLobby(String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            List<String> arrayValue = (List<String>) document.get("players");

            return  arrayValue.size() <4;

        } else {
            return false;
        }
    }

    public PlayerModel generateInstance(String username, String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        List<String> arrayValue = (List<String>) document.get("players");
        PlayerModel playerModel2 = new PlayerModel(username, arrayValue.size() + 1);
        playerModel2.setPlayerColor(assignColorToPlayer(arrayValue.size() + 1));

        playerModel2.setTurnID(arrayValue.size() + 1);
        State.TurnID = arrayValue.size() + 1;


        return playerModel2;
    }

    private double assignColorToPlayer(int i) {
        switch (i) {
            case 1:
                return State.BLUE;
            case 2:
                return State.GREEN;
            case 3:
                return State.ORANGE;
        }
        return 0;
    }


    public void joinLobby(String lobbycode, String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        System.out.println(document.get("players"));


        State.lobbycode = lobbycode;
        PlayerModel playerModel2 = generateInstance(username, lobbycode);

        ApiFuture<WriteResult> arrayUnion = docRef.update("players", FieldValue.arrayUnion(playerModel2));
        System.out.println("Update time : " + arrayUnion.get().getUpdateTime());
    }

    public boolean checkJoin(String username, String code) {
        if (username.equals("")) {
            return false;
        } else {
            try {
                if (readLobby(code)) {
                    joinLobby(code, username);
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                return false;
            }
        }
        return false;
    }

    public boolean emptyUsername(String textfield) {
        return textfield.equals("");
    }

    public boolean validateLobby(String code) {
        if (code.equals("")) {
            return false;
        } else {
            code = code.toLowerCase();
            char[] charArray = code.toCharArray();
            for (char ch : charArray) {
                if (ch >= 'a' && ch <= 'z') {
                    return false;
                }
            }
            return true;
        }
    }

    public void gameRunning() {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<WriteResult> future = docRef.update("gameIsRunning", true);
    }

    public boolean enoughPlayers() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        List<String> arrayValue = (List<String>) document.get("players");


        assert arrayValue != null;
        return arrayValue.size() >= 1;

    }


}
