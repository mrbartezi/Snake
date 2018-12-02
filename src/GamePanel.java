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
    private int snakeSize = 5, x = 0, y = 0, squareSize = 20, mapWidth, mapHeight, foodX, foodY, gameSpeed,
            score = 0, bestScore;
    private boolean running = false, gameOver = false;
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
        y = mapHeight/2;

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

        g2.setPaint(Color.LIGHT_GRAY);
        Font font = new Font("Arial", Font.PLAIN, 22);
        setFont(font);
        score = (snakeSize - 5) * gameSpeed;
        if(score > bestScore) {
            bestScore = score;
        }
        if(!gameOver) {
            g2.setPaint(Color.BLACK);

            g2.drawString("Score: " + score, (mapWidth - 3) * squareSize * 3 / 4 - 1,39);
            g2.drawString("Best Score: " + bestScore, (mapWidth - 7) * squareSize / 2 - 1, 39);
            g2.drawString("Level: " + gameSpeed, (mapWidth - 9) * squareSize / 4 - 1, 39);

            g2.setPaint(Color.LIGHT_GRAY);
            g2.drawString("Score: " + score, (mapWidth - 3) * squareSize * 3 / 4, 40);
            g2.drawString("Best Score: " + bestScore, (mapWidth - 7) * squareSize / 2, 40);
            g2.drawString("Level: " + gameSpeed, (mapWidth - 9) * squareSize / 4, 40);


        }

        if(gameOver) {
            setFont(new Font("Arial", Font.PLAIN, 30));

            g2.setPaint(Color.BLACK);
            g2.drawString("GAME OVER", 309,199);
            g2.drawString("Your score: " + score, 299,299);
            g2.drawString("Click ENTER to reset", 249,399);


            g2.setPaint(Color.LIGHT_GRAY);
            g2.drawString("GAME OVER", 310,200);
            g2.drawString("Your score: " + score, 300,300);
            g2.drawString("Click ENTER to reset", 250,400);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(!lastMove.equals("down")) {
                if(!(rectList.get(rectList.size()-2).getX() == x*squareSize && rectList.get(rectList.size()-2).getY() == (y-1)*squareSize)) {
                    lastMove = "up";
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(!lastMove.equals("up")) {
                if(!(rectList.get(rectList.size()-2).getX() == x*squareSize && rectList.get(rectList.size()-2).getY() == (y+1)*squareSize)) {
                    lastMove = "down";
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(!lastMove.equals("left")) {
                if(!(rectList.get(rectList.size()-2).getX() == (x+1)*squareSize && rectList.get(rectList.size()-2).getY() == y*squareSize)) {
                    lastMove = "right";
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(!lastMove.equals("right")) {
                if(!(rectList.get(rectList.size()-2).getX() == (x-1)*squareSize && rectList.get(rectList.size()-2).getY() == y*squareSize)) {
                    lastMove = "left";
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            stop();
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
                Thread.sleep(250/gameSpeed);
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
            for(Rectangle2D rect:rectList) {
                if(rect.getX() == x*squareSize && rect.getY() == y*squareSize){
                    stop();
                }
            }
        }
    }
    public void start() {
        running = true;
        thread.start();
    }
    public void stop() {
        running = false;
        gameOver = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void newFood() {
        Random random = new Random();
        foodX = random.nextInt(mapWidth);
        foodY = random.nextInt(mapHeight);

        for(Rectangle2D rect:rectList) {
            if(rect.getX() == foodX*squareSize && rect.getY() == foodY*squareSize){
                newFood();
            }
        }
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
    public boolean getGameOver() {
        return gameOver;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
    public int getBestScore() {
        return bestScore;
    }
}