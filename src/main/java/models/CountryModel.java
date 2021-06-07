package models;

public class CountryModel {

    private String playerColour;
    private int countryID;
    private int army;

    public CountryModel(String playerColour,int countryID, int army){
        this.playerColour = playerColour;
        this.countryID = countryID;
        this.army = army;

    }

    public int getArmy() {
        return army;
    }

    public void setArmy(int army){
        this.army = army;
    }
}
