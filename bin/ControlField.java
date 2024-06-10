import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ControlField extends JPanel {
    private final JLabel label;
    private JTextField numberInput;
    private JButton addButton;
    private List<JRadioButton> typeRBtn = new ArrayList<>();

    public ControlField(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Используем BoxLayout

        Color darkGray = new Color(30, 50, 60);
        Color gray = new Color(40, 60, 70);
        Color white = new Color(235, 235, 235);

        label = new JLabel(name);
        label.setForeground(white);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(darkGray);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        JLabel particleTypesLabel = new JLabel("Select particle types:");
        particleTypesLabel.setForeground(white);
        particleTypesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(particleTypesLabel);

        ButtonGroup typeGroup = new ButtonGroup();
        for (int i = 0; i < 5; ++i) {
            JRadioButton tempRBtn = new JRadioButton("Type " + (i + 1));
            tempRBtn.setBackground(darkGray);
            tempRBtn.setForeground(white);
            typeRBtn.add(tempRBtn);
            typeGroup.add(tempRBtn);
            inputPanel.add(tempRBtn);
        }

        JRadioButton randomRBtn = new JRadioButton("Random");
        randomRBtn.setBackground(darkGray);
        randomRBtn.setForeground(white);
        typeRBtn.add(randomRBtn);
        typeGroup.add(randomRBtn);
        inputPanel.add(randomRBtn);

        JLabel numberInputLabel = new JLabel("Number of Cells:");
        numberInputLabel.setForeground(white);
        numberInputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(numberInputLabel);

        numberInput = new JTextField(10);
        numberInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, numberInput.getPreferredSize().height));
        inputPanel.add(numberInput);

        addButton = new JButton("OK");
        addButton.setBackground(white);
        addButton.setForeground(darkGray);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(addButton);

        add(inputPanel);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public int getChoice() {
        int selectedType = -1;
        for (int i = 0; i < typeRBtn.size(); ++i) {
            if (typeRBtn.get(i).isSelected()) {
                selectedType = i;
                break;
            }
        }
        return selectedType;
    }

    public JTextField getNumberInput() {
        return numberInput;
    }
}
