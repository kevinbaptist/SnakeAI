package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

import java.util.Random;

public class MutationGaussian <I extends RealVectorIndividual> extends Mutation<I> {


    public MutationGaussian(double probability) {
        super(probability);
    }

    @Override
    public void run(I ind) {
        double delta;
        Random r = new Random();

        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                delta = r.nextGaussian();//70% dos valores serão entre  -1 e 1
                ind.setGene(i, ind.getGene(i) +delta);
            }
        }
    }
}