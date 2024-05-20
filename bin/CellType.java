import java.awt.Color;

public class CellType {
    // Создаем константы для хранения типов с заданными параметрами
    private final Color color;
    private final int size;
    // Конструктор
    CellType(Color color, int size) {
        this.color = color;
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
}