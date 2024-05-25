import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private ParticlePanel particlePanel;
    private ControlPanel controlPanel;
    private final int panelWidth = 1500, panelHeight = 800;
    private final double orbit = 100;
    private double G = 2;

    public void sortCells(ParticlePanel particlePanel){
        for (int numID = 0, i = 0; i < particlePanel.getParticles().size(); ++i){
            if (particlePanel.getParticles().get(i).getID() == 0 && particlePanel.getParticles().get(i).getType().getColor() == Color.RED) {
                particlePanel.getParticles().get(i).setID(++numID);
            }
            if (particlePanel.getParticles().get(i).getID() == 0 && particlePanel.getParticles().get(i).getType().getColor() == Color.BLUE) {
                double minRadius = 10000; int minID = 0;
                for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                    if (particlePanel.getParticles().get(j).getID() != 0) {
                        double cellsRadX = (particlePanel.getParticles().get(j).getX() - particlePanel.getParticles().get(i).getX());
                        double cellsRadY = (particlePanel.getParticles().get(j).getY() - particlePanel.getParticles().get(i).getY());
                        double radius = Math.sqrt(cellsRadX * cellsRadX + cellsRadY * cellsRadY);
                        if (radius < minRadius) {
                            minRadius = radius;
                            minID = particlePanel.getParticles().get(j).getID();
                        }
                    }
                }
                particlePanel.getParticles().get(i).setID(minID);
            }
        }
    }

    public void moveNearID(ParticlePanel particlePanel){
        for (int i = 0; i < particlePanel.getParticles().size(); ++i){
            int firstID = particlePanel.getParticles().get(i).getID();
            int minCellIterator = i;
            double minRadius = 0;
            for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                int secondID = particlePanel.getParticles().get(j).getID();
                if (firstID == secondID && i != j) {
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
        setTitle("Particle Simulation");
        setSize(panelWidth, panelHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        particlePanel = new ParticlePanel();
        controlPanel = new ControlPanel();

        add(particlePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        for (int i = 0; i < 3; i++){
            particlePanel.addParticle(new Cell(100, 100, CellType.TYPE1));
        }
        // Добавляем начальные частицы
        for (int i = 0; i < 40; i++){
            particlePanel.addParticle(new Cell(200, 150, CellType.TYPE2));
        }

        // Добавим распредееление клеток
        sortCells(particlePanel);

        Timer controlUpdateTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                G = (double) controlPanel.getSpeed() / 10;
                moveNearID(particlePanel);
            }
        });
        controlUpdateTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimulationFrame().setVisible(true);
        });
    }
}