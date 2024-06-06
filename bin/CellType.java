

public class CellType {
    // Создаем константы для хранения типов с заданными параметрами
    private final String texturePath;
    private final int size;
    private final int index;
    // Конструктор
    CellType(String texturePath, int size, int i) {
        this.texturePath = texturePath;
        this.size = size;
        this.index = i;
    }

    public int getSize() {
        return size;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public int getIndex() {
        return index;
    }
}