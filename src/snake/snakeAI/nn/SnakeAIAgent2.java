package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Environment.Environment;
import snake.Perception;

import java.awt.*;

public class SnakeAIAgent2 extends SnakeAI {


    public SnakeAIAgent2(Cell cell, Color color, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Environment environment) {
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
        inputs[10] =perception.getS()!= null && !perception.getS().hasAgent()? 1:0;
        inputs[11] = perception.getW()!= null && !perception.getW().hasAgent()? 1:0;

        Cell food = environment.getFood().getCell();
        int columnFood = food.getColumn();
        int lineFood = food.getLine();

        inputs[12] = perception.getN()!= null && Math.abs(perception.getN().getLine() - lineFood)> 0 ? 1:0;
        inputs[13] = perception.getE()!= null && Math.abs(perception.getE().getColumn() - columnFood) > 0 ? 1:0;
        inputs[14] = perception.getS()!= null && Math.abs(perception.getS().getLine() - lineFood) >0 ? 1:0;
        inputs[15] = perception.getW()!= null && Math.abs(perception.getW().getColumn() - columnFood) >0 ? 1:0;




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

    @Override
    protected void forwardPropagation(int[] instance) {
        super.forwardPropagation(instance);
        int soma;
        for (int i = 0; i < outputLayerSize; i++) {
            soma = 0;
            for (int j = 0; j < hiddenLayerSize +1; j++) {
                soma += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = 1 / (1 + Math.pow(Math.E, -soma));
        }
    }
}
