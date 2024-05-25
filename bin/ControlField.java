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

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Number of Cells:"));
        inputPanel.add(numberInput);
        inputPanel.add(addButton);

        add(inputPanel);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getNumberInput() {
        return numberInput;
    }
}
