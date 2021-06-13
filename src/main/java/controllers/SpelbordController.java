package controllers;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import models.GameStateModel;
import models.SpelbordModel;
import views.SpelbordView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SpelbordController {

    public Button buttonNA1;
    @FXML
    ImageView endTurnIcon;
    @FXML
    ImageView cardIcon;
    @FXML
    ImageView diceIcon;
    @FXML
    ImageView playerIcon;

    static SpelbordModel spelbordModel;
    GameStateModel gameStateModel;

    SpelbordView spelbordView = new SpelbordView();
    LoginController loginController = new LoginController();

    //Mogelijkheid dat dit weg kan
    //checkt of er al een instantie is, anders maakt hij er een
    public static SpelbordModel getSpelbordModelInstance() {
        if (spelbordModel == null) {
            spelbordModel = new SpelbordModel();
            System.out.println("nieuwe instantie Spelbordmodel is aangemaakt");
        }
        return spelbordModel;
    }

    public SpelbordController() {
        spelbordModel = getSpelbordModelInstance();
    }

    public void handleClicky() {
        System.out.println("CLICKYYY MOFO");
    }

    public void showCards() {
        System.out.println("showcard");
    }

    public void showPlayers() {
        System.out.println("showplayer");
    }

    public void rollDice() {
        System.out.println("rolldice");
    }


    public void setArmyAndCountryInFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.get("countries") != null) {
            System.out.println("this shit is already made");
        } else {
            spelbordModel.CountriesAndIdMap();
            Map<String, Object> data = new HashMap<>();
            data.put("countries", spelbordModel.getCountries());

//            CountryModel countryModel = new CountryModel("NA1");
            ApiFuture<WriteResult> result = docRef.update(data);
        }
    }

    public void getArmyAndCountryFromFirebase() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            for (HashMap armyAndCountryID : arrayCountryData) {
                System.out.println(armyAndCountryID);
            }
        } else {
            System.out.println("No document found!");
        }
    }


    //TODO NIET AAN DEZE 4 METHODS KOMEN

//    public void setArmyFirebase() throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        if (document.exists()) {
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata: " + arrayCountryData);
//
//            arrayCountryData.
//
//
//
//
//            HashMap newData = new HashMap();
//            newData.put("army", 4);
//
//            ArrayList<HashMap> testArray = new ArrayList<>();
//            testArray.add(newData);
////            long newValue = (long) oldValue.put("army", 4);
//
//
//            System.out.println("test new value: " + newValue);
//            docRef.update("countries", newValue);
//        }
//        else {
//            System.out.println("No document found!");
//        }
//    }

//    public void setArmyFirebase(int arrayNumber, int newArmies) throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        if (document.exists()) {
//            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
//            System.out.println("dit is arraycountrydata: " + arrayCountryData);
//
////            ArrayList<HashMap> data = new ArrayList<HashMap>();
////            data.add(0, ;
//
//            Map<String, Integer> dataMap = new HashMap<>();
//            dataMap.put("army", newArmies);
////
////            ArrayList<String> countries = new ArrayList<>();
////            countries.add(arrayNumber, "test");
//
//
//            docRef.update("countries", dataMap);
//        }
//        else {
//            System.out.println("No document found!");
//        }
//    }

    public void getArmyFirebase(int arrayNumber) throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            System.out.println("dit is arraycountrydata:    "+arrayCountryData);
            System.out.println(arrayCountryData.get(arrayNumber).get("army"));

        } else {
            System.out.println("No document found!");
        }
    }

    public void setPlayerIDtoCountry() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            System.out.println("dit is arraycountrydata:    "+arrayCountryData);
            for (HashMap armyAndCountryID : arrayCountryData) {
                System.out.println(armyAndCountryID);

            }
        } else {
            System.out.println("No document found!");
        }
    }

    public void getPlayerIDtoCountry() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection("791967").document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {

            ArrayList<HashMap> arrayCountryData = (ArrayList<HashMap>) document.get("countries");
            System.out.println("dit is arraycountrydata:    "+arrayCountryData);
            for (HashMap armyAndCountryID : arrayCountryData) {
                System.out.println(armyAndCountryID);

            }
        } else {
            System.out.println("No document found!");
        }
    }
    //TODO NIET AANKOMEN IS MINE ^^^^^^^^


    public void getButtonID(ActionEvent event) {
        System.out.println("clickedAAAAAAAAAA!!!");
        Button buttonid = (Button) event.getSource();
        System.out.println(buttonid);
    }

    public void endTurn() throws ExecutionException, InterruptedException {
        gameStateModel = loginController.getGameStateModelInstance();
        gameStateModel.nextTurnIDFirebase(State.lobbycode);

    }

    public void hideHUD() {
        cardIcon.setVisible(false);
        diceIcon.setVisible(false);
        playerIcon.setVisible(false);
    }

    public void showHUD() {
        cardIcon.setVisible(true);
        diceIcon.setVisible(true);
        playerIcon.setVisible(true);
    }

    public void HUD() throws ExecutionException, InterruptedException {
        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (State.TurnID == Integer.parseInt(document.get("gamestateTurnID").toString())) {
            showHUD();
        } else {
            hideHUD();
        }
    }


    //Todo zorg ervoor dat via de map de 2 countryID's worden meegegeven
    public void attackPlayer(String countryCodeAttacker, String countryCodeDefender) {

    }




























