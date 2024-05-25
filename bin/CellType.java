import java.awt.Color;

public enum CellType {
    /*
    Описание типов клеток:
    ТИП1: Коренные клетки, вокруг них идёт образование новых клеток. Являются определяющими при формировании колонии.
    ТИП2: Простые клетки, вокруг них не собираются другие, но Они сами стремятся к соединению с клеткой типа 1. Привязаны к конкретной клетке
     */
    TYPE1(Color.RED,"textures/seaweed[texture].png", 10, 5.0),
    TYPE2(Color.BLUE,"textures/seaweed[texture].png", 15, 7.0);

    // Создаем константы для хранения типов с заданными параметрами
    private final Color color;
    private int ID;
    private final String texturePath;
    private final int size;
    // Конструктор
    CellType(Color color, String texturePath, int size, double standartSpeed) {
        this.color = color;
        this.texturePath = texturePath;
        this.size = size;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
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