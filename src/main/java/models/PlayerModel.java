package models;

import java.awt.*;
import java.util.ArrayList;

public class PlayerModel {
    private String username;
    private Boolean turn;
    private ArrayList regions;
    private ArrayList lastThrow;
    private ArrayList cards;
    private Color color; //random #,#,# digits for color code (ik weet niet of gewoon 'red' of 'blue' werkt, maar vgm moet het color codes, dus rgb(255,255,255) bijvoorbeeld
    private Boolean hasWon; //set true if conquered all regions
    private Boolean canExchangeCards; // if true, verschijnt button met trade in cards fzo, komt later


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
