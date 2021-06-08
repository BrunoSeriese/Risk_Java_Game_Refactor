package models;

public class CountryModel {

    private int playerID;
    private int countryID;
    private int army;

    public CountryModel(int ID,int countryID, int army){
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
}
