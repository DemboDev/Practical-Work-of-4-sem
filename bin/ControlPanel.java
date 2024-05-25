import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JSlider speedSlider;
    private JLabel speedLabel;

    public ControlPanel() {
        setLayout(new GridLayout(2, 1));
        speedLabel = new JLabel("Speed");
        speedSlider = new JSlider(1, 50, 1);

        add(speedLabel);
        add(speedSlider);
    }

    public int getSpeed() {
        return speedSlider.getValue();
    }
}
