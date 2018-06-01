package snake.snakeAI;

import snake.Environment.EnvironmentTwoSnake;
import snake.SnakeType;
import snake.snakeAI.ga.utils.Maths;
import snake.snakeAI.nn.SnakeAI;
import snake.snakeAI.nn.SnakeAIAgent1;
import snake.snakeAI.nn.SnakeAIAgent2;

import java.util.Arrays;

public class SnakeDiferent extends SnakeIndividual {
    private int totalFoodSnake2;

    public SnakeDiferent(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeDiferent(SnakeDiferent original) {
        super(original);
        this.totalFoodSnake2 = original.totalFoodSnake2;
    }


    @Override
    public double computeFitness(int seed) {

        int foodSnake;

        totalMovements = totalFoodSnake1  =  totalFoodSnake2 = 0;
        EnvironmentTwoSnake ambiente = (EnvironmentTwoSnake) problem.getEnvironment();


        SnakeAI aiAgent = ambiente.getAgentAI();
        SnakeAIAgent1 aiAgent1 = ambiente.getSnakeAIAgent1();


        for (int i = 0; i < problem.getNumEvironmentSimulations();i++) {

            //inicializar o ambiente
            ambiente.initialize(i);

            aiAgent.setWeights(Arrays.copyOfRange(genome, 0, ambiente.getSizeDivision()));

            aiAgent1.setWeights(Arrays.copyOfRange(genome, ambiente.getSizeDivision(), genome.length));

            //simular o ambiente
            ambiente.simulate();


            //obter o numero de movimentos
            totalMovements += ambiente.getMovementNumber();

            //obter o numero de comidas Snake 1
            foodSnake = aiAgent.getTotalFood();
            this.totalFoodSnake1 += foodSnake;

            //obter o numero de comidas Snake 2
            foodSnake = aiAgent1.getTotalFood();
            this.totalFoodSnake2 += foodSnake;

        }

        int diferencaComida = Math.abs(totalFoodSnake1 - totalFoodSnake2) * (WEIGHT_FOOD-10);

        fitness =  totalFoodSnake1*WEIGHT_FOOD + totalFoodSnake2*WEIGHT_FOOD - diferencaComida;

        this.seed = seed;
        return fitness;



    }


    @Override
    public SnakeDiferent clone() {
        return new SnakeDiferent(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\nTotal movements (avarage): ");
        sb.append(totalMovements /problem.getNumEvironmentSimulations());

        sb.append("\nTotal Food (avarage) Snake 1: ");
        sb.append(totalFoodSnake1/problem.getNumEvironmentSimulations());

        sb.append("\nTotal Food (avarage) Snake 2: ");
        sb.append(totalFoodSnake2/problem.getNumEvironmentSimulations());

        return sb.toString();
    }
}
