package snake;

import java.awt.Color;

public class Cell {
    public static final Color COLOR = Color.WHITE;

    private final int line, column;
    private SnakeAgent agent;
    private Food food;

    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public SnakeAgent getAgent() {
        return agent;
    }

    public void setAgent(SnakeAgent agent) {
        this.agent = agent;
    }

    public boolean hasAgent() {
        return agent != null;
    }

    public Food getFood() { return food; }

    public void setFood(Food food) { this.food = food; }


    public Color getColor() {
        if (hasAgent()) {
            return agent.getColor();
        } else if (hasFood()) {
            return food.getColor();
        } else {
            return Cell.COLOR;
        }
    }

    public boolean hasFood(){
        return food != null;
    }

    public void removeAgent(){
        agent = null;
    }
}