package models;

import java.util.ArrayList;

public class PlayerModel {

    private int turnArmies;
    private String username;
    private int turnID;
    private boolean hasTurn;
    private ArrayList<CountryModel> countries;
    private boolean canInterract;

//    private ArrayList<Integer> lastThrow;

//    private ArrayList cards = new ArrayList();


//    private Color color; //random #,#,# digits for color code (ik weet niet of gewoon 'red' of 'blue' werkt, maar vgm moet het color codes, dus rgb(255,255,255) bijvoorbeeld
//    private Boolean hasWon; //set true if conquered all regions
//    private Boolean canExchangeCards; // if true, verschijnt button met trade in cards fzo, komt later


//    private int aantalLegers;

//    private int startLegers;


    public PlayerModel(){

    }
    public PlayerModel(String username,  int turnID){
        this.username = username;
        this.turnID = turnID;
        this.turnArmies = 3;
        this.hasTurn = false;
        this.countries = null;
    }

    public PlayerModel(String username,  ArrayList<CountryModel> countries, ArrayList cards, Integer aantalLegers){
//        this.username = username;
//        this.countries = countries;
//        // is last throw echt nodig? last throw lijkt heel onnodig
//        this.lastThrow = lastThrow;
//        this.cards = cards;
//        this.aantalLegers=aantalLegers;
//        this.color=color;
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

    public int getTurnArmies() {
        return turnArmies;
    }

    public void setTurnArmies(int turnArmies) {
        this.turnArmies = turnArmies;
    }

    public boolean isHasTurn() {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public ArrayList<CountryModel> getCountries() {
        return countries;
    }

    //
//    public ArrayList<Integer> getLastThrow() {
//        return lastThrow;
//    }
//
//    public void setLastThrow(ArrayList<Integer> lastThrow) {
//        this.lastThrow = lastThrow;
//    }

//    public int getAantalLegers() {
//        return aantalLegers;
//    }
//
//    public void setAantalLegers(int aantalLegers) {
//        this.aantalLegers = aantalLegers;
//    }

//    public Color getColor() {
//        return color;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
        //Todo: om het te laten werken moet elke phase wachten op events van de kaart (klik events)
        //Todo: klik events MOETEN TURNID ALTIJD VERGELIJKEN MET turnID van speler!
        // door deze regeling kan de speler altijd
    // Todo hoe zorgen ervoor dat andere spelers in deze phase wel gewoon hun kaarten kunnen bekijken?
    public void buildPhase(){}
    public void attackPhase(){}
    public void endPhase(){}

//    public ArrayList<Integer> getCards() {
//        return cards;
//    }

    public void setTurnID(int turnID) {
        this.turnID = turnID;
    }
}
