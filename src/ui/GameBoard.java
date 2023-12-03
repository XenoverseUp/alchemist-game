package ui;

import javax.swing.*;
import java.awt.*;
import domain.Auth;

public class GameBoard extends JFrame {

    private static final int BOARD_SIZE = 10;
    private static final int CELL_SIZE = 50;

    public GameBoard(Auth auth) {
        setTitle("Basic Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        BoardPanel boardPanel = new BoardPanel();
        boardPanel.setBounds(100, 100, 300, 300);
        add(boardPanel);
        JButton actionButton = new JButton("Toggle Player");
        actionButton.addActionListener(e -> auth.toggleCurrentUser());
        actionButton.setBounds(50, 50, 160, 35);
        add(actionButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleButtonClick() {

        JOptionPane.showMessageDialog(this, "Button Clicked!");
    }

    private class BoardPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Drawing the game board
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;

                    // Custom
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        }
    }

}
