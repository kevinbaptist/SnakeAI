package snake.snakeAI;

import snake.Environment.Environment;
import snake.Environment.EnvironmentTwoSnake;
import snake.SnakeType;
import snake.snakeAI.ga.Problem;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SnakeProblem implements Problem<SnakeIndividual> {
    private static final int NUM_NN_INPUTS = 17; // TODO THIS IS A FAKE NUMBER; PLEASE ADAPT TO YOUR CASE
    private static final int NUM_NN_OUTPUTS = 2; // TODO THIS IS A FAKE NUMBER; PLEASE ADAPT TO YOUR CASE
    private static int GENOME_SIZE;// = NUM_NN_INPUTS*NUM_NN_OUTPUTS*10; //TODO: Apenas para o meu caso



    final private int environmentSize;
    final private int maxIterations;
     private int numInputs;
     private int numHiddenUnits;
     public int numOutputs;
    final private int numEnvironmentRuns;

    private Environment environment;//TODO: voltar a colocar final

    private SnakeType type;

    public SnakeProblem(SnakeType type) {
        this.environmentSize = 10;
        this.maxIterations = 500;
        this.numEnvironmentRuns = 10;


        environment = new Environment(environmentSize, maxIterations,type );
    }


    public SnakeProblem(int environmentSize, int maxIterations, int numHiddenUnits, int numEnvironmentRuns, SnakeType type) {
        this.environmentSize = environmentSize;
        this.maxIterations = maxIterations;
        this.numInputs = NUM_NN_INPUTS;
        this.numHiddenUnits = numHiddenUnits;
        this.numOutputs = NUM_NN_OUTPUTS;
        this.numEnvironmentRuns = numEnvironmentRuns;

        this.type = type;
        GENOME_SIZE =  (NUM_NN_INPUTS*numHiddenUnits)+(numHiddenUnits+1) *NUM_NN_OUTPUTS;

        if (type == SnakeType.AI)
            environment = new Environment(environmentSize, maxIterations, numInputs, numHiddenUnits, numOutputs);
        if (type == SnakeType.TWO_AI_EQUAL)
            environment = new EnvironmentTwoSnake(environmentSize, maxIterations, numInputs, numHiddenUnits, numOutputs);



    }

    @Override
    public SnakeIndividual getNewIndividual() {
        if (type == SnakeType.AI)
            return new SnakeIndividual(this, GENOME_SIZE /*TODO?*/);
        return new SnakeIdentical(this,GENOME_SIZE);
    }





    public Environment getEnvironment() {
        return environment;
    }

    public int getNumEvironmentSimulations() {
        return numEnvironmentRuns;
    }

    // MODIFY IF YOU DEFINE OTHER PARAMETERS
    public static SnakeProblem buildProblemFromFile(File file, SnakeType type) throws IOException {
        java.util.Scanner f = new java.util.Scanner(file);

        List<String> lines = new LinkedList<>();

        while (f.hasNextLine()) {
            String s = f.nextLine();
            if (!s.equals("") && !s.startsWith("//")) {
                lines.add(s);
            }
        }

        List<String> parametersValues = new LinkedList<>();
        for (String line : lines) {
            String[] tokens = line.split(":");
            parametersValues.add(tokens[1].trim());
        }

        int environmentSize = Integer.parseInt(parametersValues.get(0));
        int maxIterations = Integer.parseInt(parametersValues.get(1));
        int numHiddenUnits = Integer.parseInt(parametersValues.get(2));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(3));


        return new SnakeProblem(environmentSize, maxIterations, numHiddenUnits, numEnvironmentRuns, type);
    }

    public int getGenomeSize(){
        return GENOME_SIZE;
    }

    // MODIFY IF YOU DEFINE OTHER PARAMETERS
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Environment size: ");
        sb.append(environmentSize);
        sb.append("\n");
        sb.append("Maximum number of iterations: ");
        sb.append(maxIterations);
        sb.append("\n");
        sb.append("Number of inputs: ");
        sb.append(numInputs);
        sb.append("\n");
        sb.append("Number of hidden units: ");
        sb.append(numHiddenUnits);
        sb.append("\n");
        sb.append("Number of outputs: ");
        sb.append(numOutputs);
        sb.append("\n");
        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentRuns);


        return sb.toString();
    }

}
