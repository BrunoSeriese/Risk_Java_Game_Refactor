package controllers;
import com.google.common.collect.Lists;
import io.grpc.internal.JsonUtil;
import models.PlayerModel;

import java.util.*;

public class SpelbordController {
    private List<PlayerModel> spelers = new ArrayList<>();

    public void onClick() {

        ArrayList<Integer> worp1 = new DiceController().roll(3);
        ArrayList<Integer> worp2 = new DiceController().roll(3);

        PlayerModel speler1 = new PlayerModel("Petra", worp1);
        PlayerModel speler2 = new PlayerModel("Erik", worp2);

        spelers.add(speler1);
        spelers.add(speler2);

    }
    public void aanval() {

        System.out.println("worp speler 1: "+ spelers.get(0).getLastThrow());
        System.out.println("worp speler 2: "+ spelers.get(1).getLastThrow());

        int attackThrow1 = spelers.get(0).getLastThrow().get(0);
        int defendThrow1 = spelers.get(1).getLastThrow().get(0);
        int attackThrow2= spelers.get(0).getLastThrow().get(1);
        int defendThrow2 = spelers.get(1).getLastThrow().get(1);



        if (attackThrow1 > defendThrow1 && attackThrow2 > defendThrow2){
            System.out.println("speler 1 wint");//hier iets van spelers.get(1).setSoldaten(soldaten-2)


        } else if(defendThrow1 >= attackThrow1 && defendThrow2 >= attackThrow2 ) {
            System.out.println("2 wint");

        }else {
            System.out.println("gelijkspel allebij een pion weg");
        }


   }

    public void showPlayers() {
    } //method voor de buttons in de UI/Interface - show players is een simpele popup met de namen van de players en kleur

    public void rollDice() {
    } //method voor de buttons in de UI/Interface - roll dice daar moet je met martin ff overleggen

    public void showCards() {
    } //method voor de buttons in de UI/Interface - show cards wordt een kleine nieuwe interface waar chiel en ryan aan gaan werken



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

