package snake.snakeAI;

import snake.Environment.Environment;
import snake.Environment.EnvironmentTwoSnake;
import snake.SnakeType;
import snake.snakeAI.ga.Problem;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SnakeProblem implements Problem<SnakeIndividual> {
    protected static int GENOME_SIZE;

    final protected int environmentSize;
    final protected int maxIterations;

    protected int numInputs;
    protected int numHiddenUnits;
    protected int numOutputs;


    final protected int numEnvironmentRuns;
    protected Environment environment;
    private SnakeType type;

    /**
     * Constructor to ADOC and RANDOM Snakes
     * */
    public SnakeProblem(SnakeType type) {
        this.environmentSize = 10;
        this.maxIterations = 10;
        this.numEnvironmentRuns = 10;

        environment = new Environment(environmentSize, maxIterations,type );
    }

    /**
     * Constructor to AI snakes.
     * */
    public SnakeProblem(int environmentSize, int maxIterations, int numInputsSnake, int numHiddenUnitsSnake, int numOutputsSnake, int numEnvironmentRuns, SnakeType type) {
        this.environmentSize = environmentSize;
        this.maxIterations = maxIterations;
        this.numInputs = numInputsSnake;
        this.numHiddenUnits = numHiddenUnitsSnake;
        this.numOutputs = numOutputsSnake;
        this.numEnvironmentRuns = numEnvironmentRuns;
        this.type = type;


        switch (type){
            case AI1:
            case AI2:
                GENOME_SIZE =  (numInputs * numHiddenUnits)+(numHiddenUnits +1) * numOutputs;
                environment = new Environment(environmentSize, maxIterations, numInputs, numHiddenUnits, numOutputs, type);
                break;

            case TWO_AI_EQUAL:
                GENOME_SIZE =  (numInputs * numHiddenUnits)+(numHiddenUnits +1) * numOutputs;
                environment = new EnvironmentTwoSnake(environmentSize, maxIterations, numInputs, numInputs, numHiddenUnits, numHiddenUnits,
                        numOutputs, numOutputs, type);
                break;
        }
    }



    @Override
    public SnakeIndividual getNewIndividual() {
        if (type == SnakeType.AI1 || type == SnakeType.AI2)
            return new SnakeIndividual(this, GENOME_SIZE);

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
        int numInputsSnake = Integer.parseInt(parametersValues.get(2));
        int numHiddenUnitsSnake = Integer.parseInt(parametersValues.get(3));
        int numOutputsSnake = Integer.parseInt(parametersValues.get(4));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(5));

        switch (type){
            case AI1:
            case AI2:
            case TWO_AI_EQUAL:
                return new SnakeProblem(environmentSize, maxIterations, numInputsSnake, numHiddenUnitsSnake, numOutputsSnake, numEnvironmentRuns, type);
            default:

                int numInputsSnake2 = Integer.parseInt(parametersValues.get(6));
                int numHiddenUnitsSnake2 = Integer.parseInt(parametersValues.get(7));
                int numOutputsSnake2 = Integer.parseInt(parametersValues.get(8));
                return new SnakeProblemForTwoSnake(environmentSize, maxIterations, numInputsSnake, numInputsSnake2, numHiddenUnitsSnake, numHiddenUnitsSnake2, numOutputsSnake, numOutputsSnake2, numEnvironmentRuns, type);


        }





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

        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentRuns);
        sb.append("\n");

        sb.append("Maximum number of iterations: ");
        sb.append(maxIterations);
        sb.append("\n");
        sb.append("Snake " +  type + ":");
        sb.append("\n");
        sb.append("\t" + " -> Number of inputs: ");
        sb.append(numInputs);
        sb.append("\n");

        sb.append("\t" + " -> Number of hidden units: ");
        sb.append(numHiddenUnits);
        sb.append("\n");

        sb.append("\t" + " -> Number of outputs:");
        sb.append(numOutputs);



        return sb.toString();
    }

}
