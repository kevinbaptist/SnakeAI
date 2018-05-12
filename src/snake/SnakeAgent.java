package snake;

import snake.Environment.Environment;

import java.awt.Color;
import java.util.ArrayList;

public abstract class SnakeAgent {
    protected Environment environment;
    protected Color color;
//    protected boolean isDead;

    private ArrayList<Cell> body;
    protected int totalFood;

    public ArrayList<Cell> getBody() {
        return body;
    }

    public SnakeAgent(Cell head, Color color, Environment environment) {
        this.environment = environment;
        this.color = color;
        totalFood = 0;

        if (head == null)
            return;

        body = new ArrayList<>();
        body.add(head);
        head.setAgent(this);
    }

    public void act() {
        Perception perception = buildPerception();
        Action action = decide(perception);
        execute(action);
    }

    public int getTotalFood() {
        return totalFood;
    }

    protected Perception buildPerception() {
        Cell head = getHead();
        return new Perception(environment.getNorthCell(head), environment.getSouthCell(head), environment.getEastCell(head),
                environment.getWestCell(head));
    }

    protected Cell getHead() {
        return body.get(0);
    }

    protected void execute(Action action)
    {
        Cell head = getHead();
        Cell nextCell = null;

        if (action == Action.NORTH && head.getLine() != 0) {
            nextCell = environment.getNorthCell(head);
        } else if (action == Action.SOUTH && head.getLine() != environment.getNumLines() - 1) {
            nextCell = environment.getSouthCell(head);
        } else if (action == Action.WEST && head.getColumn() != 0) {
            nextCell = environment.getWestCell(head);
        } else if (action == Action.EAST && head.getColumn() != environment.getNumColumns() - 1) {
            nextCell = environment.getEastCell(head);
        }

        if (nextCell != null  && !nextCell.hasAgent()) {
            if (nextCell.hasFood()){
                //gerar nova comida
                totalFood++;
                environment.placeFood();

                //aumentar tamanho da cobra
                increaseSize(nextCell, this);
                setHeadSnake(nextCell, false);

            }else{
                setHeadSnake( nextCell, true);//apenas necessario quando não há aumento
            }
        }
        else{
//            isDead=true;
            environment.stop();
        }
    }

    protected abstract Action decide(Perception perception);




    private void increaseSize(Cell nextCell, SnakeAgent agent) {
        body.add(0, nextCell);//new element always become the head
        body.get(0).setAgent(agent);
    }

    private void setHeadSnake( Cell cell, Boolean updateSnake) {
        if (updateSnake)//snake only move when it doesn't eat a food
            update(cell, this);

        Cell head = getHead();

        if (head.hasFood()){
            head.setFood(null);//retira a comida
        }
    }



    private void update(Cell cell, SnakeAgent agent){
        if (!hasBody()){
            moveHead(cell, agent);
        }else {
            moveAllBody(cell, agent);
        }
    }

    public Color getColor() {
        return color;
    }

    private void moveAllBody(Cell cell, SnakeAgent agent) {
        body.add(0, cell);
        getHead().setAgent(agent);
        removeTail();
    }


    private void moveHead(Cell cell, SnakeAgent agent) {
        body.get(0).setAgent(null);
        body.set(0, cell);
        body.get(0).setAgent(agent);
    }

    private void removeTail() {
        int last = body.size() - 1;
        body.get(last).setAgent(null);
        body.remove(last);
    }
    private boolean hasBody(){
        return body.size() != 1;
    }

//    public boolean isDead() {
////        return isDead;
//    }

    public void reset(Cell head){
        body.clear();

        body.add(head);
        head.setAgent(this);

        totalFood = 0;
//        isDead = false;

    }

}
