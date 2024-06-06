import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticsPanel extends JPanel {
    private Map<Integer, JLabel> typeCountLabels;
    private Font labelFont;

    public StatisticsPanel() {
        Color gray = new Color(40,60,70);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        typeCountLabels = new HashMap<>();
        labelFont = new Font("Arial", Font.PLAIN, 20); // начальный шрифт
        setBackground(gray);
    }

    public void updateStatistics(Map<Integer, Integer> typeCounts) {
        removeAll();
        Color white = new Color(235,235,235);
        JLabel title = new JLabel("Statistics:");
        title.setForeground(white);
        title.setFont(labelFont);
        add(title);
        typeCounts.forEach((type, count) -> {
            JLabel label = typeCountLabels.computeIfAbsent(type, k -> new JLabel());
            label.setText("Type " + type + ": " + count);
            label.setFont(labelFont);
            label.setForeground(white);
            add(label);
        });
        revalidate();
        repaint();
    }
}
