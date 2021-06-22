package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DiceModel {

    public DiceModel( ) {

        }


        public ArrayList<Integer> roll(int numberOfDice) {

            ArrayList<Integer> diceArray = new ArrayList<>();

            for(int i = 0; i < numberOfDice; i++) {
                Random die = new Random();
                int roll = die.nextInt(6) + 1;
                diceArray.add(roll);
            }

            Collections.sort(diceArray);
            Collections.reverse(diceArray);

            return diceArray;
        }




    }




