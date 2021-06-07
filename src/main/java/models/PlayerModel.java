package models;

import java.util.ArrayList;

public class PlayerModel {
    private String username;
    private Boolean turn;
    private ArrayList regions;
    private ArrayList lastThrow;
    private ArrayList cards;


    public PlayerModel(){

    }
    public PlayerModel(String username, Boolean turn, ArrayList regions, ArrayList lastThrow, ArrayList cards){
        this.username = username;
        this.turn = turn;
        this.regions = regions;
        this.lastThrow = lastThrow;
        this.cards = cards;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public ArrayList getRegions() {
        return regions;
    }

    public void setRegions(ArrayList regions) {
        this.regions = regions;
    }

    public ArrayList getLastThrow() {
        return lastThrow;
    }

    public void setLastThrow(ArrayList lastThrow) {
        this.lastThrow = lastThrow;
    }
}
