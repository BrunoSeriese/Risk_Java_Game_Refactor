package models;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class SpelbordModel {

    private ArrayList<PlayerModel> players;
    private ArrayList<Integer> armies;
    private ArrayList<CountryModel> countries;

    public SpelbordModel(ArrayList<PlayerModel> players,  ArrayList<CountryModel> countries){
        this.players = players;

        // geef de spelers een random unique ID in de firebase naast hun naam!. daarna laad ze hier in door over de
        //firebase connectie te loopen

        for (int i = 0; i< players.size();i++){
//            this.players.set(i,(NameFromFirebase,IDfromFirebase,) = PlayersFromFirebaseConnection[i]
        }



        // countries zijn dus alle landen met hun eigen ID en ID van de speler die het bezit
        // + legers die er op staan
        this.countries = countries;

        for (int i = 0; i<countries.size(); i++){

        }

    }


    public void updateArmies(int playerIndex, int newArmies) {
        //update to firebase here

        // this updates the amount of armies a certain player has on a certain land
        CountryModel chosenCountry = countries.get(playerIndex);

        chosenCountry.setArmy(newArmies);

        // give this updated army on the country to firebase

    }
}
