package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountryModel {

    private int playerID;
    private String countryID;
    private int army;
    private String countryName;
//    private Map<String, String> CountriesAndId = new HashMap<>();



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
    public String getCountryID(){
        return this.countryID;
    }
    public String getCountryName(){
        return this.countryName;
    }



}
