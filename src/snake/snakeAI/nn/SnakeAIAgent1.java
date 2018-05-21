package snake.snakeAI.nn;

import snake.*;
import snake.Environment.Environment;

import java.awt.Color;
import java.util.Random;

public class SnakeAIAgent1 extends SnakeAI {


    public SnakeAIAgent1(Cell cell, Color color, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Environment environment) {
        super(cell, color, inputLayerSize, hiddenLayerSize, outputLayerSize, environment);
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


        inputs[8] = perception.getN()!= null && !perception.getN().hasAgent()? 1:0;
        inputs[9] = perception.getE()!= null && !perception.getE().hasAgent()? 1:0;
        inputs[10] =perception.getS()!= null &&  !perception.getS().hasAgent()? 1:0;
        inputs[11] = perception.getW()!= null && !perception.getW().hasAgent()? 1:0;

        Cell food = environment.getFood().getCell();
        int columnFood = food.getColumn();
        int lineFood = food.getLine();


        inputs[12] = perception.getN()!= null && perception.getN().getLine() >= lineFood? 1:0;
        inputs[13] = perception.getE()!= null && perception.getE().getColumn() <= columnFood? 1:0;
        inputs[14] = perception.getS()!= null && perception.getS().getLine() <= lineFood? 1:0;
        inputs[15] = perception.getW()!= null && perception.getW().getColumn() >= columnFood? 1:0;



        /*
        inputs[4] = perception.getN()!= null? 1:0;
        inputs[5] = perception.getE()!= null? 1:0;
        inputs[6] = perception.getS()!= null? 1:0;
        inputs[7] = perception.getW()!= null? 1:0;


        inputs[4] = perception.getN()!= null && !perception.getN().hasAgent()? 1:0;
        inputs[5] = perception.getE()!= null && !perception.getE().hasAgent()? 1:0;
        inputs[6] =perception.getS()!= null &&  !perception.getS().hasAgent()? 1:0;
        inputs[7] = perception.getW()!= null && !perception.getW().hasAgent()? 1:0;

        Cell food = environment.getFood().getCell();
        int columnFood = food.getColumn();
        int lineFood = food.getLine();


        inputs[8] = perception.getN()!= null && perception.getN().getLine() >= lineFood? 1:0;
        inputs[9] = perception.getE()!= null && perception.getE().getColumn() <= columnFood? 1:0;
        inputs[10] = perception.getS()!= null && perception.getS().getLine() <= lineFood? 1:0;
        inputs[11] = perception.getW()!= null && perception.getW().getColumn() >= columnFood? 1:0;*/

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

    @Override
    protected void forwardPropagation(int[] instance) {
        super.forwardPropagation(instance);
        int soma;
        for (int i = 0; i < outputLayerSize; i++) {
            soma = 0;
            for (int j = 0; j < hiddenLayerSize +1; j++) {
                soma += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = signalFunction(soma);
        }
    }

    private int signalFunction(double inputNumber){
        return inputNumber > 1 ? 1 : 0;
    }
}
