package models;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class SpelbordModel {

    private ArrayList<PlayerModel> players;
    private ArrayList<Integer> armies;
    private ArrayList<CountryModel> countries;

    public SpelbordModel(ArrayList<PlayerModel> players,  ArrayList<CountryModel> countries){
        this.players = players;

        // countries zijn dus alle landen met hun eigen ID en playercolor van de speler die het bezit
        // + legers die er op staan
        this.countries = countries;
    }


    public void updateArmies(int playerIndex, int newArmies) {
        //update to firebase here

        // this updates the amount of armies a certain player has on a certain land
        CountryModel chosenCountry = countries.get(playerIndex);

        chosenCountry.setArmy(newArmies);

        // give this updated army on the country to firebase

    }
}
