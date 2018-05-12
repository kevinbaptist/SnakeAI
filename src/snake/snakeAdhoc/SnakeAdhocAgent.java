package snake.snakeAdhoc;

import snake.*;
import snake.Environment.Environment;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {

    public SnakeAdhocAgent(Cell head,  Color color, Environment environment) {
        super(head, color, environment);
    }


    @Override
    protected Action decide(Perception perception) {
        //obter coordenadas comida
        Cell food = environment.getFood().getCell();
        int columnFood = food.getColumn();
        int lineFood = food.getLine();


        Cell cell = perception.getN();
        if (cell != null && !cell.hasAgent() && cell.getLine() >= lineFood ){
            return Action.NORTH;
        }

        cell = perception.getE();
        if (cell != null && !cell.hasAgent() && cell.getColumn() <= columnFood){
            return Action.EAST;
        }

        cell = perception.getS();

        if (cell != null && !cell.hasAgent() && cell.getLine()<= lineFood){
            return Action.SOUTH;
        }



        cell = perception.getW();
        if (cell != null && !cell.hasAgent() &&  cell.getColumn() >= columnFood){
            return Action.WEST;
        }


        cell = perception.getN();
        if (cell != null && !cell.hasAgent()){
            return Action.NORTH;
        }

        cell = perception.getE();
        if (cell != null && !cell.hasAgent() ){
            return Action.EAST;
        }

        cell = perception.getS();

        if (cell != null && !cell.hasAgent() ){
            return Action.SOUTH;
        }



        cell = perception.getW();
        if (cell != null && !cell.hasAgent()){
            return Action.WEST;
        }

//        isDead =true;//TODO: provavelmente tirar
        environment.stop();
        return null;
        }

}
