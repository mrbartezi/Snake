import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private ArrayList<Rectangle2D> rectList;
    private int snakeSize = 5, x = 0, y = 0, squareSize = 20, mapWidth, mapHeight, foodX, foodY, gameSpeed = 10;
    private boolean running = false;
    private Thread thread;
    private String lastMove = "right";

    public GamePanel() {
        rectList = new ArrayList<>();
        thread = new Thread(this);

        addKeyListener(this);
        setFocusable(true);

        layoutCompositor();
        newFood();
        start();
    }

    public void layoutCompositor() {
        Dimension dim = getPreferredSize();
        dim.width = 800;
        dim.height = 600;
        mapHeight = dim.height/squareSize;
        mapWidth = dim.width/squareSize;

        setPreferredSize(dim);
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.RED);
        Ellipse2D foodRect = new Ellipse2D.Double(foodX*squareSize, foodY*squareSize, squareSize, squareSize);
        g2.fill(foodRect);
        g2.draw(foodRect);

        g2.setPaint(Color.GREEN);
        for(int i = 0; i < rectList.size(); i++) {
            g2.fill(rectList.get(i));
            g2.draw(rectList.get(i));
        }

        g2.setPaint(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 22);
        setFont(font);
        g2.drawString("Score: " + (snakeSize-5)*gameSpeed,(mapWidth-3)*squareSize*3/4,40);
        g2.drawString("Level: " + gameSpeed, (mapWidth-9)*squareSize/4, 40);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(!lastMove.equals("down")) {
                lastMove = "up";
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(!lastMove.equals("up")) {
                lastMove = "down";
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(!lastMove.equals("left")) {
                lastMove = "right";
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(!lastMove.equals("right")) {
                lastMove = "left";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while(running) {

            if(x == foodX && foodY == y) {
                snakeSize++;
                newFood();
            }

            rectList.add(new Rectangle2D.Double(x*squareSize, y*squareSize, squareSize,squareSize));
            if(rectList.size() > snakeSize) {
                rectList.remove(0);
            }
            repaint();

            try {
                Thread.sleep(500/gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (lastMove) {
                case "up":
                    y--;
                    if(y < 0) {
                        y = mapHeight - 1;
                    }
                    break;
                case "down":
                    y++;
                    if(y >= mapHeight) {
                        y = 0;
                    }
                    break;
                case "right":
                    x++;
                    if(x >= mapWidth) {
                        x = 0;
                    }
                    break;
                case "left":
                    x--;
                    if(x < 0) {
                        x = mapWidth - 1;
                    }
                    break;
            }
        }
    }
    public void start() {
        running = true;
        thread.start();
    }
    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void newFood() {
        Random random = new Random();
        foodX = random.nextInt(mapWidth);
        foodY = random.nextInt(mapHeight);

        for(Rectangle2D rect:rectList) {
            if(rect.getX() == foodX*squareSize && rect.getY() == foodY*squareSize){
                newFood();
                System.out.println("OOPS");
            }
        }
    }
}