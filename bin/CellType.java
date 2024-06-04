import java.awt.Color;

public class CellType {
    // Создаем константы для хранения типов с заданными параметрами
    private final String texturePath;
    private final int size;
    // Конструктор
    CellType(String texturePath, int size) {
        this.texturePath = texturePath;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getTexturePath() {
        return texturePath;
    }
}