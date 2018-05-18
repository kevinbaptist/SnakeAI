package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

//PLEASE, MODIFY THE CLASS NAME
public class MutationMUTATION_BASIC<I extends RealVectorIndividual> extends Mutation<I> {


    public MutationMUTATION_BASIC(double probability /*TODO?*/) {
        super(probability);
        // TODO

    }

    @Override
    public void run(I ind) {
        // TODO
        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, GeneticAlgorithm.random.nextDouble()*2 -1);//trocar o valor de true para false
            }
        }
    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation (" + probability /* + TODO?*/;
    }
}