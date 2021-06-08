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




        PlayerModel speler1 = new PlayerModel("Petra", worp1, Lists.newArrayList(worp1));
        PlayerModel speler2 = new PlayerModel("Erik", worp2, Lists.newArrayList(worp2));
//      SpelerModel speler3= new SpelerModel("johan",false,dice,dice,dice);
        spelers.add(speler1);
        spelers.add(speler2);


        ///// als het spel in staat X is, en speler heeft bepaalde resources, dan ....
        //////////////////// aanval()


    }
    public void aanval() {

        System.out.println("hoogste worp speler 1: "+ spelers.get(0).getLastThrow().get(0));
        System.out.println("Hoogste worp speler 2: "+ spelers.get(1).getLastThrow().get(0));

        int speler1hoogste = spelers.get(0).getLastThrow().get(0);
        int speler2hoogste = spelers.get(1).getLastThrow().get(0);

        if (speler1hoogste > speler2hoogste){
            System.out.println("speler 1 wint");

        } else if(speler2hoogste > speler1hoogste) {
            System.out.println("2 wint");

        }else {
            System.out.println("Gelijkspel");
        }


   }
}





//    public void showPlayers(){}
//    public void rollDice(){}
//    public void showCards(){}
//    public void surrenderGame(){}




        // start spel hier met oproepen van een functie

        // maak daarbij een Spelers ArrayList aan met al hun kaarten etc
        // bij het aanmaken roept die een functie aan die de spelers kaarten geeft en andere dingen fikst

        //Spelers players = new Spelers(args)

        // maak een turn while loop

        // laat bij winconditie de while loop aflopen


        // print winnaar en laat zien op scherm


