package snake.snakeAI;

import snake.Environment.Environment;
import snake.SnakeType;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAI;
import snake.snakeAI.nn.SnakeAIAgent1;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private int maxFood;

    protected static final int WEIGHT_FOOD = 1000;
    protected static final double WEIGHT_MOVEMENT = 0.2;

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/) {
        super(problem, size);
        //TODO?


    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        //TODO
        this.totalFoodSnake1 = original.totalFoodSnake1;
        this.totalMovements = original.totalMovements;
        this.maxFood = original.maxFood;
    }

    @Override
    public double computeFitness() {
        //TODO
        int food;

        totalMovements = totalFoodSnake1 =  maxFood=0;
        Environment ambiente = problem.getEnvironment();

        SnakeAI aiAgent = ambiente.getAgentAI();

        for (int i = 0; i < problem.getNumEvironmentSimulations();i++) {

            //inicializar o ambiente
            ambiente.initialize(i);


            aiAgent.setWeights(genome);

            //simular o ambiente
            ambiente.simulate();


            //obter o numero de movimentos
            totalMovements += ambiente.getMovementNumber();

            //obter o numero de comidas
            food = ambiente.getTotalFood();
            this.totalFoodSnake1 += food;

            if (maxFood < food)
                maxFood = food;

        }

        fitness = totalMovements *WEIGHT_MOVEMENT + totalFoodSnake1 *WEIGHT_FOOD;

        return fitness;
    }

    public double[] getGenome(){
        return genome;
    }

    public void setGenome(double[] genome){
        this.genome = genome;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nFitness: ");
        sb.append(fitness);
        sb.append("\nTotal movements (avarage): ");
        sb.append(totalMovements /problem.getNumEvironmentSimulations());

        sb.append("\nTotal Food (avarage): ");
        sb.append(totalFoodSnake1 /problem.getNumEvironmentSimulations());

        sb.append("\nTotal Food (max): ");
        sb.append(maxFood);
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(SnakeIndividual i) {
        //
        if (fitness == i.fitness)
            return 0;

        return (fitness > i.fitness)? 1: -1;
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }
}
