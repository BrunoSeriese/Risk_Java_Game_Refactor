package models;

import java.util.ArrayList;

public class SpelerModel {
    private String name;
    private Boolean turn;
    private ArrayList regions;
    private ArrayList lastThrow;
    private ArrayList cards;


    public SpelerModel(String name, Boolean turn, ArrayList regions, ArrayList lastThrow,ArrayList cards){
        this.name=name;
        this.turn=turn;
        this.regions=regions;
        this.lastThrow=lastThrow;
        this.cards=cards;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
