import javax.swing.*;
import java.awt.*;

public class ControlField extends JPanel {
    private JLabel Label;
    private JTextField numberInput;
    private JButton addButton;

    public ControlField(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Используем BoxLayout

        setLayout(new GridLayout(1, 1));
        Label = new JLabel(name);
        numberInput = new JTextField(10);
        addButton = new JButton("OK");
        addButton.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel();

        JLabel text = new JLabel("Number of Cells:");
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setForeground(Color.WHITE);
        inputPanel.add(text);
        inputPanel.setForeground(Color.WHITE);
        inputPanel.add(numberInput);
        inputPanel.add(addButton);

        addButton.setBorderPainted(false); // Убирает бордюр кнопки
        addButton.setFocusPainted(false); // Убирает окантовку при фокусировке
        addButton.setContentAreaFilled(false); // Убирает внутреннюю область кнопки, делает фон цветным
        addButton.setOpaque(true); // Дает возможность менять фон кнопки

        inputPanel.setBackground(Color.darkGray);
        setForeground(Color.WHITE);
        add(inputPanel);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getNumberInput() {
        return numberInput;
    }
}
