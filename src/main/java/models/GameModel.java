package models;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import controllers.SpelbordController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import observers.GameObservable;
import observers.GameObserver;
import observers.SpelbordObserver;
import views.SpelbordViewController;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class GameModel implements GameObservable {

    // spelbord model moet niet map zijn maar bijv private SpelbordModel map = firebase.get(currentMap)
    private SpelbordModel map;
    private int turnID;
    private boolean gameOver;
    private PlayerModel players;

    private SpelbordViewController viewer;
//    SpelbordController spelbordController = new SpelbordController();
    private List<GameObserver> observers = new ArrayList<GameObserver>();
    static GameModel gameModel;

//    public GameModel getGameModelInstance() {
//        if (gameModel == null) {
//            gameModel = new GameModel(1);
//            System.out.println("nieuwe instantie van GameModel is aangemaakt");
//        }
//        return gameModel;
//    }



    public GameModel(int TurnID) {
        this.turnID = 1;
        this.gameOver = false;
    }

    public GameModel() {
//        spelbordController.attachlistener();
    }

    public int getTurnID() {
        return this.turnID;
    }

    public void setTurnID(int turnID) {
        this.turnID = turnID;
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


