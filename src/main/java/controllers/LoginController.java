package controllers;

import application.Main;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import models.GameModel;
import models.LobbyModel;
import models.PlayerModel;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class LoginController {

    public String createLobbyCode() {
        int min = 100000;
        int max = 999999;

        int lobbycode = (int) Math.floor(Math.random() * (max - min + 1) + min);


        return Integer.toString(lobbycode);
    }

    public void createLobby(String username, String lobbycode) throws ExecutionException, InterruptedException {
        PlayerModel playerModel1 = new PlayerModel(username, 1, 0.0);
        GameModel.TurnID = 1;

        DocumentReference docRef = Main.database.getFirestoreDatabase().collection(lobbycode).document("players");

        Map<String, Object> data = new HashMap<>();
        data.put("players", Collections.singletonList(playerModel1));
        data.put("gameIsRunning", false);
        data.put("gamestateTurnID", 1);
        data.put("actionsTaken", 0);

        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public void checkCreate(String username) {
        try {

            String lobbycode = createLobbyCode();
            createLobby(username, lobbycode);
            LobbyModel.lobbycode = lobbycode;


        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean readLobby(String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = Main.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            List<String> arrayValue = (List<String>) document.get("players");

            assert arrayValue != null;
            return  arrayValue.size() <4;

        } else {
            return false;
        }
    }

    public PlayerModel generateInstance(String username, String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = Main.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        List<String> arrayValue = (List<String>) document.get("players");
        assert arrayValue != null;
        PlayerModel playerModel2 = new PlayerModel(username, arrayValue.size() + 1);
        playerModel2.setPlayerColor(assignColorToPlayer(arrayValue.size() + 1));

        playerModel2.setTurnID(arrayValue.size() + 1);
        GameModel.TurnID = arrayValue.size() + 1;


        return playerModel2;
    }

    private double assignColorToPlayer(int i) {
        switch (i) {
            case 1:
                return GameModel.BLUE;
            case 2:
                return GameModel.GREEN;
            case 3:
                return GameModel.ORANGE;
        }
        return 0;
    }


    public void joinLobby(String lobbycode, String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = Main.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();



        LobbyModel.lobbycode = lobbycode;
        PlayerModel playerModel2 = generateInstance(username, lobbycode);

        ApiFuture<WriteResult> arrayUnion = docRef.update("players", FieldValue.arrayUnion(playerModel2));
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
        DocumentReference docRef = Main.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<WriteResult> future = docRef.update("gameIsRunning", true);
    }

    public boolean enoughPlayers() throws ExecutionException, InterruptedException {
        DocumentReference docRef = Main.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        List<String> arrayValue = (List<String>) document.get("players");


        assert arrayValue != null;
        return arrayValue.size() >= 1;

    }


}
