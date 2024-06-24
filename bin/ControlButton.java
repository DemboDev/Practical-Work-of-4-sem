import javax.swing.*;
import java.awt.*;

public class ControlButton extends JPanel {
    private JButton Button;

    public ControlButton(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Используем BoxLayout

        setLayout(new GridLayout(1, 1));

        Button = new JButton(name);

        JPanel inputPanel = new JPanel();
        inputPanel.add(Button);

        Button.setBorderPainted(false); // Убирает бордюр кнопки
        Button.setFocusPainted(false); // Убирает окантовку при фокусировке

        setBackground(Color.WHITE);
        Button.setBackground(Color.WHITE);
        Button.setForeground(Color.BLACK);

        add(inputPanel);
    }

    public JButton getButton() {
        return Button;
    }
}
