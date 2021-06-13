package views;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import controllers.LoginController;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LobbyView {
    LoginController loginController = new LoginController();

    public LobbyView() {


    }





    @FXML
    public void displayLobbyCode() {
    }



  //TODO is nog niet af
    public ArrayList<String> getFirebaseUsernames(String lobbyCode) throws ExecutionException, InterruptedException {
        //get benodigde stuff van firestore
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbyCode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        ArrayList<String> mijnUsernamesList = new ArrayList<>();
        if (document.exists()) {

            ArrayList<HashMap> arrayPlayerData = (ArrayList<HashMap>) document.get("players"); //zet alle data van 'players' in array wat hashmaps bevatten

            for (HashMap playerData : arrayPlayerData) {
                System.out.println("playerdata player"+ playerData);  //loopt door de arrays van firestore zodat je ze apart kan zien van elke player
                mijnUsernamesList.add((String) playerData.get("username"));
                System.out.println(mijnUsernamesList);


            }
        } else {
            System.out.println("niks");
        }


        return mijnUsernamesList;
    }



}


//
//   Stage lobbyStage;
//
//
//    public LobbyView(Stage lobbyStage){
//        this.lobbyStage = lobbyStage;
//
//    }
//
//   public void loadLobbyStage() throws IOException {
//
//       Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
//       lobbyStage.setScene(new Scene(root,1280, 720));
//       lobbyStage.setResizable(false);
//       lobbyStage.show();
//   }

