package models;

import application.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import controllers.SpelbordController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import views.SpelbordViewController;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class GameModel {

    // spelbord model moet niet map zijn maar bijv private SpelbordModel map = firebase.get(currentMap)
    private SpelbordModel map;
    private int turnID;
    private boolean gameOver;
    private PlayerModel players;

    private SpelbordViewController viewer;
    SpelbordController spelbordController = new SpelbordController();

    public GameModel(int TurnID) {
        this.turnID = 1;
        this.gameOver = false;
    }

    public GameModel() {
        spelbordController.attachlistener();
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



}


