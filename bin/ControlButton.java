import javax.swing.*;
import java.awt.*;

public class ControlButton extends JPanel {
    private JLabel Label;
    private JButton Button;

    public ControlButton(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Используем BoxLayout

        setLayout(new GridLayout(1, 1));
        Label = new JLabel(name);

        Button = new JButton(name);

        JPanel inputPanel = new JPanel();
        inputPanel.add(Button);

        add(inputPanel);
    }

    public JButton getButton() {
        return Button;
    }
}
