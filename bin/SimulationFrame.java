import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SimulationFrame extends JFrame {
    private ParticlePanel particlePanel;
    private ControlPanel panelGravity, panelFrequency;
    private ControlField panelField;
    private final int panelWidth = 1500, panelHeight = 800;
    private final double orbit = 100;
    private double G = 2;
    private int frequency = 100;

    public void moveNearID(ParticlePanel particlePanel){
        for (int i = 0; i < particlePanel.getParticles().size(); ++i){
            String firstType = particlePanel.getParticles().get(i).getType().getColor().toString();
            int minCellIterator = i;
            double minRadius = 0;
            for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                String secondType = particlePanel.getParticles().get(j).getType().getColor().toString();
                if (Objects.equals(firstType, secondType) && i != j) {
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

    public SimulationFrame() {
        setTitle("Simulation of the Life");
        setSize(panelWidth, panelHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        particlePanel = new ParticlePanel();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        panelGravity = new ControlPanel("Gravity", 1, 300, 30);
        panelFrequency = new ControlPanel("Frequency", 10, 900, 100);
        panelField = new ControlField("Add Cells");

        bottomPanel.add(panelField);
        bottomPanel.add(panelGravity);
        bottomPanel.add(panelFrequency);

        add(particlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);



        for (int i = 0; i < 66; i++){
            particlePanel.addParticle(new Cell(CellType.TYPE1));
        }
        // Добавляем начальные частицы
        for (int i = 0; i < 66; i++){
            particlePanel.addParticle(new Cell(CellType.TYPE2));
        }

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
                                    CellType type = (Math.random() < 0.5) ? CellType.TYPE1 : CellType.TYPE2;
                                    particlePanel.addParticle(new Cell(x, y, type));
                                }
                            }
                            else {
                                for (int i = 0; i < maxParticles - numParticles; i++) {
                                    double x = Math.random() * particlePanel.getWidth();
                                    double y = Math.random() * particlePanel.getHeight();
                                    CellType type = (Math.random() < 0.5) ? CellType.TYPE1 : CellType.TYPE2;
                                    particlePanel.addParticle(new Cell(x, y, type));
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

        // Добавляем слушатель изменений для слайдера частоты обновления
        panelFrequency.getSlider().addChangeListener(e -> {
            int newFrequency = panelFrequency.getValue();
            controlUpdateTimer.setDelay(newFrequency);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimulationFrame().setVisible(true);
        });
    }
}