//    private List<PlayerModel> spelers = new ArrayList<>();
//
////
////    public void onClick() {
////
////        ArrayList<Integer> worp1 = new DiceController().roll(3);
////        ArrayList<Integer> worp2 = new DiceController().roll(3);
////
////        PlayerModel speler1 = new PlayerModel("Petra", worp1); ///TODO: ipv speler 1 aanmaken moet de speler die aanvalt uit de database worden gehaald en ipv speler 2 de verdediger
////        PlayerModel speler2 = new PlayerModel("Erik", worp2);  /// Playermodel speler1= Speler uit database
////
////
////        spelers.add(speler1);
////        spelers.add(speler2);
////
////    }
////    public void aanval() {
//////
//////        System.out.println("worp speler 1: "+ spelers.get(0).getLastThrow());
//////        System.out.println("worp speler 2: "+ spelers.get(1).getLastThrow());
//////
////        // (1) spelers gooien dobbelstenen
//////        int attackThrow1 = spelers.get(0).getLastThrow().get(0);
//////        int defendThrow1 = spelers.get(1).getLastThrow().get(0);
//////        int attackThrow2= spelers.get(0).getLastThrow().get(1);
//////        int defendThrow2 = spelers.get(1).getLastThrow().get(1);
////
////
////        // (2) er wordt een winnaar bepaald
////        PlayerModel winnaar = null;
////        if (attackThrow1 > defendThrow1 && attackThrow2 > defendThrow2){
////            System.out.println("speler 1 wint");//hier iets van spelers.get(1).setSoldaten(soldaten-2)
////            winnaar = spelers.get();
////        } else if(defendThrow1 >= attackThrow1 && defendThrow2 >= attackThrow2 ) {
////            System.out.println("2 wint");
////            winnaar = spelers.get(1);
////
////        }else {
////            System.out.println("gelijkspel allebij een pion weg");
////            System.out.println("STOP");
////            return;
////        }
//
//             (3) er wordt een willekeurige kaart gekozen
//
//          Integer willekeurigeKaart = new Random().nextInt(3)+1;

////            (4) de winnaar krijgt de willekerugei kaart
////        winnaar.getCards().add(willekeurigeKaart);

//
//
//
//        // (?) voeg aan een speler een aantal soldaten toe
//        //             (?.1) bepalen hoeveel soldaten
//        //             (?.2) bepalen welke soldaten
//        //              (?.3) bepalen welke speler
//        //          x   (?.4) toevoegen aan speler's soldaten
//        //                 x      (?.4.1) lijst van soldaten van de speler ophalen
//        //                 x      (?.4.2) soldaten uit stap 2 toevoegen aan de lijst
//
////
//        // soldaten toevoegen
//
//   }
//    public void startGame(){
//        spelers.get(0).setAantalLegers(20);
//        spelers.get(0).setColor(Color.blue);
//
//    }
//public void removeCard (int cardNumber){
//    spelers.get(0).getCards().remove(new Integer(cardNumber));
//    spelers.get(0).getCards().remove(new Integer(cardNumber));
//    spelers.get(0).getCards().remove(new Integer(cardNumber));
//}
//
//    public void handInCard(){
//        int Paarden= Collections.frequency(spelers.get(0).getCards(), 2);
//        int Kannonen= Collections.frequency(spelers.get(0).getCards(), 1);
//        int Ridders= Collections.frequency(spelers.get(0).getCards(), 3);
//        if(Kannonen >= 3){ ///moet nog kaarten verwijderen
//            System.out.println("Kaarten ingeleverd:Kanon");
//            spelers.get(0).setAantalLegers(spelers.get(0).getAantalLegers()+8);
//            removeCard(1);
//        }
//        else if(Paarden>= 3){
//            System.out.println("Kaarten ingeleverd(Paard");
//            spelers.get(0).setAantalLegers(spelers.get(0).getAantalLegers()+10);
//            removeCard(2);
//
//
//        }else if (Ridders >= 3){
//            System.out.println("Kaarten ingeleverd");
//            spelers.get(0).setAantalLegers(spelers.get(0).getAantalLegers()+14);
//            removeCard(3);
//
//
//        }else{
//            System.out.println("Je hebt geen geldige combinatie van kaarten");
//        }
//    }










    //    IK WEET BTW NIET OF DIT IN DE CONTROLLER MOET OF IN DE MODEL!!!


    //maak aantal spelers gelijk aan hoeveel mensen in lobby, dus 4 nieuwe spelers.
    // de usernames kan je met .getUserName fzo pakken, kijk ff in de rest van de classes van jansen.

    // de code kiest een random kleur en assigned die tot de speler OFFFFFF we kunnen 4 standaard kleuren kiezen bijvoorbeeld, rood blauw groen oranje.


    //als het spel is gestart heeft elke player een kleur en speler attributes, zoals regions cards etc...
    //ik maak nog een knopje met END TURN, dan als je erop klikt dat de beurt dan overgaat naar de volgende,


    // deze knop staat in de map Images en die mag alleen visible worden als degene heeft aangevallen/reinforced. maar dat komt later wel


    //if player has all regions, set hasWon = true, stop de turn loop >>> go to results screen


    //als een speler die 3 cards heeft dan schakelt de canExchageCards naar = true en dan verschijnt er een knopje die we nog moeten maken en die hele interface nog met, 'Trade in' bijvoorbeeld
    //dan verschijnt er een scherm met het aantal troepen dat diegene krijgt en dan klikt ie op "OK" en dan ontvangt hij de troepen en gaan die kaarten weg.


}
