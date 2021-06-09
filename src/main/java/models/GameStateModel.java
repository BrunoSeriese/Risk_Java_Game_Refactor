package models;

import java.util.Spliterator;

public class GameStateModel {

    // spelbord model moet niet map zijn maar bijv private SpelbordModel map = firebase.get(currentMap)

    private SpelbordModel map;
    private int turnID;
    private boolean gameOver;
    private PlayerModel players;
    private CountryModel countries;



    public GameStateModel(int TurnID){
        this.turnID = 1;
        this.gameOver = false;

    }

    //if the player turnID matches the gamestate turnID. then he can start his turn

    public void nextTurn(){
        if (gameOver == true){
            //end game. this should be called by an observer?
        }else if(turnID < 4){
            this.turnID = turnID +1;
            // roept de volgende turn aan
            map.turnInProgress(map.getPlayers(), new GameStateModel(this.getTurnID()));


        }else if (turnID == 4){
            this.turnID = 1;
            // roept de volgende turn aan
            map.turnInProgress(map.getPlayers(), new GameStateModel(this.getTurnID()));
        }
    }

    public int getTurnID(){
        return this.turnID;
    }


}