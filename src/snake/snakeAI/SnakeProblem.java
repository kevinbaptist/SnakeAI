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
    private static int GENOME_SIZE;

    final private int environmentSize;
    final private int maxIterations;
    private int numInputs1;
    private int numInputs2;
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


    public SnakeProblem(int environmentSize, int maxIterations, int numInputsSnake1, int numInputsSnake2, int numHiddenUnitsSnake1, int numHiddenUnitsSnake2,
                        int numOutputsSnake1, int numOutputsSnake2, int numEnvironmentRuns, SnakeType type) {
        this.environmentSize = environmentSize;
        this.maxIterations = maxIterations;
        this.numInputs1 = numInputsSnake1;
        this.numInputs2 = numInputsSnake2;
        this.numHiddenUnits1 = numHiddenUnitsSnake1;
        this.numHiddenUnits2 = numHiddenUnitsSnake2;
        this.numOutputs1 = numOutputsSnake1;
        this.numOutputs2 = numOutputsSnake2;
        this.numEnvironmentRuns = numEnvironmentRuns;

        this.type = type;

        switch (type){
            case AI1:
                GENOME_SIZE =  (numInputs1*numHiddenUnits1)+(numHiddenUnits1+1) *numOutputs1;
                environment = new Environment(environmentSize, maxIterations, numInputs1, numHiddenUnits1, numOutputs1, type);

                break;

            case AI2:
                GENOME_SIZE =  (numInputs2*numHiddenUnits2)+(numHiddenUnits2+1) *numOutputs2;
                environment = new Environment(environmentSize, maxIterations, numInputs2, numHiddenUnits2, numOutputs2, type);
                break;

            case TWO_AI_EQUAL:

                GENOME_SIZE =  (numInputs1*numHiddenUnits1)+(numHiddenUnits1+1) *numOutputs1;

                environment = new EnvironmentTwoSnake(environmentSize, maxIterations, numInputs1, numInputs2, numHiddenUnits1, numHiddenUnits2,
                        numOutputs1, numOutputs2, type);

                break;

            case TWO_AI_DIF:
                GENOME_SIZE =  (numInputs2*numHiddenUnits2)+(numHiddenUnits2+1) *numOutputs2;

                environment = new EnvironmentTwoSnake(environmentSize, maxIterations, numInputs1, numInputs2, numHiddenUnits1, numHiddenUnits2,
                        numOutputs1, numOutputs2, type);
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
        int numInputsSnake1 = Integer.parseInt(parametersValues.get(2));
        int numInputsSnake2 = Integer.parseInt(parametersValues.get(3));
        int numHiddenUnitsSnake1 = Integer.parseInt(parametersValues.get(4));
        int numHiddenUnitsSnake2 = Integer.parseInt(parametersValues.get(5));
        int numOutputsSnake1 = Integer.parseInt(parametersValues.get(6));
        int numOutputsSnake2 = Integer.parseInt(parametersValues.get(7));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(8));


        return new SnakeProblem(environmentSize, maxIterations, numInputsSnake1, numInputsSnake2, numHiddenUnitsSnake1, numHiddenUnitsSnake2, numOutputsSnake1, numOutputsSnake2, numEnvironmentRuns, type);
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
        sb.append("Number of inputs Snake_1: ");
        sb.append(numInputs1);
        sb.append("\n");
        sb.append("Number of inputs Snake_2: ");
        sb.append(numInputs2);
        sb.append("\n");
        sb.append("Number of hidden units Snake_1: ");
        sb.append(numHiddenUnits1);
        sb.append("\n");
        sb.append("Number of hidden units Snake_2: ");
        sb.append(numHiddenUnits2);
        sb.append("\n");
        sb.append("Number of outputs Snake_1: ");
        sb.append(numOutputs1);
        sb.append("\n");
        sb.append("Number of outputs Snake_2: ");
        sb.append(numOutputs2);
        sb.append("\n");
        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentRuns);


        return sb.toString();
    }

}
