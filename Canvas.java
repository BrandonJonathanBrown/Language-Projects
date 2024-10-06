import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

/**
 * 
 */
public class Canvas extends JPanel implements KeyListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int BALL_DIAMETER = 50;
    private static final int RECT_WIDTH = 250;
    private static final int RECT_HEIGHT = 50;
    private static final double INITIAL_SPEED = 20;
    private static final int SPEED_INCREMENT_SCORE = 500;

    private Timer timer;
    private Timer elapsedTimeTimer;
    private long startTime;
    private int score = 0;
    private int ballX, ballY;
    private double ballVelX, ballVelY;
    private int rectX, rectY;
    private boolean gameOver = false;
    private boolean paused = false;
    private JLabel scoreLabel;
    private JLabel gameOverLabel;
    private int lastSpeedIncreaseScore = 0;
    private DecimalFormat timeFormat = new DecimalFormat("0.0");
    private BufferedImage image = null;

    public Canvas() {
        initializeCanvas();
        initializeTimers();
        startGame();
    }

    private void initializeCanvas() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);

        scoreLabel = createScoreLabel();
        add(scoreLabel);

        gameOverLabel = createGameOverLabel();
        add(gameOverLabel);
    }

    private JLabel createScoreLabel() {
        JLabel label = new JLabel("<html><font color='white'>Score: " + score + " | Time: 0.0s</font></html>");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        label.setBounds(10, 10, 400, 30);
        return label;
    }

    private JLabel createGameOverLabel() {
        JLabel label = new JLabel("GAME OVER");
        label.setFont(new Font("Times New Roman", Font.BOLD, 50));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.GREEN);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBounds(0, HEIGHT / 2 - 50, WIDTH, 100);
        label.setVisible(false);
        return label;
    }

    private void initializeTimers() {
        timer = new Timer(1000 / 60, e -> {
            if (!gameOver && !paused) {
                updateBallPosition();
                repaint();
            }
        });
        timer.start();

        elapsedTimeTimer = new Timer(100, e -> {
            if (!gameOver && !paused) {
                updateElapsedTime();
            }
        });
        elapsedTimeTimer.start();
    }

    public void startGame() {
        ballX = WIDTH / 2 - BALL_DIAMETER / 2;
        ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
        ballVelX = INITIAL_SPEED * 0.5;
        ballVelY = INITIAL_SPEED;
        startTime = System.currentTimeMillis();
        gameOverLabel.setVisible(false);
        paused = false;
        rectX = WIDTH / 2 - RECT_WIDTH / 2; // Center the paddle
        rectY = HEIGHT - RECT_HEIGHT - 50;
    }

    private void updateBallPosition() {
        ballX += ballVelX;
        ballY += ballVelY;

        if (ballX < 0 || ballX + BALL_DIAMETER > WIDTH) {
            ballVelX = -ballVelX; // Bounce off left/right walls
        }

        if (ballY < 0) {
            ballVelY = -ballVelY; // Bounce off the top wall
        }

        if (ballY + BALL_DIAMETER >= HEIGHT) {
            handleGameOver();
            return;
        }

        if (isCollidingWithPaddle()) {
            handlePaddleCollision();
            updateScore();
        }
    }

    private boolean isCollidingWithPaddle() {
        return ballX + BALL_DIAMETER > rectX && ballX < rectX + RECT_WIDTH &&
               ballY + BALL_DIAMETER > rectY && ballY < rectY + RECT_HEIGHT;
    }

    private void handleGameOver() {
        gameOver = true;
        resetBallPosition();
        updateScore();
        gameOverLabel.setVisible(true);
    }

    private void resetBallPosition() {
        ballX = WIDTH / 2 - BALL_DIAMETER / 2;
        ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
        ballVelX = INITIAL_SPEED * 0.5;
        ballVelY = INITIAL_SPEED;
    }

    private void handlePaddleCollision() {
        double ballCenterX = ballX + BALL_DIAMETER / 2;
        double ballCenterY = ballY + BALL_DIAMETER / 2;
        double rectCenterX = rectX + RECT_WIDTH / 2;
        double rectCenterY = rectY + RECT_HEIGHT / 2;

        double dx = Math.min(ballCenterX - rectX, rectX + RECT_WIDTH - ballCenterX);
        double dy = Math.min(ballCenterY - rectY, rectY + RECT_HEIGHT - ballCenterY);

        if (dx < dy) {
            ballVelX = -ballVelX; // Bounce off paddle
        } else {
            ballVelY = -ballVelY; // Bounce off paddle
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateScoreLabel();
    }

    public void updateScore() {
        if (gameOver) {
            score = 0;
            lastSpeedIncreaseScore = 0;
        } else {
            int randomInt = ThreadLocalRandom.current().nextInt(1, 100);
            score += randomInt;
            if (score / SPEED_INCREMENT_SCORE > lastSpeedIncreaseScore / SPEED_INCREMENT_SCORE) {
                increaseBallSpeed();
                lastSpeedIncreaseScore = score;
            }
        }
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        String timeString = timeFormat.format((System.currentTimeMillis() - startTime) / 1000.0);
        scoreLabel.setText("<html><font color='white'>Score: " + score + " | Time: " + timeString + "s</font></html>");
    }

    private void increaseBallSpeed() {
        double speedIncrement = 5;
        ballVelX += (ballVelX > 0 ? speedIncrement : -speedIncrement);
        ballVelY += (ballVelY > 0 ? speedIncrement : -speedIncrement);
    }

    private void updateElapsedTime() {
        if (!gameOver && !paused) {
            updateScoreLabel();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (image != null) {
            g2d.drawImage(image, 0, 0, this);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,800,800);

        g2d.setColor(Color.ORANGE);
        g2d.fillRect(rectX, rectY, RECT_WIDTH, RECT_HEIGHT);

        g2d.setColor(Color.CYAN);
        g2d.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                startGame();
                setScore(0);
                gameOver = false;
            }
            return;
        }
        
        int MOVE_DISTANCE = 25;

        if (e.getKeyCode() == KeyEvent.VK_S) {
            paused = !paused;
            if (paused) {
                scoreLabel.setText("<html><font color='white'>Score: " + score + " | Time: " + timeFormat.format((System.currentTimeMillis() - startTime) / 1000.0) + "s (Paused)</font></html>");
            } else {
                updateScoreLabel();
            }
            return;
        }
        switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            rectX -= MOVE_DISTANCE;
            break;
        case KeyEvent.VK_RIGHT:
            rectX += MOVE_DISTANCE;
            break;
    }
    rectX = Math.max(0, Math.min(rectX, WIDTH - RECT_WIDTH)); // Ensure paddle stays within bounds
}

