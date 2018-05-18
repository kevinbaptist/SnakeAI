package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

//PLEASE, MODIFY THE CLASS NAME
public class MutationUniformDistributionWithLimit<I extends RealVectorIndividual> extends Mutation<I> {

   
    public MutationUniformDistributionWithLimit(double probability /*TODO?*/) {
        super(probability);
        // TODO

    }

    @Override
    public void run(I ind) {
        // TODO
        double delta;

        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                delta = GeneticAlgorithm.getRandom(-0.5, 0.5);
                ind.setGene(i, ind.getGene(i) +delta);//TODO: GeneticAlgorithm.random.nextDouble()*2 -1
            }
        }
    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation (" + probability /* + TODO?*/;
    }
}