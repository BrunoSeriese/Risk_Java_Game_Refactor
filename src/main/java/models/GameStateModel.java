package models;

public class GameStateModel {


    private int turnID;
    private boolean gameOver;



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

        }else if (turnID == 4){
            this.turnID = 1;
        }
    }

    public int getTurnID(){
        return this.turnID;
    }
}
