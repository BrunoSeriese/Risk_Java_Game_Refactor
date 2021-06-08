package models;

import java.awt.*;
import java.util.ArrayList;

public class PlayerModel {
    private String username;
    private Boolean turn;
    private int playerID;
    private ArrayList<CountryModel> countries;
    private ArrayList lastThrow;
    private ArrayList cards;
    private Color color; //random #,#,# digits for color code (ik weet niet of gewoon 'red' of 'blue' werkt, maar vgm moet het color codes, dus rgb(255,255,255) bijvoorbeeld
    private Boolean hasWon; //set true if conquered all regions
    private Boolean canExchangeCards; // if true, verschijnt button met trade in cards fzo, komt later


    public PlayerModel(){

    }
    public PlayerModel(String username, int playerID){
        this.username = username;
        this.playerID = playerID;

    }
    public PlayerModel(String username, Boolean turn, ArrayList<CountryModel> countries, ArrayList lastThrow, ArrayList cards){
        this.username = username;

        // ik zou dit niet in het playermodel zetten maar in de gamestate
        this.turn = turn;

        this.countries = countries;

        // is last throw echt nodig? last throw lijkt heel onnodig
        this.lastThrow = lastThrow;
        this.cards = cards;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }



    public ArrayList getLastThrow() {
        return lastThrow;
    }

    public void setLastThrow(ArrayList lastThrow) {
        this.lastThrow = lastThrow;
    }
}
