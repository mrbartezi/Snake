import javax.swing.*;
import java.awt.*;


public class Menu extends JPanel{

    private int gameSpeed = 5;

    public Menu() {
        Dimension dim = getPreferredSize();
        dim.width = 800;
        dim.height = 600;
        setPreferredSize(dim);
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 40);
        setFont(font);
        g2.drawString("Gamespeed: " + gameSpeed,400,300);
    }

    public void gameSpeedChange(boolean change) {
        if(change) {
            if(this.gameSpeed < 10) {
                this.gameSpeed++;
            }
        }
        else{
            if(this.gameSpeed > 1) {
                this.gameSpeed--;
            }

        }
        repaint();
    }

    public int getGameSpeed() {
        return gameSpeed;
    }
}
