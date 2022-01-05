package snake_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

    private static final int BOARD_WIDHT = 300;
    private static final int BOARD_HEIGHT = 300;
    private static final int DOT_SIZE = 10;
    private static final int ALL_DOTS = 900;
    private static final int RAND_POS = 29;
    private static final int DELAY = 140;

    private Random rand = SecureRandom.getInstanceStrong();

    private final int [] boardX = new int[ALL_DOTS];
    private final int [] boardY = new int[ALL_DOTS];

    private int dots;
    private int appleX;
    private int appleY;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private transient Image ball;
    private transient Image apple;
    private transient Image head;

    public Board() throws NoSuchAlgorithmException {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(BOARD_WIDHT, BOARD_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            boardX[z] = 50 - z * 10;
            boardY[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {

            g.drawImage(apple, appleX, appleY, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, boardX[z], boardY[z], this);
                } else {
                    g.drawImage(ball, boardX[z], boardY[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BOARD_WIDHT - metr.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
    }

    private void checkApple() {

        if ((boardX[0] == appleX) && (boardY[0] == appleY)) {

            dots++;
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            boardX[z] = boardX[(z - 1)];
            boardY[z] = boardY[(z - 1)];
        }

        if (leftDirection) {
            boardX[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            boardX[0] += DOT_SIZE;
        }

        if (upDirection) {
            boardY[0] -= DOT_SIZE;
        }

        if (downDirection) {
            boardY[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (boardX[0] == boardX[z]) && (boardY[0] == boardY[z])) {
                inGame = false;
            }
        }

        if (boardY[0] >= BOARD_HEIGHT) {
            inGame = false;
        }

        if (boardY[0] < 0) {
            inGame = false;
        }

        if (boardX[0] >= BOARD_WIDHT) {
            inGame = false;
        }

        if (boardX[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = this.rand.nextInt(RAND_POS);
        appleX = r * DOT_SIZE;

        r = this.rand.nextInt(RAND_POS);
        appleY = r * DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
