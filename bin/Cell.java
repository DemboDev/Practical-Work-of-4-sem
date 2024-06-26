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

    // Создание клетки (конструкторы)
    public Cell(double x, double y, CellType type, int size) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.size = size;
        // случайная начальная скорость
        URL imageUrl = getClass().getResource(type.getTexturePath());
        if (imageUrl != null) {
            this.texture = new ImageIcon(imageUrl).getImage();
        }
        this.vx = (Math.random() - 0.5) * 2 * 10;
        this.vy = (Math.random() - 0.5) * 2 * 10;
    }
    public Cell(CellType type) {
        this.x = (Math.random()) * 1400;
        this.y = (Math.random()) * 730;
        this.type = type;
        int s = (int) (Math.random() + 0.5) * 20;
        this.size = Math.abs(this.type.getSize() + s);
        URL imageUrl = getClass().getResource(type.getTexturePath());
        if (imageUrl != null) {
            this.texture = new ImageIcon(imageUrl).getImage();
        }
        this.vx = (Math.random() - 0.5) * 2 * 10;
        this.vy = (Math.random() - 0.5) * 2 * 10;
    }
    public Cell(CellType type, int size) {
        this.x = (Math.random()) * 1400;
        this.y = (Math.random()) * 730;
        this.type = type;
        this.size = size;
        URL imageUrl = getClass().getResource(type.getTexturePath());
        if (imageUrl != null) {
            this.texture = new ImageIcon(imageUrl).getImage();
        }
        this.vx = 0;
        this.vy = 0;
    }
    // обновление положения
    public void update() {
        x += vx;
        y += vy;
    }
    // отрисовка клетки
    public void draw(Graphics g) {
        if (texture != null) {
            g.drawImage(this.texture, (int)x, (int)y, this.size, this.size, null);
        }
        else {
            URL imageUrl = getClass().getResource("textures/plant.png");
            if (imageUrl != null) {
                this.texture = new ImageIcon(imageUrl).getImage();
            }
            g.drawImage(this.texture, (int)x, (int)y, this.size, this.size, null);
        }
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

    public void getDamage(int damage) {
        this.size = (this.size - damage);
    }

    public void getFood(int damage) {
        this.size = (this.size + damage);
    }

    // Получение параметров при необходимости
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getLastVX() { return lastVX; }
    public double getLastVY() { return lastVY; }
    public CellType getType() { return type; }
    public int getSize() { return (int) size; }

    // Установка параметров при необходимости
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
    public void setLastVX(double lastVX) { this.lastVX = lastVX; }
    public void setLastVY(double lastVY) { this.lastVY = lastVY; }
    public void setSize(int size) { this.size = size; }
}
