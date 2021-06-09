package models;

public class CountryModel {

    private int playerID;
    private String countryID;
    private int army;
    private String countryName;

    public CountryModel(String countryID,String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
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
}
