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
    private static final int NUM_NN_INPUTS = 13;
    private static final int NUM_NN_OUTPUTS_SNAKE_1 = 2;//TODO: ir buscar ficheiro
    private static final int NUM_NN_OUTPUTS_SNAKE_2 = 4;
    private static int GENOME_SIZE;



    final private int environmentSize;
    final private int maxIterations;
    private int numInputs;
    private int numHiddenUnits1;
    private int numHiddenUnits2;

    private int numOutputs1;
    private int numOutputs2;

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
        this.numHiddenUnits1 = numHiddenUnits;
        this.numEnvironmentRuns = numEnvironmentRuns;

        this.type = type;

        switch (type){
            case AI1:
                this.numOutputs1 = NUM_NN_OUTPUTS_SNAKE_1;
                GENOME_SIZE =  (NUM_NN_INPUTS*numHiddenUnits)+(numHiddenUnits+1) *NUM_NN_OUTPUTS_SNAKE_1;
                environment = new Environment(environmentSize, maxIterations, numInputs, numHiddenUnits, numOutputs1, type);

                break;

            case AI2:
                this.numOutputs1 = NUM_NN_OUTPUTS_SNAKE_2;
                GENOME_SIZE =  (NUM_NN_INPUTS*numHiddenUnits)+(numHiddenUnits+1) *NUM_NN_OUTPUTS_SNAKE_2;
                environment = new Environment(environmentSize, maxIterations, numInputs, numHiddenUnits, numOutputs1, type);
                break;

            case TWO_AI_EQUAL:
                this.numOutputs1 = NUM_NN_OUTPUTS_SNAKE_1;

                GENOME_SIZE =  (NUM_NN_INPUTS*numHiddenUnits)+(numHiddenUnits+1) *NUM_NN_OUTPUTS_SNAKE_1;

                environment = new EnvironmentTwoSnake(environmentSize, maxIterations, numInputs, numHiddenUnits, numHiddenUnits,
                        numOutputs1, numOutputs1, type);

                break;

            case TWO_AI_DIF:
                this.numOutputs1 = NUM_NN_OUTPUTS_SNAKE_2;
                GENOME_SIZE =  (NUM_NN_INPUTS*numHiddenUnits)+(numHiddenUnits+1) *NUM_NN_OUTPUTS_SNAKE_2;

                environment = new EnvironmentTwoSnake(environmentSize, maxIterations, numInputs, numHiddenUnits, numHiddenUnits,
                        2, numOutputs1, type);
                break;

        }
    }

    @Override
    public SnakeIndividual getNewIndividual() {
        if (type == SnakeType.AI1 || type == SnakeType.AI2)
            return new SnakeIndividual(this, GENOME_SIZE /*TODO?*/);

        if (type == SnakeType.TWO_AI_EQUAL)
            return new SnakeIdentical(this,GENOME_SIZE);

        return new SnakeDiferent(this, GENOME_SIZE);


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
        sb.append(numHiddenUnits1);
        sb.append("\n");
        sb.append("Number of outputs: ");
        sb.append(numOutputs1);
        sb.append("\n");
        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentRuns);


        return sb.toString();
    }

}
