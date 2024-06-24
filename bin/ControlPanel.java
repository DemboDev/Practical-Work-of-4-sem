import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JSlider Slider;
    private JLabel Label;

    public ControlPanel(String name, int min, int max, int start) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Используем BoxLayout

        setLayout(new GridLayout(2, 1));
        Label = new JLabel(name);
        Slider = new JSlider(min, max, start);
        Slider.setBackground(Color.DARK_GRAY);
        setBackground(Color.DARK_GRAY);
        Label.setForeground(Color.WHITE);
        add(Label);
        add(Slider);
    }

    public int getValue() {
        return Slider.getValue();
    }

    public JSlider getSlider() {
        return Slider;
    }
}
