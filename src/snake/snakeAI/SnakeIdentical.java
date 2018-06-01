package snake.snakeAI;

import snake.Environment.EnvironmentTwoSnake;
import snake.SnakeType;
import snake.snakeAI.nn.SnakeAI;
import snake.snakeAI.nn.SnakeAIAgent1;
import snake.snakeAI.nn.SnakeAIAgent2;

public class SnakeIdentical extends SnakeIndividual {
    private int totalFoodSnake2;



    public SnakeIdentical(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeIdentical(SnakeIdentical original) {
        super(original);

        this.totalFoodSnake2 = original.totalFoodSnake2;
    }

    @Override
    public double computeFitness(int seed) {

        int foodSnake;

        totalMovements = totalFoodSnake1  =  totalFoodSnake2 = 0;
        EnvironmentTwoSnake ambiente = (EnvironmentTwoSnake) problem.getEnvironment();


        SnakeAI aiAgent = ambiente.getAgentAI();
        SnakeAIAgent2 aiAgent1 = ambiente.getSnakeAIAgent2();


        for (int i = 0; i < problem.getNumEvironmentSimulations();i++) {

            //inicializar o ambiente
            ambiente.initialize(i);


            aiAgent.setWeights(genome);
            aiAgent1.setWeights(genome);

            //simular o ambiente
            ambiente.simulate();


            //obter o numero de movimentos
            totalMovements += ambiente.getMovementNumber();

            //obter o numero de comidas
            foodSnake = aiAgent.getTotalFood();

            this.totalFoodSnake1 += foodSnake;

            foodSnake = aiAgent1.getTotalFood();
            this.totalFoodSnake2 += foodSnake;

        }

        int diferencaComida = totalFoodSnake1 - totalFoodSnake2;
        fitness = totalMovements *WEIGHT_MOVEMENT + totalFoodSnake1*WEIGHT_FOOD + totalFoodSnake2*WEIGHT_FOOD -
                (diferencaComida < 0? -diferencaComida: diferencaComida)* 10;

        this.seed = seed;
        return fitness;



    }


    @Override
    public SnakeIdentical clone() {
        return new SnakeIdentical(this);
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
