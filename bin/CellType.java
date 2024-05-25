import java.awt.Color;

public enum CellType {
    TYPE1(Color.RED,"textures/seaweed[texture].png", 10, 5.0),
    TYPE2(Color.BLUE,"textures/seaweed[texture].png", 15, 7.0);

    // Создаем константы для хранения типов с заданными параметрами
    private final Color color;
    private final String texturePath;
    private final int size;
    private final double standartSpeed;
    // Конструктор
    CellType(Color color, String texturePath, int size, double standartSpeed) {
        this.color = color;
        this.texturePath = texturePath;
        this.size = size;
        this.standartSpeed = standartSpeed;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
}