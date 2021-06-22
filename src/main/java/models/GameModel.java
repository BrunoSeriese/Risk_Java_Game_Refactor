package models;

import observers.GameObservable;
import observers.GameObserver;
import views.SpelbordViewController;

import java.util.ArrayList;
import java.util.List;

public class GameModel implements GameObservable {

    public static int TurnID;
    public static double RED = 0.0;
    public static double BLUE = -0.85;
    public static double GREEN = 0.70;
    public static double ORANGE = 0.20;
    private SpelbordModel map;
    private int turnID;
    private boolean gameOver;
    private PlayerModel players;


    private int phaseID;
    private ArrayList<String> selectedCountries;
    private SpelbordViewController viewer;
    private List<GameObserver> observers = new ArrayList<>();
    static GameModel gameModel;



    public GameModel(int TurnID) {
        this.turnID = 1;
        this.gameOver = false;
        this.phaseID = 1;
        this.selectedCountries = new ArrayList<>();
    }

    public GameModel() {
    }

    public void clearSelectedCountries(String countryID){

        if (this.selectedCountries == null || this.selectedCountries.size() < 2  ){
            assert this.selectedCountries != null;
            this.selectedCountries.add(countryID);
        } else if (this.selectedCountries.size() == 2){
            this.selectedCountries.remove(0);
            this.selectedCountries.remove(1);
        }
    }

    public void clearSelectedCountries(){
        this.selectedCountries.clear();
    }

    public ArrayList<String> getSelectedCountries(){
        return this.selectedCountries;
    }

    public int getTurnID() {
        return this.turnID;
    }

    public void setTurnID(int turnID) {
        this.turnID = turnID;
    }

    public int getPhaseID() {
        return this.phaseID;
    }

    public void setPhaseID(int phaseID) {
        this.phaseID = phaseID;
    }

    public void updatePhaseID() {
        if (this.phaseID < 3) {
            this.phaseID += 1;
        } else {this.phaseID = 1;}

    }

    public SpelbordModel getMap() {
        return map;
    }

    public void setMap(SpelbordModel map) {
        this.map = map;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public PlayerModel getPlayers() {
        return players;
    }

    public void setPlayers(PlayerModel players) {
        this.players = players;
    }

    @Override
    public void register(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (GameObserver g : observers) {
            g.update(this);
        }
    }
}


