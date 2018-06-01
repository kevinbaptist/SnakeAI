package snake.Environment;

import snake.*;
import snake.snakeAI.nn.SnakeAI;
import snake.snakeAI.nn.SnakeAIAgent1;
import snake.snakeAI.nn.SnakeAIAgent2;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


/*
* x -> getColumn()
* y -> getLine()*/
public  class Environment {

    public Random random;
    protected final Cell[][] grid;
//    private final List<SnakeAgent> agents;
    protected SnakeAgent agent;
    private Food food;
    protected final int maxIterations;

    protected int movementNumber;
    protected int totalFood;

    protected boolean stop;

    public Environment(int size, int maxIterations, SnakeType type){
        this.maxIterations = maxIterations;
        this.grid = new Cell[size][size];
        movementNumber = 0;
        totalFood = 0;

        stop = false;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        switch (type){
            case ADOC:
                agent = new SnakeAdhocAgent(getCell(0,0), Color.BLACK, this);
                break;
            case RANDOM:
                agent = new SnakeRandomAgent(getCell(0,0), Color.BLACK, this);
                break;
            default:
                agent  = null;

        }
        this.random = new Random();

        this.food = new Food(null);//Nao interessa o sitio, pois irá ser limpo
    }


    public Environment(int size, int maxIterations, int numInputs, int numHiddenUnits, int numOutputs, SnakeType type) {
        this(size, maxIterations, SnakeType.AI1);

        if(type == SnakeType.AI2){
            agent = new SnakeAIAgent2(getCell(0,0), Color.YELLOW,numInputs, numHiddenUnits, numOutputs, this);
        }else if (type == SnakeType.AI1){
            agent = new SnakeAIAgent1(getCell(0,0), Color.BLUE,numInputs, numHiddenUnits, numOutputs, this);
        }
    }

    protected void cleanGrid(){
        for (Cell cell: agent.getBody()) {
            grid[cell.getLine()][cell.getColumn()].removeAgent();
        }
    }

    //TODO: modified -  kevin
    public void initialize(int seed) {

        random.setSeed(seed);
//        placeAgents(agentType);
        placeAgents();

        placeFood();


    }



    // TODO MODIFY TO PLACE ADHOC OR AI SNAKE AGENTS
    protected Cell placeAgents() {
        //antes de colocar um agente necessário limpar a grelha
        cleanGrid();

        int line = createRandomInt(0, grid.length);
        int column = createRandomInt(0, grid.length);

        Cell cell = getCell(line, column);//obter a celula correspondente à grid
        agent.reset(cell);


        start();

        return cell;
    }

    public void simulate() {
        int i;
        for ( i= 0; i < maxIterations && !stop; i++) {
            agent.act();
            fireUpdatedEnvironment();
        }
        movementNumber = i;

        totalFood = agent.getTotalFood();

    }

    //##############################################################################################################//
    //########################################### Food ## ##########################################################//
    public void placeFood() {//estava a private

        Cell cellEmpty = searchEmptyCell();
        //x-> coluna
        //y _> linha -> grid[y][x]

        this.food.setCell(cellEmpty);
        cellEmpty.setFood(this.food);


    }

    protected Cell searchEmptyCell(){
        int newFoodx, newFoody;

        do{
            newFoodx = createRandomInt(0, grid.length);
            newFoody = createRandomInt(0, grid.length);
        }while ((grid[newFoody][newFoodx].hasAgent()) || grid[newFoody][newFoodx].hasFood());

        return grid[newFoody][newFoodx];
    }



    //##############################################################################################################//
    //############################################### Aux ###########################################################//


    //return a number [min; max[
    protected int createRandomInt(int min, int max){
        return random.nextInt(max-min) + min;
    }



    //##############################################################################################################//
    //############################################### Listeners ######################################################//
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    //##############################################################################################################//
    //###############################################Gets###########################################################//
    public int getSize() {
        return grid.length;
    }

    public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public  Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    public Food getFood() {
        return food;
    }

    public SnakeAgent getAgent() {
        return agent;
    }


    public int getMovementNumber() {
        return movementNumber;
    }

    public int getTotalFood() {
        return totalFood;
    }

//    public SnakeAgent getAgentActive() {
//        return agents.get(agents.size()-1);
//    }
//
//    private boolean hasAgents(){
//        return agents.size() != 0;
//    }

    public void stop(){
        stop = true;
    }

    protected void start(){
        stop = false;
    }

    public SnakeAI getAgentAI(){
        return (SnakeAI) agent;
    }


    //TODO: retirar, apenas para debug
    public Cell[][] getGrid(){
        return grid;
    }

    public SnakeAIAgent2 getSnakeAIAgentSecond() {
        return (SnakeAIAgent2) agent;
    }
}
