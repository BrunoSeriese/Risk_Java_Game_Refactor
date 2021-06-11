package controllers;
import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.common.collect.Lists;
import io.grpc.internal.JsonUtil;
import models.DiceModel;
import models.PlayerModel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpelbordController {
//    PlayerModel playerModel;
//    LoginController loginController = new LoginController();
//
//    public void showCards(){
//    }
//    public void showPlayers(){}
//
//
//    private List<PlayerModel> spelers = new ArrayList<>();
//
//    public ArrayList getDefender(String lobbycode) throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection(lobbycode).document("players");
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        return (ArrayList) document.getData().get("players");
//    }
//
//    public void rollDice() {
//        playerModel = loginController.getInstance();
//
//        ArrayList<Integer> worp1 = new DiceModel().roll(3);
//        ArrayList<Integer> worp2 = new DiceModel().roll(3);
//        playerModel.setLastThrow(worp1);
//
//        ///TODO: ipv speler 1 aanmaken moet de speler die aanvalt uit de database worden gehaald en ipv speler 2 de verdediger
//        /// Playermodel speler1= Speler uit database
//
//        System.out.println(playerModel);
//
////        spelers.add(speler1);
////        spelers.add(speler2);
//
//    }
//    public void aanval() {
////
//        System.out.println("worp speler 1: "+ spelers.get(0).getLastThrow());
//        System.out.println("worp speler 2: "+ spelers.get(1).getLastThrow());
//
//        //(1) spelers gooien dobbelstenen
//        int attackThrow1 = spelers.get(0).getLastThrow().get(0);
//        int defendThrow1 = spelers.get(1).getLastThrow().get(0);
//        int attackThrow2= spelers.get(0).getLastThrow().get(1);
//        int defendThrow2 = spelers.get(1).getLastThrow().get(1);
//
//
//        // (2) er wordt een winnaar bepaald
//        PlayerModel winnaar = null;
//        if (attackThrow1 > defendThrow1 && attackThrow2 > defendThrow2){
//            System.out.println("speler 1 wint");//hier iets van spelers.get(1).setSoldaten(soldaten-2)
//            winnaar = spelers.get(0);
//        } else if(defendThrow1 >= attackThrow1 && defendThrow2 >= attackThrow2 ) {
//            System.out.println("2 wint");
//            winnaar = spelers.get(1);
//
//        }else {
//            System.out.println("gelijkspel allebij een pion weg");
//            System.out.println("STOP");
//            return;
//        }
//
//        //(3) er wordt een willekeurige kaart gekozen
//
//        Integer willekeurigeKaart = new Random().nextInt(3)+1;
//
////            (4) de winnaar krijgt de willekerugei kaart
//        winnaar.getCards().add(willekeurigeKaart);
//
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
//    }
//    public void startGame(){
//        spelers.get(0).setAantalLegers(20);
//        spelers.get(0).setColor(Color.blue);
//
//    }
//    public void removeCard (int cardNumber){
//        spelers.get(0).getCards().remove(new Integer(cardNumber));
//        spelers.get(0).getCards().remove(new Integer(cardNumber));
//        spelers.get(0).getCards().remove(new Integer(cardNumber));
//    }
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
//
//    //    public void showPlayers() {
////    } //method voor de buttons in de UI/Interface - show players is een simpele popup met de namen van de players en kleur
////
////    public void rollDice() {
////    } //method voor de buttons in de UI/Interface - roll dice daar moet je met martin ff overleggen
////
////    public void showCards() {
////    } //method voor de buttons in de UI/Interface - show cards wordt een kleine nieuwe interface waar chiel en ryan aan gaan werken
////
//    public void cardToBase() throws ExecutionException, InterruptedException {
//        DocumentReference docRef = State.database.getFirestoreDatabase().collection(State.lobbycode).document("players");
//        ApiFuture<WriteResult> future = docRef.update("cards", spelers.get(0).getCards());
//
//        WriteResult result = future.get();
//        System.out.println("Write result: " + result);
//    }
//
//    //    IK WEET BTW NIET OF DIT IN DE CONTROLLER MOET OF IN DE MODEL!!!
//
//
//
//    //maak aantal spelers gelijk aan hoeveel mensen in lobby, dus 4 nieuwe spelers.
//    // de usernames kan je met .getUserName fzo pakken, kijk ff in de rest van de classes van jansen.
//
//    // de code kiest een random kleur en assigned die tot de speler OFFFFFF we kunnen 4 standaard kleuren kiezen bijvoorbeeld, rood blauw groen oranje.
//
//
//    //als het spel is gestart heeft elke player een kleur en speler attributes, zoals regions cards etc...
//    //ik maak nog een knopje met END TURN, dan als je erop klikt dat de beurt dan overgaat naar de volgende,
//
//
//    // deze knop staat in de map Images en die mag alleen visible worden als degene heeft aangevallen/reinforced. maar dat komt later wel
//
//
//    //if player has all regions, set hasWon = true, stop de turn loop >>> go to results screen
//
//
//    //als een speler die 3 cards heeft dan schakelt de canExchageCards naar = true en dan verschijnt er een knopje die we nog moeten maken en die hele interface nog met, 'Trade in' bijvoorbeeld
//    //dan verschijnt er een scherm met het aantal troepen dat diegene krijgt en dan klikt ie op "OK" en dan ontvangt hij de troepen en gaan die kaarten weg.
//
//
//
}
