package gui;

import snake.Environment.Environment;
import snake.Environment.EnvironmentTwoSnake;
import snake.EnvironmentListener;
import snake.SnakeAgent;
import snake.SnakeType;
import sun.management.Agent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
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
    JPanel container = new JPanel();
    JSeparator separator1;
    JSeparator separator2;
    JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private SwingWorker worker;

    public PanelSimulation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        environmentPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        container.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE+1000));
        setLayout(new BorderLayout());
        add(btnPnl, java.awt.BorderLayout.SOUTH);
        btnPnl.add(buttonSimulate);
        btnPnl.add(buttonStop);
        buttonStop.setEnabled(false);
        JScrollPane scroller = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) ;
        setPreferredSize(new Dimension(300, 500));

        add(environmentPanel, java.awt.BorderLayout.NORTH);
        add(scroller, BorderLayout.CENTER);

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

        iniciarDados();


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
            iniciarDadosCorreto();

            environment.addEnvironmentListener(this);

            buildImage(environment);

            final PanelSimulation simulationPanel = this;


            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    int environmentSimulations = mainFrame.getProblem().getNumEvironmentSimulations();

                    SnakeType snakeType = mainFrame.getPanelParameters().getAlgorithmSelected();

                    //TODO VLOLTAR A VER: voltar ca que devem estar coisas a mais
                    if (snakeType != SnakeType.ADOC && snakeType != SnakeType.RANDOM){
                        environment.getAgentAI().setWeights(mainFrame.getBestInRun().getGenome());
                    }
                    if( snakeType == SnakeType.TWO_AI_EQUAL ){
                        ((EnvironmentTwoSnake)environment).getSnakeAIAgent2().setWeights(mainFrame.getBestInRun().getGenome());

                    }else if(snakeType == SnakeType.TWO_AI_DIF){
                        int sizeDivision = ((EnvironmentTwoSnake)environment).getSizeDivision();
                        ((EnvironmentTwoSnake)environment).getSnakeAIAgent1().setWeights(Arrays.copyOfRange(mainFrame.getBestInRun().getGenome(), sizeDivision, mainFrame.getBestInRun().getGenome().length));
                    }

                    container.removeAll();
                    for (int i = 0; i < environmentSimulations; i++) {
                        environment.initialize(i);
                        environmentUpdated();
                        environment.simulate();

                        iniciarDados();
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

    private void iniciarDadosCorreto() {
        //TODO
        SnakeType snakeType = mainFrame.getPanelParameters().getAlgorithmSelected();

        snakeType = snakeType == SnakeType.TWO_AI_EQUAL || snakeType == SnakeType.TWO_AI_DIF? SnakeType.AI1 : snakeType;

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

        //Todo
    }

    private void iniciarDados(){
        //TODO
        separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2 = new JSeparator(SwingConstants.HORIZONTAL);

        separator1.setPreferredSize(new Dimension(PANEL_SIZE, 1));
        separator2.setPreferredSize(new Dimension(PANEL_SIZE, 1));

        //Sanke 1
        labelSegundaria = new JLabel("Dados Snake 1 ");
        container.add(labelSegundaria);
        container.add(separator1);

        SnakeAgent agent = mainFrame.getProblem().getEnvironment().getAgent();


        totalComidas = new JLabel("Comidas: " +  agent.getTotalFood()
                + " | " + "Movimentos: " + agent.getTotalMovimentos());

        container.add(totalComidas );

        container.add(separator2);

        SnakeType snakeType = mainFrame.getPanelParameters().getAlgorithmSelected();

        //Sanke 2 (if)
        if(snakeType == SnakeType.TWO_AI_EQUAL || snakeType == SnakeType.TWO_AI_DIF ){

            separator1 = new JSeparator(SwingConstants.HORIZONTAL);
            separator2 = new JSeparator(SwingConstants.HORIZONTAL);
            separator1.setPreferredSize(new Dimension(PANEL_SIZE, 1));
            separator2.setPreferredSize(new Dimension(PANEL_SIZE, 1));

            labelSegundaria = new JLabel("Dados Snake 2 ");
            container.add(labelSegundaria);
            container.add(separator1);

            SnakeAgent agent2 = snakeType == SnakeType.TWO_AI_EQUAL?
                    ((EnvironmentTwoSnake)mainFrame.getProblem().getEnvironment()).getSnakeAIAgent2():
                    ((EnvironmentTwoSnake)mainFrame.getProblem().getEnvironment()).getSnakeAIAgent1();



            totalComidas = new JLabel("Média Comidas: " + agent2.getTotalFood() +
                    " | " + "Média Movimentos: " + agent2.getTotalMovimentos());

            container.add(totalComidas );
            container.add(separator2);
        }
        container.setVisible(false);
        container.setVisible(true);

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
