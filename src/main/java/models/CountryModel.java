package models;

import java.util.HashMap;
import java.util.Map;

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

    //TODO dictionary werkend krijgen en van kunnen getten dus de Keys en Values
//    public void CountriesAndIdMap() {
//        //EUROPE
//        CountriesAndId.put("Iceland", "EU1");
//        CountriesAndId.put("Scandinavia", "EU2");
//        CountriesAndId.put("Ukraine", "EU3");
//        CountriesAndId.put("Great-Britain", "EU4");
//        CountriesAndId.put("Northern-Europe", "EU5");
//        CountriesAndId.put("Western-Europe", "EU6");
//        CountriesAndId.put("Southern-Europe", "EU7");
//
//        //ASIA
//        CountriesAndId.put("Ural", "ASIA1");
//        CountriesAndId.put("Siberia", "ASIA2");
//        CountriesAndId.put("Yakutsk", "ASIA3");
//        CountriesAndId.put("Kamchatka", "ASIA4");
//        CountriesAndId.put("Irkutsk", "ASIA5");
//        CountriesAndId.put("Mongolia", "ASIA6");
//        CountriesAndId.put("Japan", "ASIA7");
//        CountriesAndId.put("Afghanistan", "ASIA8");
//        CountriesAndId.put("China", "ASIA9");
//        CountriesAndId.put("Middle-East", "ASIA10");
//        CountriesAndId.put("India", "ASIA11");
//        CountriesAndId.put("Siam", "ASIA12");
//
//        //OCEANIA
//        CountriesAndId.put("Indonesia", "OCE1");
//        CountriesAndId.put("Western-Australia", "OCE2");
//        CountriesAndId.put("Eastern-Australia", "OCE3");
//        CountriesAndId.put("New-Zealand", "OCE4");
//
//        //AFRICA
//        CountriesAndId.put("North-Africa", "AFRICA1");
//        CountriesAndId.put("Egypt", "AFRICA2");
//        CountriesAndId.put("East-Africa", "AFRICA3");
//        CountriesAndId.put("Congo", "AFRICA4");
//        CountriesAndId.put("South-Africa", "AFRICA5");
//        CountriesAndId.put("Madagascar", "AFRICA6");
//
//        //SOUTH-AMERICA
//        CountriesAndId.put("Venezuela", "SA1");
//        CountriesAndId.put("Brazil", "SA2");
//        CountriesAndId.put("Peru", "SA3");
//        CountriesAndId.put("Argentina", "SA4");
//
//        //NORTH-AMERICA
//        CountriesAndId.put("Greenland", "NA1");
//        CountriesAndId.put("Alaska", "NA2");
//        CountriesAndId.put("North-West-Territory", "NA3");
//        CountriesAndId.put("Atlantis", "NA4");
//        CountriesAndId.put("Alberta", "NA5");
//        CountriesAndId.put("Ontario", "NA6");
//        CountriesAndId.put("Quebec", "NA7");
//        CountriesAndId.put("Western-United-States", "NA8");
//        CountriesAndId.put("Eastern-United-States", "NA9");
//        CountriesAndId.put("Central-America", "NA10");
//    }

}
