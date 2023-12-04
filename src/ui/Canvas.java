package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import enums.BoardState;

public class Canvas extends JPanel {
        private Timer timer;
        private int FPS = 80;
        private int x = 0;
        private int y = 0;
        private int velocityX = 2;
        private int velocityY = 3;
        private BoardState state = BoardState.Table;


        public Canvas() {
            setBackground(Color.red);
            int delay = 1000 / FPS;
            timer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    update();
                    repaint();
                }
            });
            
            timer.stop();
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
            int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);


            Graphics2D g = (Graphics2D) graphics;
            g.setPaint(Color.BLUE);
            g.fillRect(x, y, 100, 100);

            

            g.dispose();

        }

        private void update() {
            int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
            int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);


            if (this.x < 0 || this.x + 100 > width) {
                this.velocityX *= -1;
            }
           
            if (this.y < 0 || this.y + 100 > height) {
                this.velocityY *= -1;
            }

            this.x += this.velocityX;
            this.y += this.velocityY;
        }


        public void start() {
            this.timer.start();
        }
        
        public void stop() {
            this.timer.stop();
        }
    }
