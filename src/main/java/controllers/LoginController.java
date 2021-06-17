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
    static LoginController loginController;

    public LoginController getLoginControllerInstance() {
        if (loginController == null) {
            loginController = new LoginController();
            System.out.println("nieuwe instantie van Logincontroller is aangemaakt");
        }
        return loginController;
    }

    public void testMessage(String username) {
        System.out.println("de username is: " + username);
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
        PlayerModel playerModel1 = new PlayerModel(username, 1, 0.0);
        State.TurnID = 1;
        System.out.println("playermodel instantie is gemaakt");
        System.out.println("jouw kleur  " + playerModel1.getPlayerColor());

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
        data.put("gamestateTurnID", 1);
        data.put("actionsTaken", 0);

        ApiFuture<WriteResult> result = docRef.set(data);

        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public void checkCreate(String username) {
        try {
            if (username.equals("")) {
                System.out.println("Username is leeg");
            } else {
                String lobbycode = createLobbyCode();
                createLobby(username, lobbycode);
                State.lobbycode = lobbycode;

                System.out.println("De state lobbycode is " + State.lobbycode);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("de username is: " + username);
    }


    public boolean readLobby(String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            System.out.println("Document data: " + document.getData());
            List<String> arrayValue = (List<String>) document.get("players");
            System.out.println(arrayValue.size());

            if (arrayValue.size() < 4) {
                System.out.println("er zitten in array: " + arrayValue.size());
                return true;
            } else {
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

        List<String> arrayValue = (List<String>) document.get("players");
        PlayerModel playerModel2 = new PlayerModel(username, arrayValue.size() + 1);
        playerModel2.setPlayerColor(assignColorToPlayer(arrayValue.size() + 1));
        System.out.println("dit is wahed color    " + playerModel2.getPlayerColor());
        playerModel2.setTurnID(arrayValue.size() + 1);
        State.TurnID = arrayValue.size() + 1;
        System.out.println("De stateturnid is: " + State.TurnID);

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

//        if (i == 2) {
//            return State.BLUE;
//        } else if (i == 3) {
//            return State.GREEN;
//        } else if (i == 4) {
//            return State.ORANGE;
//        }
//        return 0;



    public void joinLobby(String lobbycode, String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        System.out.println(document.get("players"));

//        Map<String, Object> playerData = new HashMap<>();
//        playerData.put("isHost", false);
//        playerData.put("username", username);

        State.lobbycode = lobbycode;
        PlayerModel playerModel2 = generateInstance(username, lobbycode);

        ApiFuture<WriteResult> arrayUnion = docRef.update("players", FieldValue.arrayUnion(playerModel2));
        System.out.println("Update time : " + arrayUnion.get().getUpdateTime());
    }

    public boolean checkJoin(String username, String code) {
        if (username.equals("")) {
            System.out.println("Username is leeg");
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
                System.out.println("Lobby not found");
                return false;
            }
        }
        return false;
    }

    public boolean emptyUsername(String textfield) {
        if (textfield.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateLobby(String code) {
        if (code.equals("")) {
            return false;
        } else {
            code = code.toLowerCase();
            char[] charArray = code.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char ch = charArray[i];
                if (ch >= 'a' && ch <= 'z') {
                    System.out.println("Ingevulde lobbycode bevat letters");
                    return false;
                }
            }
            return true;
        }
    }

    public void gameRunning() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<WriteResult> future = docRef.update("gameIsRunning", true);

        WriteResult result = future.get();
        System.out.println("Write result: " + result);
    }

    public boolean enoughPlayers() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        List<String> arrayValue = (List<String>) document.get("players");

        //TODO vergeet niet om de nummer terug naar 4 te zetten
        if (arrayValue.size() >= 1) {
            return true;
        } else {
            System.out.println("Er zijn niet genoeg mensen in de lobby");
            return false;
        }
    }

    public void allPlayers(String lobbycode) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        //TODO net als bij logincontrol.generateInstance een instantie maken voor spelbordModel met de arrayvalue van hier en countries.

        if (document.exists()) {
            ArrayList<PlayerModel> arrayValue = (ArrayList<PlayerModel>) document.get("players");
//            System.out.println(arrayValue);
            spelbordModel.setPlayers(arrayValue);
        }
    }


}
