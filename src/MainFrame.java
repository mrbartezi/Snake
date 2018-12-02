import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener {

    private GamePanel gamePanel;
    private Menu menu;
    private boolean menuOn = true, newMenu = false;
    private int bestScore = 0, gameSpeed = 5;

    public MainFrame() {
        setLocation(560,240);
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());

        addKeyListener(this);
        setFocusable(true);

        menu = new Menu();
        menu.setGameSpeed(gameSpeed);
        add(menu, BorderLayout.CENTER);
        pack();

    }
    public void newG() {
        remove(gamePanel);
        menuOn = true;

        menu = new Menu();
        menu.setGameSpeed(gameSpeed);
        add(menu, BorderLayout.CENTER);
        pack();
        newMenu = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!menuOn) {
            if(gamePanel.getGameOver()) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    newG();
                    newMenu = true;
                    e.setKeyCode(0);
                }
            }
            gamePanel.keyPressed(e);
            if(gamePanel.getBestScore() > bestScore) {
                this.bestScore = gamePanel.getBestScore();
            }
        }
        if(menuOn) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                gameSpeed = menu.getGameSpeed();
                remove(menu);
                menuOn = false;
                gamePanel = new GamePanel();
                gamePanel.setBestScore(bestScore);
                gamePanel.setGameSpeed(gameSpeed);
                add(gamePanel, BorderLayout.CENTER);
                pack();
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                menu.gameSpeedChange(true);
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                menu.gameSpeedChange(false);
            }
            else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setBestScore(int score) {
        this.bestScore = score;
    }
    public int getBestScore() {
        return bestScore;
    }
}