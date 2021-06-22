package models;

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
    private Map<String, String> countriesWithID = new HashMap<>();
    private List<SpelbordObserver> observers = new ArrayList<>();
    static SpelbordModel spelbordModel;
    private Map<Integer, String> cards = new HashMap<Integer, String>();


    public static SpelbordModel getSpelbordModelInstance() {
        if (spelbordModel == null) {
            spelbordModel = new SpelbordModel();
        }
        return spelbordModel;
    }

    public SpelbordModel() {
    }

    public SpelbordModel(ArrayList<PlayerModel> players, ArrayList<CountryModel> countries) {
        this.players = players;
        this.countries = countries;


    }


    public void updateArmies(int playerIndex, int newArmies) {

    }

    public void turnInProgress(ArrayList<PlayerModel> players, GameModel hostedGame) {

        for (int i = 0; i < players.size(); i++) {

            if (players.get(i).getTurnID() == hostedGame.getTurnID()) {


                currentPlayer = players.get(i);

                currentPlayer.buildPhase();
                currentPlayer.attackPhase();
                currentPlayer.endPhase();


            }
        }

    }

    public void CountriesAndIdMap() {
        ArrayList<CountryModel> countriesAndID = new ArrayList<CountryModel>();
        int count = 1;
        try {
            File myObj = new File("src/main/resources/text/countries.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String countryCode = data.split(":")[1];
                String neighbors = data.split(":")[2];
                ArrayList<String> neighborData = new ArrayList<>(Arrays.asList(neighbors.split(",")));

                CountryModel newCountry = new CountryModel(countryCode, neighborData);

                if (count == 5) {
                    count = 1;
                }
                newCountry.setPlayerID(count);
                countriesAndID.add(newCountry);
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.shuffle(countriesAndID);

        this.countries = countriesAndID;

    }

    public ArrayList<PlayerModel> getPlayers() {
        return this.players;
    }

    public ArrayList<CountryModel> getCountries() {
        ArrayList<CountryModel> countryID = new ArrayList<>();

        System.out.println(countryID);
        System.out.println(countries);

        return this.countries;
    }

    public void setCountries(Map<String, String> countriesWithID) {

        ArrayList<CountryModel> newCountryList = new ArrayList<CountryModel>();
    }

    public void setPlayers(ArrayList<PlayerModel> playersFromFirebase) {
        this.players = playersFromFirebase;
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



