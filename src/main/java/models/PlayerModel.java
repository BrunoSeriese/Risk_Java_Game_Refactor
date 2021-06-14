package models;

import java.util.ArrayList;

public class PlayerModel {

    private int turnArmies;
    private String username;
    private int turnID;
    private boolean hasTurn;
    private ArrayList<CountryModel> countries;
    private boolean canInterract;
    static PlayerModel playerModel;


    public static PlayerModel getPlayerModelInstance() {
        if (playerModel == null) {
            playerModel = new PlayerModel();
            System.out.println("nieuwe instantie van PlayerModel is aangemaakt");
        }
        return playerModel;
    }


//    private ArrayList<Integer> lastThrow;
//    private ArrayList cards = new ArrayList();

//    private Color color; //random #,#,# digits for color code (ik weet niet of gewoon 'red' of 'blue' werkt, maar vgm moet het color codes, dus rgb(255,255,255) bijvoorbeeld
//    private Boolean hasWon; //set true if conquered all regions
//    private Boolean canExchangeCards; // if true, verschijnt button met trade in cards fzo, komt later


//    private int aantalLegers;

//    private int startLegers;


    public PlayerModel() {

    }

    public PlayerModel(String username, int turnID) {
        this.username = username;
        this.turnID = turnID;
        this.turnArmies = 3;
        this.hasTurn = false;
        this.countries = null;
    }

    public PlayerModel(String username, ArrayList<CountryModel> countries, ArrayList cards, Integer aantalLegers) {
//        this.username = username;
//        this.countries = countries;
//        // is last throw echt nodig? last throw lijkt heel onnodig
//        this.lastThrow = lastThrow;
//        this.cards = cards;
//        this.aantalLegers=aantalLegers;
//        this.color=color;
    }


    public void garrison(int playerID) {
        //this.armies = *haal aantal landen uit firebase dat de player bezit en deel door 3/ als kleiner dan 3 dan is het 3. (mag niet lager zijn)


        //Todo hier moet een mouselistener zijn die het land opvangt waar je op klikt en kijkt of het overeenkomt met jouw id
        // zo ja dan is elke klik 1 leger erbij op het land
        //elke keer this.turnArmies -1 en bij aanvang controleren of turnArmies niet 0 is

        if (this.turnArmies != 0) {

        } else if (this.turnArmies == 0) {
            this.attack();
        }
    }

    public void attack() {
        //Todo hier moet een mouselistener zijn die het land opvangt waar je op klikt en kijkt of het overeenkomt met jouw ID

//       if (clickedCountry.playerid == player.turnID){
//          if (secondClickedCountry.playerID != player.turnID){
//              System.out.println("je kan dit land aanvallen");
//            }else{ System.out.println("je kan niet jezelf aanvallen");}
//          } else { System.out.println("je kan niet aanvallen vanuit een land van een vijand!");
//       }
    }

    public void setCountries(ArrayList<CountryModel> countries) {
    }

    public void setHasTurn() {
        this.hasTurn = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTurnID() {
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

    public void removeCountry(int lostCountry) {
        // give as parameter the country that the player lost
        this.countries.remove(lostCountry);
    }

    public void gainCountry(CountryModel wonCountry) {
        this.countries.add(wonCountry);
    }

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
    public void buildPhase() {
    }

    public void attackPhase() {
    }

    public void endPhase() {
    }

//    public ArrayList<Integer> getCards() {
//        return cards;
//    }

    public void setTurnID(int turnID) {
        this.turnID = turnID;
    }
}
