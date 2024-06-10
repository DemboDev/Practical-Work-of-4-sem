import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimulationFrame extends JFrame {
    private final ParticlePanel particlePanel;
    private final ControlPanel panelGravity;
    private final ControlPanel panelFrequency;
    private final ControlPanel panelLimit;
    private final ControlPanel panelDamage;
    private final ControlPanel panelDPS;
    private final ControlPanel panelDPSFreq;
    private final ControlPanel panelFood;
    private final ControlPanel panelFoodFreq;
    private final ControlField panelField;
    private final ControlButton btnPause;
    private final ControlButton btnShow;
    private final ControlButton btnRestart;
    private StatisticsPanel statisticsPanel;
    private Clip clip;
    private final int limitCells = 1000;
    private final int panelWidth = 1400, panelHeight = 730;
    private double G = 2;
    private int limitDiv = 200;
    private boolean isPaused = false, isShowed = true;
    int countOfTick;
    int initTypes;
    private final CellType plant;

    //...........................................................................
    // Логика движения частиц
    //...........................................................................
    public void moveNearID(ParticlePanel particlePanel){
        for (int i = 0; i < particlePanel.getParticles().size(); ++i){
            CellType fType = particlePanel.getParticles().get(i).getType();
            if (fType != plant){
                int minCellIterator = i;
                double minRadius = 0;
                for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                    CellType sType = particlePanel.getParticles().get(j).getType();
                    if (Objects.equals(fType, sType) && i != j) {
                        double cellsRadX = (particlePanel.getParticles().get(j).getX() - particlePanel.getParticles().get(i).getX());
                        double cellsRadY = (particlePanel.getParticles().get(j).getY() - particlePanel.getParticles().get(i).getY());
                        double radius = Math.sqrt(cellsRadX * cellsRadX + cellsRadY * cellsRadY);
                        if (minRadius > radius || minRadius == 0) {
                            minRadius = radius;
                            minCellIterator = j;
                        }
                    }
                }
                if (minRadius != 0) {
                    double cellsRadX = (particlePanel.getParticles().get(minCellIterator).getX() - particlePanel.getParticles().get(i).getX());
                    double cellsRadY = (particlePanel.getParticles().get(minCellIterator).getY() - particlePanel.getParticles().get(i).getY());
                    particlePanel.getParticles().get(i).setVx((particlePanel.getParticles().get(i).getVx() + ((G / minRadius) * cellsRadX)) / 2);
                    particlePanel.getParticles().get(i).setVy((particlePanel.getParticles().get(i).getVy() + ((G / minRadius) * cellsRadY)) / 2);
                }
            }
        }
    }

    public void moveToBorder(ParticlePanel particlePanel){
        for (int i = 0; i < particlePanel.getParticles().size(); ++i){
            if (particlePanel.getParticles().get(i).getX() < 1) {
                particlePanel.getParticles().get(i).setX(1);
            }
            if (particlePanel.getParticles().get(i).getY() < 1) {
                particlePanel.getParticles().get(i).setY(1);
            }
            if (particlePanel.getParticles().get(i).getX() >= panelWidth) {
                if (particlePanel.getParticles().get(i).getX() > panelWidth) {
                    particlePanel.getParticles().get(i).setX(panelWidth - 1);
                }
            }
            if (particlePanel.getParticles().get(i).getY() >= panelHeight) {
                if (particlePanel.getParticles().get(i).getY() > panelHeight) {
                    particlePanel.getParticles().get(i).setY(panelHeight - 1);
                }
            }
        }
    }

    //...........................................................................
    // Логика взаимодействия частиц
    //...........................................................................
    private boolean isNear(Cell f, Cell s) {
        double whenNear;
        whenNear = (double) f.getSize() / 3 + (double) s.getSize() / 3;
        if (s.getType() == plant) {
            if (f.getY() < s.getY() && f.getX() < s.getX()){
                whenNear += (double) f.getSize() / 2;
            }
        }
        double cellsRadX = (f.getX() - s.getX());
        double cellsRadY = (f.getY() - s.getY());
        double radius = Math.sqrt(cellsRadX * cellsRadX + cellsRadY * cellsRadY);
        return (radius <= whenNear);
    }

    private boolean canEat(Cell predator, Cell prey) {
        return (predator.getSize() > prey.getSize() && predator.getType() != prey.getType() && predator.getType() != plant);
    }

    private void eating(Cell predator, Cell prey, java.util.List<Cell> toRemove, java.util.List<Cell> toAdd) {
        int damage = panelDamage.getValue();
        if (predator.getType() != plant) {
            if (isNear(predator, prey)) {
                if (canEat(predator, prey)) {
                    if (predator.getSize() < 400 && predator.getSize() < limitDiv + 15) {
                        predator.getFood(damage);
                    }
                    prey.getDamage(damage);
                    if (prey.getHealth() <= 0) {
                        toRemove.add(prey);
                    }
                }
            }
        }
    }

    private boolean isBig(Cell predator) {
        return predator.getSize() > limitDiv;
    }

    private void division(Cell predator, java.util.List<Cell> toAdd) {
        predator.setSize(predator.getSize() / 2);
        predator.setHealth(predator.getHealth() / 2);
        toAdd.add(new Cell(predator.getX(), predator.getY(), predator.getType(), predator.getSize()));
        playSound();
    }

    private void playSound() {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    private void updateParticles() {
        // Обновление статистики
        Map<Integer, Integer> typeCounts = new HashMap<>();
        for (Cell cell : particlePanel.getParticles()) {
            typeCounts.put(cell.getType().getIndex(), typeCounts.getOrDefault(cell.getType().getIndex(), 0) + 1);
        }
        statisticsPanel.updateStatistics(typeCounts);
    }

    //...........................................................................
    // Основная функция
    //...........................................................................
    public SimulationFrame(int initialParticles, int initialTypes) {
        setTitle("Simulation of the Life");
        setSize(panelWidth, panelHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("Sounds/division.wav")));
            clip.open(inputStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        Color darkGray = new Color(30,50,60);
        Color gray = new Color(40,60,70);
        Color white = new Color(235,235,235);

        particlePanel = new ParticlePanel();
        statisticsPanel = new StatisticsPanel();

        JPanel topMainPanel = new JPanel();
        JPanel showPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel settingsPanel = new JPanel();
        topMainPanel.setLayout(new BoxLayout(topMainPanel, BoxLayout.X_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        btnRestart = new ControlButton("RESTART");
        btnShow = new ControlButton("HIDE");
        btnPause = new ControlButton("PAUSE");
        panelGravity = new ControlPanel("Gravity", 1, 300, 90);
        panelFrequency = new ControlPanel("Frequency", 10, 900, 100);
        panelLimit = new ControlPanel("Limit", 50, 300, 200);
        panelDamage = new ControlPanel("Damage of Cells", 1, 50, 15);
        panelDPS = new ControlPanel("Damage per Seconds", 1, 10, 2);
        panelDPSFreq = new ControlPanel("DPS Frequency", 1, 50, 10);
        panelFoodFreq = new ControlPanel("Frequency of spawn food", 1, 300, 100);
        panelFood = new ControlPanel("Count of spawn food", 1, 100, 25);
        panelField = new ControlField("Add Cells");

        topPanel.add(btnPause);
        topPanel.add(btnRestart);
        showPanel.add(btnShow);

        bottomPanel.add(panelField);
        bottomPanel.add(panelGravity);
        bottomPanel.add(panelLimit);
        bottomPanel.add(panelDamage);
        bottomPanel.add(panelDPS);
        bottomPanel.add(panelDPSFreq);
        bottomPanel.add(panelFood);
        bottomPanel.add(panelFoodFreq);
        bottomPanel.add(panelFrequency);

        bottomPanel.setBackground(darkGray);
        settingsPanel.setBackground(darkGray);
        showPanel.setBackground(darkGray);
        topPanel.setBackground(darkGray);

        settingsPanel.add(bottomPanel);
        settingsPanel.add(showPanel);
        topMainPanel.add(topPanel);

        add(particlePanel, BorderLayout.CENTER);
        add(topMainPanel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.SOUTH);
        add(statisticsPanel, BorderLayout.EAST);

        //.................................................................
        // Создание частиц
        //.................................................................
        initTypes = initialTypes;
        java.util.List<CellType> type = new ArrayList<>();
        for (int i = 1; i <= 5; ++i) {
            type.add(new CellType("textures/seaweed" + i + "[texture].png",100, i));
        }
        plant = new CellType("textures/plant.png",10, 0);
        type.add(plant);
        // Добавляем начальные частицы
        for (int i = 0; i < initialParticles; ++i){
            for (int j = 0; j < initialTypes; ++j) {
                particlePanel.addParticle(new Cell(type.get(j)));
            }
        }
        for (int i = 0; i < 25; ++i) {
            particlePanel.getParticles().add(new Cell(plant, 15));
            particlePanel.getParticles().getLast().setVx(0);
            particlePanel.getParticles().getLast().setVy(0);
        }
        //...........................................................................
        // Слушатели обновлений
        //...........................................................................
        int frequency = panelFrequency.getValue();
        // Передвижение клеток
        Timer controlUpdateTimer = new Timer(frequency, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limitDiv = panelLimit.getValue();
                updateParticles();
                countOfTick += 1;
                java.util.List<Cell> toAdd = new ArrayList<>();
                java.util.List<Cell> toRemove = new ArrayList<>();
                if (countOfTick % 100 == 0)
                    countOfTick -= 100;
                if (countOfTick % panelFoodFreq.getValue() == 0) {
                    if (particlePanel.getParticles().size() < limitCells) {
                        for (int i = 0; i < panelFood.getValue(); ++i) {
                            particlePanel.getParticles().add(new Cell(plant, 15));
                            particlePanel.getParticles().getLast().setVx(0);
                            particlePanel.getParticles().getLast().setVy(0);
                        }
                    }
                }
                if (countOfTick % panelDPSFreq.getValue() == 0) {
                    for (Cell cell : particlePanel.getParticles()){
                        if (cell.getType() != plant)
                            cell.getDamage(panelDPS.getValue());
                            if (cell.getHealth() <= 0) {
                                toRemove.add(cell);
                            }
                    }
                }
                G = (double) panelGravity.getValue() / 10;
                moveNearID(particlePanel);
                moveToBorder(particlePanel);
                for (int i = 0; i < particlePanel.getParticles().size(); ++i) {
                    for (int j = 0; j < particlePanel.getParticles().size(); ++j) {
                        eating(particlePanel.getParticles().get(i), particlePanel.getParticles().get(j), toRemove, toAdd);
                    }
                    if (particlePanel.getParticles().size() < limitCells && isBig(particlePanel.getParticles().get(i))) {
                        division(particlePanel.getParticles().get(i), toAdd);
                    }
                }
                particlePanel.getParticles().removeAll(toRemove);
                particlePanel.getParticles().addAll(toAdd);
            }
        });
        controlUpdateTimer.start();

        // Добавляем обработчик для кнопки "Restart"
        btnRestart.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartWindow().setVisible(true);
                // Сбросить панель частиц
                particlePanel.reset();
                statisticsPanel.updateStatistics(new HashMap<>());
                SimulationFrame.this.dispose();
            }
        });

        btnPause.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    for (Cell p : particlePanel.getParticles()){
                        p.cont();
                    }
                    controlUpdateTimer.start();
                    btnPause.getButton().setText("PAUSE");
                } else {
                    for (Cell p : particlePanel.getParticles()){
                        p.stop();
                    }
                    controlUpdateTimer.stop();
                    btnPause.getButton().setText("RESUME");
                }
                isPaused = !isPaused;
            }
        });
        btnShow.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isShowed) {
                    btnShow.getButton().setText("HIDE");
                    isShowed = true;
                    bottomPanel.setVisible(isShowed);
                }
                else {
                    btnShow.getButton().setText("SHOW");
                    isShowed = false;
                    bottomPanel.setVisible(isShowed);
                }
            }
        });
        // Добавление обработчика для кнопки "OK"
        panelField.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numParticles = Integer.parseInt(panelField.getNumberInput().getText());
                    int maxParticles = 600;
                    if (numParticles <= 200) {
                        if (particlePanel.getParticles().size() <= maxParticles) {
                            if (particlePanel.getParticles().size() + numParticles < maxParticles) {
                                for (int i = 0; i < numParticles; i++) {
                                    double x = Math.random() * particlePanel.getWidth();
                                    double y = Math.random() * particlePanel.getHeight();
                                    CellType localType;
                                    int choice = panelField.getChoice();
                                    if (choice >= 0 && choice <= 4) {
                                        localType = type.get(panelField.getChoice());
                                        particlePanel.addParticle(new Cell(x, y, localType, limitDiv));
                                        if (isPaused){
                                            particlePanel.getParticles().getLast().stop();
                                        }
                                    }
                                    else if (choice == 5) {
                                        localType = type.get((int) (Math.random() * initialTypes));
                                        particlePanel.addParticle(new Cell(x, y, localType, limitDiv));
                                        if (isPaused){
                                            particlePanel.getParticles().getLast().stop();
                                        }
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(SimulationFrame.this, "Please choose one", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                            else {
                                for (int i = 0; i < maxParticles - numParticles; i++) {
                                    double x = Math.random() * particlePanel.getWidth();
                                    double y = Math.random() * particlePanel.getHeight();
                                    CellType localType;
                                    int choice = panelField.getChoice();
                                    if (choice >= 1 && choice <= 5) {
                                        localType = type.get(panelField.getChoice());
                                        particlePanel.addParticle(new Cell(x, y, localType, limitDiv));
                                        if (isPaused){
                                            particlePanel.getParticles().getLast().stop();
                                        }
                                    }
                                    else if (choice == 6) {
                                        localType = type.get((int) (Math.random() * initialTypes));
                                        particlePanel.addParticle(new Cell(x, y, localType, limitDiv));
                                        if (isPaused){
                                            particlePanel.getParticles().getLast().stop();
                                        }
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(SimulationFrame.this, "Please choose one", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }

                        }
                        else {
                            JOptionPane.showMessageDialog(SimulationFrame.this, "Particle limit reached", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(SimulationFrame.this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SimulationFrame.this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Добавляем слушатель изменений для слайдера частоты обновления
        panelFrequency.getSlider().addChangeListener(e -> {
            int newFrequency = panelFrequency.getValue();
            controlUpdateTimer.setDelay(newFrequency);
        });

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartWindow().setVisible(true);
        });
    }
}