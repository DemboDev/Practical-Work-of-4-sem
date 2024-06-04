import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class SimulationFrame extends JFrame {
    private ParticlePanel particlePanel;
    private ControlPanel panelGravity, panelFrequency;
    private ControlField panelField;
    private ControlButton btnPause, btnShow, btnRestart;
    private final int panelWidth = 1300, panelHeight = 600;
    private double G = 2;
    private int frequency = 100;
    private boolean isPaused = false, isShowed = true;

    public void moveNearID(ParticlePanel particlePanel){
        for (int i = 0; i < particlePanel.getParticles().size(); ++i){
            CellType fType = particlePanel.getParticles().get(i).getType();
            int minCellIterator = i;
            double minRadius = 0;
            for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                CellType sType = particlePanel.getParticles().get(j).getType();
                if (Objects.equals(fType, sType) && i != j) {
                    double cellsRadX = (particlePanel.getParticles().get(j).getX() - particlePanel.getParticles().get(i).getX());
                    double cellsRadY = (particlePanel.getParticles().get(j).getY() - particlePanel.getParticles().get(i).getY());
                    double radius = Math.sqrt(cellsRadX * cellsRadX + cellsRadY * cellsRadY);
                    if (minRadius > radius || minRadius == 0)  {
                        minRadius = radius;
                        minCellIterator = j;
                    }
                }
            }
            if (minRadius != 0) {
                double cellsRadX = (particlePanel.getParticles().get(minCellIterator).getX() - particlePanel.getParticles().get(i).getX());
                double cellsRadY = (particlePanel.getParticles().get(minCellIterator).getY() - particlePanel.getParticles().get(i).getY());
                particlePanel.getParticles().get(i).setVx((particlePanel.getParticles().get(i).getVx() + ((G / minRadius) * cellsRadX)) / 2);
                particlePanel.getParticles().get(i).setVy((particlePanel.getParticles().get(i).getVy() + ((G / minRadius) * cellsRadY)) / 2);
            }
        }
    }

    public void moveToBorder(ParticlePanel particlePanel){
        for (int i = 0; i < particlePanel.getParticles().size(); ++i){
            if (particlePanel.getParticles().get(i).getX() < 1) {
                particlePanel.getParticles().get(i).setX(1);
                particlePanel.getParticles().get(i).changeDirectionX();
            }
            if (particlePanel.getParticles().get(i).getY() < 1) {
                particlePanel.getParticles().get(i).setY(1);
                particlePanel.getParticles().get(i).changeDirectionY();
            }
            if (particlePanel.getParticles().get(i).getX() >= panelWidth) {
                if (particlePanel.getParticles().get(i).getX() > panelWidth) {
                    particlePanel.getParticles().get(i).setX(panelWidth - 1);
                }
                particlePanel.getParticles().get(i).changeDirectionX();
            }
            if (particlePanel.getParticles().get(i).getY() >= panelHeight) {
                if (particlePanel.getParticles().get(i).getY() > panelHeight) {
                    particlePanel.getParticles().get(i).setY(panelHeight - 1);
                }
                particlePanel.getParticles().get(i).changeDirectionY();
            }
        }
    }

    public SimulationFrame(int initialParticles, int initialTypes) {
        setTitle("Simulation of the Life");
        setSize(panelWidth, panelHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        particlePanel = new ParticlePanel();

        JPanel showPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel settingsPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        btnRestart = new ControlButton("RESTART");
        btnShow = new ControlButton("HIDE");
        btnPause = new ControlButton("PAUSE");
        panelGravity = new ControlPanel("Gravity", 1, 300, 180);
        panelFrequency = new ControlPanel("Frequency", 10, 900, 100);
        panelField = new ControlField("Add Cells");

        topPanel.add(btnPause);
        topPanel.add(btnRestart);

        showPanel.add(btnShow);

        bottomPanel.add(panelField);
        bottomPanel.add(panelGravity);
        bottomPanel.add(panelFrequency);

        settingsPanel.add(bottomPanel);
        settingsPanel.add(showPanel);

        add(particlePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.SOUTH);

        //.................................................................
        // Определение типов частиц
        //.................................................................
        java.util.List<CellType> type = new ArrayList<>();
        for (int i = 1; i <= initialTypes; ++i) {

            type.add(new CellType("textures/seaweed" + i + "[texture].png", 100));
        }
        setTitle(Integer.toString(initialParticles));
        // Добавляем начальные частицы
        for (int i = 0; i < initialParticles; ++i){

            for (int j = 0; j < initialTypes; ++j) {
                particlePanel.addParticle(new Cell(type.get(j)));
            }
        }

        frequency = panelFrequency.getValue();
        // Передвижение клеток
        Timer controlUpdateTimer = new Timer(frequency, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                G = (double) panelGravity.getValue() / 10;
                moveNearID(particlePanel);
                moveToBorder(particlePanel);
            }
        });
        controlUpdateTimer.start();

        // Добавляем обработчик для кнопки "Restart"
        btnRestart.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartWindow().setVisible(true);
                SimulationFrame.this.dispose();
            }
        });

        btnPause.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    for (Cell p : particlePanel.getParticles()){
                        p.cont();
                    }
                    controlUpdateTimer.start();
                    btnPause.getButton().setText("PAUSE");
                } else {
                    for (Cell p : particlePanel.getParticles()){
                        p.stop();
                    }
                    controlUpdateTimer.stop();
                    btnPause.getButton().setText("RESUME");
                }
                isPaused = !isPaused;
            }
        });
        btnShow.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isShowed) {
                    btnShow.getButton().setText("HIDE");
                    isShowed = true;
                    bottomPanel.setVisible(isShowed);
                }
                else {
                    btnShow.getButton().setText("SHOW");
                    isShowed = false;
                    bottomPanel.setVisible(isShowed);
                }
            }
        });
        // Добавление обработчика для кнопки "OK"
        panelField.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numParticles = Integer.parseInt(panelField.getNumberInput().getText());
                    int maxParticles = 600;
                    if (numParticles <= 200) {
                        if (particlePanel.getParticles().size() <= maxParticles) {
                            if (particlePanel.getParticles().size() + numParticles < maxParticles) {
                                for (int i = 0; i < numParticles; i++) {
                                    double x = Math.random() * particlePanel.getWidth();
                                    double y = Math.random() * particlePanel.getHeight();
                                    CellType localType = type.get((int) (Math.random() * initialTypes));
                                    particlePanel.addParticle(new Cell(x, y, localType));
                                    if (isPaused){
                                        particlePanel.getParticles().getLast().stop();
                                    }
                                }
                            }
                            else {
                                for (int i = 0; i < maxParticles - numParticles; i++) {
                                    double x = Math.random() * particlePanel.getWidth();
                                    double y = Math.random() * particlePanel.getHeight();
                                    CellType localType = type.get((int) (Math.random() * initialTypes));
                                    particlePanel.addParticle(new Cell(x, y, localType));
                                    if (isPaused){
                                        particlePanel.getParticles().getLast().stop();
                                    }
                                }
                            }

                        }
                        else {
                            JOptionPane.showMessageDialog(SimulationFrame.this, "Particle limit reached", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(SimulationFrame.this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SimulationFrame.this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Добавляем слушатель изменений для слайдера частоты обновления
        panelFrequency.getSlider().addChangeListener(e -> {
            int newFrequency = panelFrequency.getValue();
            controlUpdateTimer.setDelay(newFrequency);
        });

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartWindow().setVisible(true);
        });
    }
}