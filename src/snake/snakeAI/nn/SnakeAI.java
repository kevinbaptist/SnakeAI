package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Environment.Environment;
import snake.Perception;
import snake.SnakeAgent;

import java.awt.*;

public abstract class SnakeAI extends SnakeAgent {
    final protected int inputLayerSize;
    final protected int hiddenLayerSize;
    final protected int outputLayerSize;

    /**
     * Network inputs array.
     */
    final protected int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final protected double[][] w1;
    /**
     * Output layer weights.
     */
    final protected double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final protected double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final protected int[] output;


    public SnakeAI(Cell cell, Color color, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Environment environment) {
        super(cell, color, environment);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new int[outputLayerSize];
    }

    /**
     * Initializes the network's weights
     *
     * @param weights vector of weights comming from the individual.
     */
    public void setWeights(double[] weights) {
        // TODO:

        int t = 0;
        for (int i = 0; i <inputLayerSize ; i++) {
            for (int j = 0; j < hiddenLayerSize; j++) {
                w1[i][j]= weights[t++];
            }
        }

        for (int i = 0; i < hiddenLayerSize +1; i++) {
            for (int j = 0; j < outputLayerSize; j++) {
                w2[i][j] = weights[t++];
            }
        }


    }

    @Override
    protected abstract Action decide(Perception perception);
}
