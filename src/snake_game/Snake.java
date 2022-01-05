package snake_game;

import java.awt.EventQueue;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;

public class Snake extends JFrame {

    public Snake() throws NoSuchAlgorithmException {

        initUI();
    }

    private void initUI() throws NoSuchAlgorithmException {

        add(new Board());

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = null;
            try {
                ex = new Snake();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }
}
