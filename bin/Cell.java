import java.awt.Color;

public class Cell {
    // Координаты клетки
    private double x, y;
    // Вектор перемещения клетки
    private double vx, vy;
    // Тип клетки реализован через строку
    private CellType type;
    // Переменная задаёт максимальную скорость перемещения (возможны доработки)
    private static final double MAX_SPEED = 5.0;
    // Создание клетки (конструктор)
    public Cell(double x, double y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        // случайная начальная скорость
        this.vx = (Math.random() - 0.5) * 2 * MAX_SPEED;
        this.vy = (Math.random() - 0.5) * 2 * MAX_SPEED;
    }
    // обновление перемещения
    public void update() {
        x += vx;
        y += vy;
    }
    // отрисовка клетки (возможны доработки)
    public void draw() {
    }
    // Получение параметров при необходимости
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public CellType getType() { return type; }
    // Установка векторов перемещения клетки
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
}
