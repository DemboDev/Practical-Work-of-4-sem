import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.*;

public class Cell {
    // Координаты клетки
    private double x, y;
    // Вектор перемещения клетки
    private double vx, vy, lastVX, lastVY;
    private int size;
    private CellType type;
    private Image texture;
    private int health;
    // Константа
    private final int differSize = 20;

    // Создание клетки (конструкторы)
    public Cell(double x, double y, CellType type) {
        this.x = x;
        this.y = y;
        this.health = 100;
        this.type = type;
        int s = (int) (Math.random() + 0.5) * differSize;
        this.size = type.getSize() + s;
        if (this.size < 50) {
            this.size = 50;
        }
        // случайная начальная скорость
        URL imageUrl = getClass().getResource(type.getTexturePath());
        if (imageUrl != null) {
            this.texture = new ImageIcon(imageUrl).getImage();
        }
        this.vx = (Math.random() - 0.5) * 2 * 10;
        this.vy = (Math.random() - 0.5) * 2 * 10;
    }
    public Cell(CellType type) {
        this.x = (Math.random()) * 1500;
        this.y = (Math.random()) * 800;
        this.health = 100;
        this.type = type;
        int s = (int) (Math.random() + 0.5) * differSize;
        this.size = Math.abs(this.type.getSize() + s);
        if (this.size < 50) {
            this.size = 50;
        }
        URL imageUrl = getClass().getResource(type.getTexturePath());
        if (imageUrl != null) {
            this.texture = new ImageIcon(imageUrl).getImage();
        }
        this.vx = (Math.random() - 0.5) * 2 * 10;
        this.vy = (Math.random() - 0.5) * 2 * 10;
    }

    // обновление положения
    public void update() {
        x += vx;
        y += vy;
    }
    // отрисовка клетки
    public void draw(Graphics g) {
        g.drawImage(this.texture, (int)x, (int)y, this.size, this.size, null);
    }

    // Функции для управления клетками
    public void stop(){
        setLastVX(getVx());
        setLastVY(getVy());
        setVx(0);
        setVy(0);
    }

    public void cont(){
        setVx(getLastVX());
        setVy(getLastVY());
    }

    // Получение параметров при необходимости
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getLastVX() { return lastVX; }
    public double getLastVY() { return lastVY; }
    public CellType getType() { return type; }
    public int getSize() { return size; }
    // Установка параметров при необходимости
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
    public void setLastVX(double lastVX) { this.lastVX = lastVX; }
    public void setLastVY(double lastVY) { this.lastVY = lastVY; }
    public void setSize(int size) { this.size = size; }

}
