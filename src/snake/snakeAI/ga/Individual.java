package snake.snakeAI.ga;

public abstract class Individual<P extends Problem, I extends Individual> implements Comparable<I>{

    protected double fitness;
    protected P problem;
    protected int totalFoodSnake1;//TODO: EU ADicionei nao estava
    protected int totalMovements;//TODO: Eu adicionei, não estava: para usar nas estistica de escrever no ficheiro

    public Individual(P problem) {
        this.problem = problem;
    }

    public Individual(Individual<P, I> original) {
        this.problem = original.problem;
        this.fitness = original.fitness;
        this.totalFoodSnake1 = original.totalFoodSnake1;
    }

    public abstract double computeFitness();
    
    public abstract int getNumGenes();
    
    public abstract void swapGenes(I other, int g);    

    public double getFitness() {
        return fitness;
    }

    public int getTotalFoodSnake1(){
        return totalFoodSnake1;
    }

    @Override
    public abstract I clone();

    public int getTotalMovements(){
        return totalMovements;
    }
}
