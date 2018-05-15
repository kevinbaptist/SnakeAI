package snake.snakeAI.nn;

import snake.*;
import snake.Environment.Environment;

import java.awt.Color;
import java.util.Random;

public class SnakeAIAgent extends SnakeAgent {
   
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

    public SnakeAIAgent(Cell cell, Color color,int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Environment environment) {
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
    
    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    protected void forwardPropagation(int[] instance) {
        // TODO
        double soma;
        for (int i = 0; i < hiddenLayerSize; i++) {
            soma = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                soma += instance[j] * w1[j][i];
            }
            hiddenLayerOutput[i]=1/(1 + Math.pow(Math.E, -soma));
        }


        for (int i = 0; i < outputLayerSize; i++) {
            soma = 0;
            for (int j = 0; j < hiddenLayerSize +1; j++) {
                soma += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = signalFunction(soma);
        }
    }

    private int signalFunction(double inputNumber){
        //return (inputNumber%2 > 1)? 1: 0;
        return inputNumber > 1 ? 1 : 0;
    }

    @Override
    protected Action decide(Perception perception) {
        //preencher os inputs
        //inputLayerSize - 1, porque o ultimo é o bias que ja foi atribuido no construtor

        //13 neuronios de entrad
        inputs[0] = perception.getN()!= null && perception.getN().hasFood()? 1:0;
        inputs[1] = perception.getE()!= null && perception.getE().hasFood()? 1:0;
        inputs[2] = perception.getS()!= null && perception.getS().hasFood()? 1:0;
        inputs[3] = perception.getW()!= null && perception.getW().hasFood()? 1:0;

        inputs[4] = perception.getN()!= null? 1:0;
        inputs[5] = perception.getE()!= null? 1:0;
        inputs[6] = perception.getS()!= null? 1:0;
        inputs[7] = perception.getW()!= null? 1:0;


        inputs[8] = perception.getN()!= null && perception.getN().hasAgent()? 1:0;
        inputs[9] = perception.getE()!= null && perception.getE().hasAgent()? 1:0;
        inputs[10] =perception.getS()!= null &&  perception.getS().hasAgent()? 1:0;
        inputs[11] = perception.getW()!= null && perception.getW().hasAgent()? 1:0;

        Cell food = environment.getFood().getCell();
        int columnFood = food.getColumn();
        int lineFood = food.getLine();


        inputs[12] = perception.getN()!= null && perception.getN().getLine() >= lineFood? 1:0;
        inputs[13] = perception.getE()!= null && perception.getE().getColumn() <= columnFood? 1:0;
        inputs[14] = perception.getS()!= null && perception.getS().getLine() <= lineFood? 1:0;
        inputs[15] = perception.getW()!= null && perception.getW().getColumn() >= columnFood? 1:0;








        //fazer o forward propagation
        forwardPropagation(inputs);
        //fazer os 4 ifs
        if (output[0] == 0 && output[1] == 0)
            return Action.NORTH;

        if (output[0] == 0 && output[1] == 1)
            return Action.EAST;

        if (output[0] == 1 && output[1] == 1)
            return Action.SOUTH;

        if (output[0] == 1 && output[1] == 0)
            return Action.WEST;
        return null;//nunca chega aqui
    }
}
