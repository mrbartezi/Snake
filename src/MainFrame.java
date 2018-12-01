import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private GamePanel gamePanel;
    private ScoreBar scoreBar;

    public MainFrame() {
        setSize(800,680);
        setLocation(560,200);
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        setLayout(new BorderLayout());

        gamePanel = new GamePanel();
        scoreBar = new ScoreBar();

        add(gamePanel, BorderLayout.CENTER);
        add(scoreBar, BorderLayout.NORTH);

        pack();

    }
}
