package gui;

import snake.SnakeType;
import snake.snakeAI.ga.geneticOperators.*;
import snake.snakeAI.ga.selectionMethods.RouletteWheel;
import snake.snakeAI.ga.selectionMethods.SelectionMethod;
import snake.snakeAI.ga.selectionMethods.Tournament;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import snake.snakeAI.SnakeIndividual;
import snake.snakeAI.SnakeProblem;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;

    // TODO MODIFY TO CHANGE THE DEFAULT PARAMETER VALUES
    public static final String SEED = "1";
    public static final String POPULATION_SIZE = "100";
    public static final String GENERATIONS = "1000";
    public static final String TOURNAMENT_SIZE = "10";
    public static final String PROB_RECOMBINATION = "0.85";
    public static final String PROB_MUTATION = "0.2";


    JTextField textFieldSeed = new JTextField(SEED, TEXT_FIELD_LENGHT);
    JTextField textFieldN = new JTextField(POPULATION_SIZE, TEXT_FIELD_LENGHT);
    JTextField textFieldGenerations = new JTextField(GENERATIONS, TEXT_FIELD_LENGHT);
    String[] selectionMethods = {"Tournament", "Roulette"};
    String[] selectionAlgorithm = {"Snake AdaDhoc",
            "Snake Aleatoria",
            "Snake AI1",
            "Snake AI2",
            "Two IdenticalSnakes",
            "Two Diferent Snakes"};
    JComboBox comboBoxSelectionMethods = new JComboBox(selectionMethods);
    JComboBox comboBoxSelectionAlgorithm = new JComboBox(selectionAlgorithm);

    JTextField textFieldTournamentSize = new JTextField(TOURNAMENT_SIZE, TEXT_FIELD_LENGHT);
    String[] recombinationMethods = {"One cut", "Two cuts", "Uniform"};
    JComboBox comboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField textFieldProbRecombination = new JTextField(PROB_RECOMBINATION, TEXT_FIELD_LENGHT);
    String[] selectionMutation = {"Mutacao uniforme", "Mutacao uniforme com limite" , "Mutacao Gaussian"};
    JComboBox comboBoxSelectionMutation = new JComboBox(selectionMutation);
    JTextField textFieldProbMutation = new JTextField(PROB_MUTATION, TEXT_FIELD_LENGHT);
    //TODO - MORE PARAMETERS?
    private MainFrame mainFrame;

    public PanelParameters(MainFrame mainFrame) {
        title = "Genetic algorithm parameters";
        this.mainFrame = mainFrame;

        labels.add(new JLabel("Seed: "));
        valueComponents.add(textFieldSeed);
        textFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Population size: "));
        valueComponents.add(textFieldN);
        textFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(textFieldGenerations);
        textFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(comboBoxSelectionMethods);
        comboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));


        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(textFieldTournamentSize);
        textFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(comboBoxRecombinationMethods);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(textFieldProbRecombination);

        labels.add(new JLabel("Selection mutation: "));
        valueComponents.add(comboBoxSelectionMutation);

        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(textFieldProbMutation);

        //TODO - MORE PARAMETERS?
        labels.add(new JLabel("Selection Algorithm: "));

        valueComponents.add(comboBoxSelectionAlgorithm);
        comboBoxSelectionAlgorithm.addActionListener(new JComboBoxSelectionAlgorithm_ActionAdapter(this));
//        comboBoxSelectionAlgorithm.addActionListener(actionListenerSelectionAlgorithm);

        configure();

    }

//    ActionListener actionListenerSelectionAlgorithm = new ActionListener() {
//        public void actionPerformed(ActionEvent actionEvent) {
//            algorithmSelected = comboBoxSelectionAlgorithm.getSelectedIndex();
//            if (algorithmSelected < 2)
//
//        }
//    };

    //TODO: kevin -add
    public SnakeType getAlgorithmSelected() {
        switch (comboBoxSelectionAlgorithm.getSelectedIndex()){
            case 0:
                return SnakeType.ADOC;
            case 1:
                return SnakeType.RANDOM;
            case 2:
                return SnakeType.AI1;
            case 3:
                return SnakeType.AI2;
            case 4:
                return SnakeType.TWO_AI_EQUAL;
            case 5:
                return SnakeType.TWO_AI_DIF;
        }
        return SnakeType.ADOC;//nunca chega ca
    }


    public void actionPerformedSelectionMethods(ActionEvent e) {
        textFieldTournamentSize.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 0);
    }


    //Atualização da combobox relativa ao algoritmo de seleção
    public void actionPerformedSelectionAlgorithm(ActionEvent e){
        if (comboBoxSelectionAlgorithm.getSelectedIndex() >= 2){
            mainFrame.manageButtons(true, false, false, true, false,true, false, false);
        }else{
            mainFrame.createProblem();
            mainFrame.manageButtons(false, false, false,false, false, false, false, true);
        }

    }

    public SelectionMethod<SnakeIndividual, SnakeProblem> getSelectionMethod() {

        switch (comboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament<>(Integer.parseInt(textFieldN.getText()), Integer.parseInt(textFieldTournamentSize.getText()));
            case 1:
                return new RouletteWheel<>(Integer.parseInt(textFieldN.getText()));

        }
        return null;
    }


    public Recombination<SnakeIndividual> getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(textFieldProbRecombination.getText());

        switch (comboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationOneCut<>(recombinationProb);
            case 1:
                return new RecombinationTwoCuts<>(recombinationProb);
            case 2:
                return new RecombinationUniform<>(recombinationProb);
        }
        return null;
    }

    public Mutation<SnakeIndividual> getMutationMethod() {
        double mutationProbability = Double.parseDouble(textFieldProbMutation.getText());
        //TODO

        switch (comboBoxSelectionMutation.getSelectedIndex()){
            case 0:
                return new MutationMUTATION_NAME<>(mutationProbability/*TODO?*/);
            case 1:
                return new MutationMUTATION_BASIC<>(mutationProbability/*TODO?*/);
            case 2:
                return new MutationMUTATION_NAME<>(mutationProbability/*TODO?*/);
        }
        return null;
    }


}

class JComboBoxSelectionMethods_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}


class JComboBoxSelectionAlgorithm_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionAlgorithm_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionAlgorithm(e);
    }
}



class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
}
