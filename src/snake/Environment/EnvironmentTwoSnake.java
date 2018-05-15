package snake.Environment;

import snake.Cell;
import snake.SnakeType;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAI.nn.SnakeAIAgentSecond;

import java.awt.*;

public class EnvironmentTwoSnake extends Environment {
    private SnakeAIAgent snakeAIAgent1;


    public EnvironmentTwoSnake(int size, int maxIterations, int numInputs, int numHiddenUnits, int numOutputs, SnakeType type) {
        super(size, maxIterations, numInputs, numHiddenUnits, numOutputs, type);
        if(type == SnakeType.TWO_AI_EQUAL){
            snakeAIAgent1 = new SnakeAIAgent(grid[0][1], Color.GREEN,numInputs, numHiddenUnits, numOutputs, this);
        }
        if(type == SnakeType.TWO_AI_DIF){
            snakeAIAgent1 = new SnakeAIAgentSecond(grid[0][1], Color.GREEN,numInputs, numHiddenUnits, numOutputs, this);
        }
    }


    @Override
    protected Cell placeAgents(SnakeType type) {
//        Cell cellOcupadoBySnake1 = super.placeAgents(type);

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
    public SnakeAIAgent getSnakeAIAgent(){
        return snakeAIAgent1;
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
//        System.out.println("agent 1 na casa: " + agent.getBody().get(0).getColumn() +", " + agent.getBody().get(0).getLine() );
//        System.out.println("agent 2 na casa: " + snakeAIAgent1.getBody().get(0).getColumn() +", " +snakeAIAgent1.getBody().get(0).getLine());
//        System.out.println("iteracoes : " + i);


        totalFood = agent.getTotalFood();
    }


    @Override
    public void initialize(int seed, SnakeType type) {
        random.setSeed(seed);

        this.placeAgents(type);
        placeFood();
    }

    public SnakeAIAgent getSnakeAIAgent1() {
        return snakeAIAgent1;
    }
}
