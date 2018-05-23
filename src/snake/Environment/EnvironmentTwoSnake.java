package snake.Environment;

import snake.Cell;
import snake.SnakeType;
import snake.snakeAI.nn.SnakeAI;
import snake.snakeAI.nn.SnakeAIAgent1;
import snake.snakeAI.nn.SnakeAIAgent2;

import java.awt.*;

public class EnvironmentTwoSnake extends Environment {
    private SnakeAI snakeAIAgent1;


    public EnvironmentTwoSnake(int size, int maxIterations, int numInputs1, int numInputs2, int numHiddenUnits1, int numHiddenUnits2,
                               int numOutputs1, int numOutputs2, SnakeType type) {
        super(size, maxIterations, numInputs1, numHiddenUnits1, numOutputs1, SnakeType.AI1);


        if(type == SnakeType.TWO_AI_EQUAL){
            snakeAIAgent1 = new SnakeAIAgent1(grid[0][1], Color.GREEN,numInputs1, numHiddenUnits1, numOutputs1, this);
        }else if(type == SnakeType.TWO_AI_DIF){
            snakeAIAgent1 = new SnakeAIAgent2(grid[0][1], Color.YELLOW,numInputs2, numHiddenUnits2, numOutputs2, this);
        }
    }


    @Override
    protected Cell placeAgents() {

        //antes de colocar um agente necessário limpar a grelha
        this.cleanGrid();

        int line = createRandomInt(0, grid.length);
        int column = createRandomInt(0, grid.length);

        Cell cell = getCell(line, column);//obter a celula correspondente à grid
        agent.reset(cell);

        snakeAIAgent1.reset(searchEmptyCell());

        start();

        return cell;

    }
    public SnakeAIAgent1 getSnakeAIAgent1(){
        return (SnakeAIAgent1) snakeAIAgent1;
    }

    public SnakeAIAgent2 getSnakeAIAgent2(){
        return (SnakeAIAgent2) snakeAIAgent1;
    }

    @Override
    protected void cleanGrid() {
        super.cleanGrid();
        for (Cell cell: snakeAIAgent1.getBody()) {
            grid[cell.getLine()][cell.getColumn()].removeAgent();
        }
    }

    @Override
    public void simulate() {

        int i;
        for ( i= 0; i < maxIterations && !stop; i++) {
            agent.act();
            snakeAIAgent1.act();
            fireUpdatedEnvironment();
        }

        movementNumber = i;

        totalFood = agent.getTotalFood();
    }


    @Override
    public void initialize(int seed) {
        random.setSeed(seed);

        this.placeAgents();
        placeFood();
    }
}
