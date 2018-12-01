import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener {

    private GamePanel gamePanel;
    private Menu menu;
    private boolean menuOn = true;

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
        add(menu, BorderLayout.CENTER);
        pack();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!menuOn) {
            gamePanel.keyPressed(e);
        }
        if(menuOn) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                remove(menu);
                menuOn = false;
                gamePanel = new GamePanel();
                gamePanel.setGameSpeed(menu.getGameSpeed());
                gamePanel.setFocusable(true);
                add(gamePanel, BorderLayout.CENTER);
                pack();
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                menu.gameSpeedChange(true);
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                menu.gameSpeedChange(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
