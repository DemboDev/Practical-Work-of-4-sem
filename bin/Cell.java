import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Cell {
    private Image texture;
    // Координаты клетки
    private double x, y;
    // Вектор перемещения клетки
    private double vx, vy;
    private int ID = 0;
    private double startVx, startVy;
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
        this.texture = new ImageIcon(getClass().getResource(type.getTexturePath())).getImage();
        startVx = this.vx = (Math.random() - 0.5) * 2 * MAX_SPEED;
        startVy = this.vy = (Math.random() - 0.5) * 2 * MAX_SPEED;
    }
    public Cell(CellType type) {
        this.x = (Math.random()) * 1500;
        this.y = (Math.random()) * 800;
        this.type = type;
        // случайная начальная скорость и положение
        this.texture = new ImageIcon(getClass().getResource(type.getTexturePath())).getImage();
        startVx = this.vx = (Math.random() - 0.5) * 2 * MAX_SPEED;
        startVy = this.vy = (Math.random() - 0.5) * 2 * MAX_SPEED;
    }
    public void changeDirectionX() { this.startVx *= -1;}
    public void changeDirectionY() { this.startVy *= -1;}
    // обновление перемещения
    public void update() {
        x += vx;
        y += vy;
    }
    // отрисовка клетки (возможны доработки)
    public void draw(Graphics g) {
        g.drawImage(texture, (int)x, (int)y, type.getSize(), type.getSize(), null);
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
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
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
