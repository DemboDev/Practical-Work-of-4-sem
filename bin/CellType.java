import java.awt.Color;

public class CellType {
    /*
    Описание типов клеток:
    ТИП1: Коренные клетки, вокруг них идёт образование новых клеток. Являются определяющими при формировании колонии.
    ТИП2: Простые клетки, вокруг них не собираются другие, но Они сами стремятся к соединению с клеткой типа 1. Привязаны к конкретной клетке
     */

    // Создаем константы для хранения типов с заданными параметрами
    private final String texturePath;
    private final Color color;
    private final int size;
    // Конструктор
    CellType(Color color, String texturePath, int size) {
        this.color = color;
        this.texturePath = texturePath;
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public String getTexturePath() {
        return texturePath;
    }
}