@Override
public void keyTyped(KeyEvent e) {
    // Not used
}

@Override
public void keyReleased(KeyEvent e) {
    // Not used
}

public void setImage(BufferedImage image) {
    this.image = image;
    repaint();
}

public Timer getElapsedTimeTimer() {
	return elapsedTimeTimer;
}

public void setElapsedTimeTimer(Timer elapsedTimeTimer) {
	this.elapsedTimeTimer = elapsedTimeTimer;
}

public int getBallX() {
	return ballX;
}

public void setBallX(int ballX) {
	this.ballX = ballX;
}

public int getBallY() {
	return ballY;
}

public void setBallY(int ballY) {
	this.ballY = ballY;
}

public double getBallVelX() {
	return ballVelX;
}

public void setBallVelX(double ballVelX) {
	this.ballVelX = ballVelX;
}

public double getBallVelY() {
	return ballVelY;
}

public void setBallVelY(double ballVelY) {
	this.ballVelY = ballVelY;
}

public boolean isGameOver() {
	return gameOver;
}

public void setGameOver(boolean gameOver) {
	this.gameOver = gameOver;
}

public JLabel getGameOverLabel() {
	return gameOverLabel;
}

public void setGameOverLabel(JLabel gameOverLabel) {
	this.gameOverLabel = gameOverLabel;
}

public int getWidth() {
	return WIDTH;
}

public int getHeight() {
	return HEIGHT;
}

public static int getBallDiameter() {
	return BALL_DIAMETER;
}

public static int getRectWidth() {
	return RECT_WIDTH;
}

public static int getRectHeight() {
	return RECT_HEIGHT;
}

public static double getInitialSpeed() {
	return INITIAL_SPEED;
}

public static int getSpeedIncrementScore() {
	return SPEED_INCREMENT_SCORE;
}

public BufferedImage getImage() {
	return image;
}

public void setRectX(int recX) {
	
	this.rectX = recX;
}

public void setRectY(int recY)
{
	this.rectY = recY;
}

public int getRectX()
{
	return rectX;
	
}

public int getRectY()
{
	return rectY;
}

}  
