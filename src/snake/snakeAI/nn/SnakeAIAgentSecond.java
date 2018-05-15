package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Environment.Environment;
import snake.Perception;
import snake.snakeAI.nn.SnakeAIAgent;

import java.awt.*;
import java.util.Arrays;

public class SnakeAIAgentSecond extends SnakeAIAgent {

    double[] output = new double[4];

    public SnakeAIAgentSecond(Cell cell, Color color, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Environment environment) {
        super(cell, color, inputLayerSize, hiddenLayerSize, outputLayerSize, environment);
    }

    @Override
    protected void forwardPropagation(int[] instance) {
        // TODO
        double sum;
        for (int i = 0; i < hiddenLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                sum += inputs[j] * w1[j][i];
            }
            hiddenLayerOutput[i] = 1 / (1 + Math.pow(Math.E, -sum));
        }
        for (int i = 0; i < outputLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = 1 / (1 + Math.pow(Math.E, -sum));
        }
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

/*
       inputs[12] = perception.getN()!= null && Math.abs(perception.getN().getLine() - lineFood) >= 0 ? 1:0;
        inputs[13] = perception.getE()!= null && Math.abs(perception.getE().getColumn() - columnFood) >= 0? 1:0;
        inputs[14] = perception.getS()!= null && Math.abs(perception.getS().getLine() - lineFood) >= 0? 1:0;
        inputs[15] = perception.getW()!= null && Math.abs(perception.getW().getColumn() - columnFood) >= 0? 1:0;
/*
        inputs[12] = perception.getN()!= null && Math.abs(perception.getN().getLine() - lineFood) + Math.abs(perception.getN().getLine() - columnFood) == 0 ? 1:0;
        inputs[13] = perception.getE()!= null && Math.abs(perception.getE().getColumn() - lineFood) + Math.abs(perception.getE().getColumn() - columnFood) == 0 ? 1:0;
        inputs[14] = perception.getS()!= null && Math.abs(perception.getS().getLine() - lineFood) + Math.abs(perception.getS().getLine() - columnFood) == 0 ? 1:0;
        inputs[15] = perception.getW()!= null && Math.abs(perception.getW().getColumn() - lineFood) + Math.abs(perception.getW().getColumn() - columnFood) == 0 ? 1:0;
*/
        inputs[12] = perception.getN()!= null && perception.getN().getLine() >= lineFood? 1:0;
        inputs[13] = perception.getE()!= null && perception.getE().getColumn() <= columnFood? 1:0;
        inputs[14] = perception.getS()!= null && perception.getS().getLine() <= lineFood? 1:0;
        inputs[15] = perception.getW()!= null && perception.getW().getColumn() >= columnFood? 1:0;
        /*inputs[12] = perception.getN()!= null && perception.getN().getLine() >= lineFood? 1:0;
        inputs[13] = perception.getE()!= null && perception.getE().getColumn() <= columnFood? 1:0;
        inputs[14] = perception.getS()!= null && perception.getS().getLine() <= lineFood? 1:0;
        inputs[15] = perception.getW()!= null && perception.getW().getColumn() >= columnFood? 1:0;
*//*
        inputs[16] = perception.getN()!= null && getTail().getLine() == perception.getN().getLine() ? 1:0;
        inputs[17] = perception.getE()!= null && getTail().getColumn() == perception.getE().getLine()? 1:0;
        inputs[18] = perception.getS()!= null && getTail().getLine() == perception.getS().getLine()? 1:0;
        inputs[19] = perception.getW()!= null && getTail().getColumn() == perception.getW().getLine()? 1:0;
/*
        inputs[8] = perception.getN()!= null &&  perception.getN().getLine()  ? 1:0;
        inputs[10] =perception.getS()!= null &&  perception.getS().hasAgent()? 1:0;
        inputs[11] = perception.getW()!= null && perception.getW().hasAgent()? 1:0;

        Cell food = environment.getFood().getCell();
        int columnFood = food.getColumn();
        int lineFood = food.getLine();
       /*



      /* inputs[12] = perception.getN()!= null && perception.getN().getLine() >= lineFood? 1:0;
        inputs[13] = perception.getE()!= null && perception.getE().getColumn() <= columnFood? 1:0;
        inputs[14] = perception.getS()!= null && perception.getS().getLine() <= lineFood? 1:0;
        inputs[15] = perception.getW()!= null && perception.getW().getColumn() >= columnFood? 1:0;

       /* inputs[16] = perception.getN()!= null && getTail().getLine() == perception.getN().getLine()  ? 1:0;
        inputs[17] = perception.getE()!= null && getTail().getColumn() == perception.getE().getColumn()? 1:0;*/

        /*inputs[17] = perception.getN()!= null && !getBody().contains(perception.getN()) ? 1:0;
        inputs[17] = perception.getE()!= null && !getBody().contains(perception.getE()) ? 1:0;*/





        //fazer o forward propagation
        //this.forwardPropagation(inputs);

        forwardPropagation(inputs);

        //quando não come para
        //fazer os 4 ifs
        double max = Integer.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < output.length; i++)
        {
            if( output[i] > max )
            {
                max = output[i];
                index = i;
            }

        }
        return Action.values()[ index ];

    }
}
