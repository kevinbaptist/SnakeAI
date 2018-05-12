package snake.snakeRandom;

import snake.*;
import snake.Environment.Environment;

import java.awt.*;
import java.util.Random;

public class SnakeRandomAgent extends SnakeAgent {
    // TODO

    public SnakeRandomAgent(Cell cell, Color color, Environment environment) {
        super(cell, color, environment);
    }

    @Override
    protected Action decide(Perception perception) {
        Random rnd = new Random();
        int pos = rnd.nextInt(4);
        Cell cell;
        switch (pos){
            case 0: cell = perception.getN();
                if(cell != null && !cell.hasAgent() ){
                    return Action.NORTH;
                }
            case 1 :cell = perception.getE();
                if(cell != null  && !cell.hasAgent() ){
                    return Action.EAST;
                }
            case 2: cell = perception.getS();
                if(cell != null && !cell.hasAgent() ){

                    return Action.SOUTH;
                }
            case 3: cell = perception.getW();
                if(cell != null && !cell.hasAgent() ){
                    return Action.WEST;
                }
                break;
        }




        cell = perception.getN();
        if (cell != null && !cell.hasAgent()){
            return Action.NORTH;
        }

        cell = perception.getE();
        if (cell != null && !cell.hasAgent()){
            return Action.EAST;
        }

        cell = perception.getS();

        if (cell != null && !cell.hasAgent()){
            return Action.SOUTH;
        }


        cell = perception.getW();
        if (cell != null && !cell.hasAgent()){
            return Action.WEST;
        }

//        isDead = true;TODO: provavelmente tirar: este nao tem ambiente...

        return null;
    }
}
