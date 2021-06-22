package models;

import java.util.ArrayList;
import java.util.Map;

public class PlayerModel {

    private int turnArmies;
    private String username;
    private int turnID;
    private boolean hasTurn;
    private ArrayList<CountryModel> countries;
    private boolean canInterract;
    static PlayerModel playerModel;
    private double playerColor;
    private ArrayList<Integer> playerCards;

    public static PlayerModel getPlayerModelInstance() {
        if (playerModel == null) {
            playerModel = new PlayerModel();
            System.out.println("nieuwe instantie van PlayerModel is aangemaakt");
        }
        return playerModel;
    }







    public PlayerModel() {

    }

    public PlayerModel(String username, int turnID) {
        this.username = username;
        this.turnID = turnID;
        this.turnArmies = 3;
        this.hasTurn = false;
        this.countries = null;
    }

    public PlayerModel(String username, int turnID, double playerColor) {
        this.username = username;
        this.turnID = turnID;
        this.turnArmies = 3;
        this.hasTurn = false;
        this.countries = null;
        this.playerColor = playerColor;
    }


    public PlayerModel(String username, ArrayList<CountryModel> countries, ArrayList cards, Integer aantalLegers) {

    }


    public void garrison(int playerID) {


        if (this.turnArmies != 0) {

        } else {
            this.attack();
        }
    }

    public void attack() {
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

    public double getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(double playerColor) {
        this.playerColor = playerColor;
    }

    public void buildPhase() {
    }

    public void attackPhase() {
    }

    public void endPhase() {
    }


    public void setTurnID(int turnID) {
        this.turnID = turnID;
    }
}
