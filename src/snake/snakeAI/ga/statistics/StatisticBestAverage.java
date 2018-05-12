package snake.snakeAI.ga.statistics;

import snake.snakeAI.ga.experiments.ExperimentEvent;
import snake.snakeAI.ga.GAEvent;
import snake.snakeAI.ga.GAListener;
import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.Individual;
import snake.snakeAI.ga.Problem;
import snake.snakeAI.ga.utils.Maths;

public class StatisticBestAverage<E extends Individual, P extends Problem<E>> implements GAListener  {
    
    private final double[] fitness;
    private final double [] totalFood;
    private int run;
    
    public StatisticBestAverage(int numRuns) {
        fitness = new double[numRuns];
        totalFood = new double[numRuns];
    }

    @Override
    public void generationEnded(GAEvent e) {    
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<E, P> ga = e.getSource();
        totalFood[run] = ga.getBestInRun().getTotalFoodSnake1();
        fitness[run++] = ga.getBestInRun().getFitness();

    }

    @Override
    public void experimentEnded(ExperimentEvent e) {

        double avg_fitness = Maths.average(fitness);
        double avg_food = Maths.average(totalFood);
        double sd = Maths.standardDeviation(fitness, avg_fitness);

        avg_food /= 10;

        //TODO:alteracao do texto dos experimentos

        snake.snakeAI.ga.utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", e.getSource() + "\t" + avg_fitness + "\t" + avg_food+  "\t"+ sd + "\r\n");
    }    
}
