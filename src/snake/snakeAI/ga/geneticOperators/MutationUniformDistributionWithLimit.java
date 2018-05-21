package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

public class MutationUniformDistributionWithLimit<I extends RealVectorIndividual> extends Mutation<I> {
    private double limit;
   
    public MutationUniformDistributionWithLimit(double probability, double limit) {
        super(probability);
        this.limit = limit;

    }

    @Override
    public void run(I ind) {
        // TODO
        double delta;

        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                delta = GeneticAlgorithm.getRandom(-limit, limit);
                ind.setGene(i, ind.getGene(i) +delta);
            }
        }
    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation with limit \t" + probability + "\t" + limit;
    }
}