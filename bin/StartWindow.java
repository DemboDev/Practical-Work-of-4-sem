import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame {
    private JTextField numParticlesField;
    private JTextField numTypesField;
    private JButton startButton;

    public StartWindow() {
        setTitle("Start Simulation");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Number of Particles:"));
        numParticlesField = new JTextField("10");
        add(numParticlesField);

        add(new JLabel("Number of Types:"));
        numTypesField = new JTextField("2");
        add(numTypesField);

        startButton = new JButton("Start");
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numParticles = Integer.parseInt(numParticlesField.getText());
                int numTypes = Integer.parseInt(numTypesField.getText());
                new SimulationFrame(numParticles, numTypes).setVisible(true);
                StartWindow.this.dispose();
            }
        });
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartWindow().setVisible(true);
        });
    }
}
