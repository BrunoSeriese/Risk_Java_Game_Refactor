package models;

import java.util.ArrayList;

public class CountryModel {

    private int playerID;
    private String countryID;
    private int army;
    private ArrayList<String> neighbor;

    public CountryModel(String countryID, ArrayList<String> neighborData){
        this.countryID = countryID;
        this.army = 2;
        this.playerID = 1;
        this.neighbor = neighborData;
    }

    public void getAllCountryID(){

    }

    public CountryModel(int ID,String countryID, int army){
        this.playerID = ID;
        this.countryID = countryID;
        this.army = army;
    }

    public int getArmy() {
        return army;
    }

    public void setArmy(int army){
        this.army = army;
    }
    public String getCountryID(){
        return this.countryID;
    }

    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public ArrayList<String> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(ArrayList<String> neighbor) {
        this.neighbor = neighbor;
    }
}
