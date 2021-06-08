package models;

import java.awt.*;
import java.util.ArrayList;

public class PlayerModel {
    private int turnArmies;
    private String username;
    private int turnID;
    private boolean hasTurn;
    private int playerID;
    private ArrayList<CountryModel> countries;
    private ArrayList<Integer> lastThrow;
    private ArrayList cards;
    private Color color; //random #,#,# digits for color code (ik weet niet of gewoon 'red' of 'blue' werkt, maar vgm moet het color codes, dus rgb(255,255,255) bijvoorbeeld
    private Boolean hasWon; //set true if conquered all regions
    private Boolean canExchangeCards; // if true, verschijnt button met trade in cards fzo, komt later


    public PlayerModel(){

    }
    public PlayerModel(String username, int playerID, int turnID){
        this.username = username;
        this.playerID = playerID;
        this.turnID = turnID;
        this.turnArmies = 0;
        this.hasTurn = false;

    }
    public PlayerModel(String username, ArrayList<Integer> lastThrow, ArrayList cards){
        this.username = username;



        //this.countries = countries;

        // is last throw echt nodig? last throw lijkt heel onnodig
        this.lastThrow = lastThrow;
        this.cards = cards;

    }

    public void startTurn(GameStateModel hostedGame){
        if (this.turnID == hostedGame.getTurnID()) {

            // let observer check this for all players, if yes then let them play

            // give player armies to use

//            this.turnArmies = ownedCountries / modifier

            //force player to put down his turnArmies until it reaches zero



            // let player attack from one country to another


            // if end turn is pressed. end turn and let observer update turnID for GAMESTATE, not player
        }
    }

    public void setCountries(ArrayList<CountryModel> countries){
        // give as input the countries arraylist from the initialized spelbordmodel

        // randomize it with an arrayList function

        // loop through an arrayList and divide the countries by 4, every time you get at a 1/4th point switch
        // to next playerID

        for(int i = 0;i<countries.size(); i++){
            //
            //if country [i] is at 1/4th, switch to next playerID
        }
    }

    public void setHasTurn(){
        this.hasTurn = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getTurnID(){
        return turnID;
    }


    public ArrayList<Integer> getLastThrow() {
        return lastThrow;
    }

    public void setLastThrow(ArrayList<Integer> lastThrow) {
        this.lastThrow = lastThrow;
    }


    public void buildPhase(){}
    public void attackPhase(){}
    public void endPhase(){}
}
