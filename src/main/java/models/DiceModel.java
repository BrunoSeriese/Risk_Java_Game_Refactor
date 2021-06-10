package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class DiceModel {

        private int roll;
        private ArrayList<Integer> diceArray;
        private Random die;

        public DiceModel( ) {

        }


        public ArrayList<Integer> roll(int numberOfDice) {

            diceArray = new ArrayList<>();

            for(int i = 0; i < numberOfDice; i++) {
                die = new Random();
                roll = die.nextInt(6) + 1;
                diceArray.add(roll);
            }

            Collections.sort(diceArray);
            Collections.reverse(diceArray);

            return diceArray;
        }




    }




