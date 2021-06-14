package models;

import controllers.SpelbordController;
import observers.SpelbordObservable;
import observers.SpelbordObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class SpelbordModel implements SpelbordObservable {
    private PlayerModel currentPlayer;
    private ArrayList<PlayerModel> players;
    private ArrayList<Integer> armies;
    private ArrayList<CountryModel> countries;
    private Map<String, String> countriesWithID = new HashMap<String, String>();
    private List<SpelbordObserver> observers = new ArrayList<SpelbordObserver>();
    static SpelbordModel spelbordModel;

    public static SpelbordModel getSpelbordModelInstance(){
        if (spelbordModel == null) {
            spelbordModel = new SpelbordModel();
            System.out.println("nieuwe instantie van SpelbordModel is aangemaakt");
        }
        return spelbordModel;
    }


    public SpelbordModel(){
    }


    public SpelbordModel(ArrayList<PlayerModel> players,  ArrayList<CountryModel> countries){
        this.players = players;
        this.countries = countries;
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

    public void turnInProgress(ArrayList<PlayerModel> players, GameModel hostedGame){

        for (int i = 0; i<players.size();i++){

            if (players.get(i).getTurnID() == hostedGame.getTurnID()){

                // now you have the player who has a turn
                currentPlayer = players.get(i);

                currentPlayer.buildPhase();
                currentPlayer.attackPhase();
                currentPlayer.endPhase();

                hostedGame.spelbordController.nextTurn();
            }
        }

    }

    public void CountriesAndIdMap() {
        //EUROPE
        ArrayList<CountryModel> countriesAndID = new ArrayList<CountryModel>();

        try {
            File myObj = new File("src/main/resources/text/countries.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                   String data = myReader.nextLine();

                String countryCode = data.split(":")[1];
                CountryModel newCountry = new CountryModel(countryCode);
                countriesAndID.add(newCountry);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Collections.shuffle(countriesAndID);

        this.countries = countriesAndID;

    }

    public ArrayList<PlayerModel> getPlayers(){
        return this.players;
    }

    public ArrayList<CountryModel> getCountries(){
        ArrayList<CountryModel> countryID = new ArrayList<>();
//        countryID
//        for (int i = 0; i<countries.size(); i++){
////            System.out.println(countries.get(i).getCountryID() + " " +countries.get(i).getCountryName());
//            System.out.println(countries.get(i).getCountryID());
////            String x = countries.get(i).getCountryID();
////            countryID.add(x);
//        }
        System.out.println(countryID);
        System.out.println(String.valueOf(countries));

        return this.countries;
    }

    public void setCountries(Map<String, String> countriesWithID){

        ArrayList<CountryModel> newCountryList = new ArrayList<CountryModel>();
        // elke keer loopen en dan newCountryList.add een country met zijn id in een Country Object


    }

    public void setPlayers(ArrayList<PlayerModel> playersFromFirebase){
        //maak hier een arrayList<PlayerModel> die gemaakt wordt vanuit alle 4 spelers uit firebase
        // ArrayList<PlayerModel> playersFromFirebase = new ArrayList<PlayerModel>
        // loop over de informatie van alle spelers en voeg ze toe als player object
        // dus this.players = playersFromFirebase

        this.players = playersFromFirebase;
        System.out.println("de players zijn " + players);
    }

    @Override
    public void register(SpelbordObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (SpelbordObserver s : observers) {
            s.update(this);
        }
    }


}



