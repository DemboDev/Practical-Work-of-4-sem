import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
// Карта где будут взаимодействовать частицы-клетки
public class ParticlePanel extends JPanel {
    private List<Cell> particles = new ArrayList<>(); // массив
    private Timer timer; // таймер для обновления положения частиц

    public List<Cell> getParticles() {
        return particles;
    }

    public ParticlePanel() { // сама панель
        setBackground(Color.WHITE); // закрашиваем фон в белый цвет
        timer = new Timer(16, e -> { // обновляем положение частиц каждый тик
            for (Cell p : particles) {
                p.update();
            }
            repaint();
        });
        timer.start();
    }

    public void addParticle(Cell particle) {
        particles.add(particle);
    } // функция для добавления частиц

    // отрисовка частиц
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Cell p : particles) {
            p.draw(g);
        }
    }
}
