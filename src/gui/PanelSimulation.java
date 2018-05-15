package gui;

import snake.Environment.Environment;
import snake.Environment.EnvironmentTwoSnake;
import snake.EnvironmentListener;
import snake.SnakeType;
import snake.snakeAI.nn.SnakeAIAgentSecond;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class PanelSimulation extends JPanel implements EnvironmentListener {

    public static final int PANEL_SIZE = 250;
    public static final int CELL_SIZE = 20;
    public static final int GRID_TO_PANEL_GAP = 20;
    MainFrame mainFrame;
    private Environment environment;
    private Image image;
    JPanel environmentPanel = new JPanel();
    final JButton buttonSimulate = new JButton("Simulate");
    final JButton buttonStop = new JButton("Stop");
    JLabel snakeOriginal;
    JLabel totalComidas;
    JLabel labelSegundaria;
    JSeparator separator;
    JSeparator separator2;
    JSeparator separator3;
    JPanel container = new JPanel();

    JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private SwingWorker worker;

    public PanelSimulation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        environmentPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        container.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));

        //Separador
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(PANEL_SIZE, 1));
        separator2.setPreferredSize(new Dimension(PANEL_SIZE, 1));
        separator3 = new JSeparator(SwingConstants.HORIZONTAL);
        separator3.setPreferredSize(new Dimension(PANEL_SIZE, 1));
        separator3.setBackground(Color.white);

        setLayout(new BorderLayout());
        add(btnPnl, java.awt.BorderLayout.SOUTH);
        btnPnl.add(buttonSimulate);
        btnPnl.add(buttonStop);
        buttonStop.setEnabled(false);

        add(environmentPanel, java.awt.BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
//        add(buttonSimulate, java.awt.BorderLayout.SOUTH);
//        add(buttonStop, BorderLayout.CENTER);

        buttonSimulate.addActionListener(new SimulationPanel_jButtonSimulate_actionAdapter(this));

        buttonStop.addActionListener(new SimulationPanel_jButtonStop_actionAdapter(this));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setJButtonSimulateEnabled(boolean enabled) {
        buttonSimulate.setEnabled(enabled);
        if (enabled)
            setJButtonStopEnabled(false);
    }

    public void setJButtonStopEnabled(boolean enabled){
        buttonStop.setEnabled(enabled);
    }


    public void jButtonStop_actionPerformed(ActionEvent e){
//        if  (worker != null){
            buttonStop.setEnabled(false);

            SnakeType type = mainFrame.getPanelParameters().getAlgorithmSelected();
            if (type != SnakeType.ADOC && type != SnakeType.RANDOM){
                mainFrame.manageButtons(true, false, false, false, false, true, false, true);
            }else
                mainFrame.manageButtons(false, false, false, true, true, false, false, true);

            //TODO
            type = type == SnakeType.TWO_AI_EQUAL || type == SnakeType.TWO_AI_DIF? SnakeType.AI : type;

            iniciarDados(type);
            //TODO

            worker.cancel(true);
            environment.removeEnvironmentListener(this);
            worker = null;
//        }
    }

    public void jButtonSimulate_actionPerformed(ActionEvent e) {
        buttonStop.setEnabled(true);
        mainFrame.manageButtons(false, false, false, false, false, false, false, false);

        try {
            environment = mainFrame.getProblem().getEnvironment();

            //TODO
            SnakeType snakeType = mainFrame.getPanelParameters().getAlgorithmSelected();

            snakeType = snakeType == SnakeType.TWO_AI_EQUAL || snakeType == SnakeType.TWO_AI_DIF? SnakeType.AI : snakeType;

            //Reset
            environmentPanel.removeAll();

            //Cabeçalho
            snakeOriginal = new JLabel("Snake em Execução : " +   snakeType);
            environmentPanel.add(snakeOriginal, BorderLayout.NORTH);

            snakeOriginal.setVisible(false);
            snakeOriginal.setVisible(true);

            if( mainFrame.getPanelParameters().getAlgorithmSelected() !=  snakeType) {

                labelSegundaria = new JLabel("/ " + snakeType);
                environmentPanel.add(labelSegundaria, BorderLayout.NORTH);
            }

            iniciarDados(snakeType);

            //Todo

            environment.addEnvironmentListener(this);

            buildImage(environment);

            final PanelSimulation simulationPanel = this;


            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    int environmentSimulations = mainFrame.getProblem().getNumEvironmentSimulations();
                    SnakeType snakeType = mainFrame.getPanelParameters().getAlgorithmSelected();

                    //TODO: voltar ca que devem estar coisas a mais
                    if (snakeType == SnakeType.AI || snakeType == SnakeType.AI1){
                        mainFrame.getBestInRun().getGenome();
                        environment.getAgentAI().setWeights(mainFrame.getBestInRun().getGenome());
                    }
                    else if( snakeType == SnakeType.TWO_AI_EQUAL || snakeType == SnakeType.TWO_AI_DIF){
                        mainFrame.getBestInRun().getGenome();
                        environment.getAgentAI().setWeights(mainFrame.getBestInRun().getGenome());
                        ((EnvironmentTwoSnake)environment).getSnakeAIAgent1().setWeights(mainFrame.getBestInRun().getGenome());
                    }

                    SnakeType type =mainFrame.getPanelParameters().getAlgorithmSelected();

                    for (int i = 0; i < environmentSimulations; i++) {
                        //TODO:modified Kevin
                        environment.initialize(i,  type);
                        //penso que pode ser feito la em cima *(/\)*
//                        if (mainFrame.getPanelParameters().getAlgorithmSelected() == SnakeType.AI){
//                            environment.getAgentAI().setWeights(mainFrame.getBestInRun().getGenome());
//                        }
                        environmentUpdated();
                        environment.simulate();


                        snakeType = snakeType == SnakeType.TWO_AI_EQUAL || snakeType == SnakeType.TWO_AI_DIF? SnakeType.AI : snakeType;
                        iniciarDados(snakeType);
                    }

                    return null;
                }

                @Override
                public void done() {
                    environment.removeEnvironmentListener(simulationPanel);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ignore) {
                    }

                }
            };
            worker.execute();
        }catch (NullPointerException ex){
            JOptionPane.showMessageDialog(mainFrame, "Deve escolher um problema primeiro", "Error!", JOptionPane.ERROR_MESSAGE);

        }


    }

    private void iniciarDados(SnakeType snakeType){
        //TODO
        container.removeAll();

        //Sanke 1
        labelSegundaria = new JLabel("Dados Snake 1 ");
        container.add(labelSegundaria);
        container.add(separator);

        totalComidas = new JLabel("Comidas: " +  mainFrame.getProblem().getEnvironment().getAgent().getTotalFood() + " | " + "Movimentos: " + mainFrame.getProblem().getEnvironment().getAgent().getTotalMovimentos());

        container.add(totalComidas );

        container.add(separator3);

        //Sanke 2 (if)
        if( mainFrame.getPanelParameters().getAlgorithmSelected() !=  snakeType){

            labelSegundaria = new JLabel("Dados Snake 2 ");
            container.add(labelSegundaria);
            container.add(separator2);

            totalComidas = new JLabel("Comidas: " +  mainFrame.getProblem().getEnvironment().getAgentAI().getTotalFood() + " | " + "Movimentos: " + mainFrame.getProblem().getEnvironment().getAgentAI().getTotalMovimentos());

            container.add(totalComidas );
        }
        container.setVisible(false);
        container.setVisible(true);

        //TODO
    }


    public void buildImage(Environment environment) {
        image = new BufferedImage(
                environment.getSize() * CELL_SIZE + 1,
                environment.getSize() * CELL_SIZE + 1,
                BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void environmentUpdated() {
        int n = environment.getSize();
        Graphics g = image.getGraphics();

        //Fill the cells color
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                g.setColor(environment.getCellColor(y, x));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        //Draw the grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            g.drawLine(0, i * CELL_SIZE, n * CELL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, n * CELL_SIZE);
        }

        g = environmentPanel.getGraphics();
        g.drawImage(image, GRID_TO_PANEL_GAP, GRID_TO_PANEL_GAP, null);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {
        }
    }
}




//--------------------
class SimulationPanel_jButtonSimulate_actionAdapter implements ActionListener {

    final private PanelSimulation adaptee;

    SimulationPanel_jButtonSimulate_actionAdapter(PanelSimulation adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonSimulate_actionPerformed(e);
    }
}


class SimulationPanel_jButtonStop_actionAdapter implements ActionListener {

    final private PanelSimulation adaptee;

    SimulationPanel_jButtonStop_actionAdapter(PanelSimulation adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonStop_actionPerformed(e);
    }
}
