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
    public SimulationFrame() {
        setTitle("Particle Simulation");
        setSize(panelWidth, panelHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        particlePanel = new ParticlePanel();
        controlPanel = new ControlPanel();

        add(particlePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        for (int i = 0; i < 1; i++){
            particlePanel.addParticle(new Cell(100, 100, CellType.TYPE1));
        }
        // Добавляем начальные частицы
        for (int i = 0; i < 20; i++){
            particlePanel.addParticle(new Cell(200, 150, CellType.TYPE2));
        }

        Timer controlUpdateTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                G = (double) controlPanel.getSpeed() / 10;
                for (int i = 0; i < particlePanel.getParticles().size(); ++i) {
                    double tempX = 0.0, tempY = 0.0;
                    if (particlePanel.getParticles().get(i).getType().getColor() == Color.BLUE) {
                        for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                            if (particlePanel.getParticles().get(j).getType().getColor() == Color.RED) {
                                double cellsRadX = (particlePanel.getParticles().get(j).getX() - particlePanel.getParticles().get(i).getX());
                                double cellsRadY = (particlePanel.getParticles().get(j).getY() - particlePanel.getParticles().get(i).getY());
                                double radius = Math.sqrt(cellsRadX * cellsRadX + cellsRadY * cellsRadY);
                                if (radius > 0) {
                                    tempX = ((G / radius) * cellsRadX);
                                    tempY = ((G / radius) * cellsRadY);
                                }
//                                if (radius < orbit && radius > orbit / 4) {
//                                    tempX = orbit - (particlePanel.getParticles().get(j).getX() - particlePanel.getParticles().get(i).getX()) * 4;
//                                    tempY = orbit - (particlePanel.getParticles().get(j).getY() - particlePanel.getParticles().get(i).getY()) * 4;
//                                }
//                                if (radius < orbit / 4) {
//                                    tempX -= orbit - (particlePanel.getParticles().get(j).getX() - particlePanel.getParticles().get(i).getX());
//                                    tempY -= orbit - (particlePanel.getParticles().get(j).getY() - particlePanel.getParticles().get(i).getY());
//                                }
                            }
                        }
                    }
                    if (particlePanel.getParticles().get(i).getType().getColor() == Color.BLUE) {
                        particlePanel.getParticles().get(i).setVx(particlePanel.getParticles().get(i).getVx() + tempX);
                        particlePanel.getParticles().get(i).setVy(particlePanel.getParticles().get(i).getVy() + tempY);
                    }
                    if (particlePanel.getParticles().get(i).getType().getColor() == Color.RED) {
                        tempX = (Math.random() - 0.5) * 5 * G;
                        tempY = (Math.random() - 0.5) * 5 * G;
                        particlePanel.getParticles().get(i).setVx((particlePanel.getParticles().get(i).getVx() + tempX) / 2);
                        particlePanel.getParticles().get(i).setVy((particlePanel.getParticles().get(i).getVy() + tempY) / 2);
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
        });
        controlUpdateTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimulationFrame().setVisible(true);
        });
    }
}