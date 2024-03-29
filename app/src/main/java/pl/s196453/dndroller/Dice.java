package pl.s196453.dndroller;

import java.util.Random;

public class Dice {

    private Random r = new Random();

    public Dice(){

    }
    //nearly all special throws I found consist of a dice cast and added mod value
    public String specialThrow(int attribute){
        int throwResult = throwDice(20,1);
        String result = "Throw: " + throwResult + " + Mod: " + abilityModCheck(attribute) + " = " + (throwResult+abilityModCheck(attribute));
        return result;
    }

    //according to the table in game docs
    public int abilityModCheck(int attribute){
        int[] modValues = new int[] {0,-5,-4,-4,-3,-3,-2,-2,-1,-1,0,0,1,1,2,2,3,3,4,4};
        return modValues[attribute];
    }

    public int throwDice(int sides, int ammount){
        int res = 0;
        for(int i=1; i<=ammount; i++){
            res +=r.nextInt(sides) + 1;
        }
        return res;
    }
}
