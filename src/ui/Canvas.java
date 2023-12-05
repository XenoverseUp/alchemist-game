package ui;


import javax.imageio.ImageIO;
import javax.swing.*;

import domain.TheAlchemistGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import enums.BoardHover;
import enums.BoardState;

public class Canvas extends JPanel {
        private Timer timer;
        private int FPS = 80;
        private TheAlchemistGame game;
        private BoardHover boardHover = BoardHover.None;
        
        private int x = 0;
        private int y = 0;
        private int width, height;
        private int velocityX = 2;
        private int velocityY = 3;
        
        private MouseAdapter mouseAdapter = new MouseEvents();
        private BoardState state = BoardState.Table;

        private BufferedImage bg = null; 
        private BufferedImage titleLarge = null; 

        private BufferedImage inventoryOutline = null;
        private BufferedImage inventoryOutlineHover = null;
        private BufferedImage deductionOutline = null;
        private BufferedImage deductionOutlineHover = null;
        private BufferedImage pbaOutline = null;
        private BufferedImage pbaOutlineHover = null;
        private BufferedImage cardDeckOutline = null;
        private BufferedImage cardDeckOutlineHover = null;


        private Rectangle deductionBoardArea = new Rectangle(258, 540, 200, 70);
        private Rectangle inventoryArea = new Rectangle(458, 340, 220, 190);
        private Rectangle cardArea = new Rectangle(0, 540, 220, 190);
        private Rectangle potionBrewingArea = new Rectangle(780, 280, 290, 300);


        public Canvas(TheAlchemistGame game) {
            this.game = game;

            setOpaque(false);
            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);

            try {
                titleLarge = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
                bg = ImageIO.read(new File("./src/resources/image/board.jpg"));
                inventoryOutline = ImageIO.read(new File("./src/resources/image/HUD/InventoryOutline.png"));
                inventoryOutlineHover = ImageIO.read(new File("./src/resources/image/HUD/InventoryOutlineHover.png"));
                deductionOutline = ImageIO.read(new File("./src/resources/image/HUD/DeductionOutline.png"));
                deductionOutlineHover = ImageIO.read(new File("./src/resources/image/HUD/DeductionOutlineHover.png"));
                pbaOutline = ImageIO.read(new File("./src/resources/image/HUD/PBAOutline.png"));
                pbaOutlineHover = ImageIO.read(new File("./src/resources/image/HUD/PBAOutlineHover.png"));
                cardDeckOutline = ImageIO.read(new File("./src/resources/image/HUD/cardDeckOutline.png"));
                cardDeckOutlineHover = ImageIO.read(new File("./src/resources/image/HUD/cardDeckOutlineHover.png"));
            } catch (IOException e) {
                System.out.println(e);
            }

      

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



            Graphics2D g = (Graphics2D) graphics;


            // Draw background
            g.drawImage(bg, null, -20, -30);

            // Draw sidebar
            g.setPaint(new Color(25, 25, 25));
            g.fillRect(1233, 0, width - 1233, height);

            g.setPaint(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Beril", 1248, 35);
            
            // Draw title
            setCurrentPlayer(g, "Can");
            
            // Draw outlines
            g.setPaint(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            
            g.drawImage(inventoryOutline, null, 434, 316);
            g.drawString("Inventory", 531, 333);
            if (boardHover == BoardHover.Inventory) 
                g.drawImage(inventoryOutlineHover, null, 422, 348);
                
            g.drawImage(deductionOutline, null, 188, 388);
            g.drawString("Deduction Board", 203, 405);
            if (boardHover == BoardHover.DeductionBoard) 
                g.drawImage(deductionOutlineHover, null, 227, 381);
           
            g.drawImage(pbaOutline, null, 799, 294);
            g.drawString("Potion Brewing Area", 867, 311);
            if (boardHover == BoardHover.PotionBrewingArea) 
                g.drawImage(pbaOutlineHover, null, 787, 318);
            
            g.drawImage(cardDeckOutline, null, -24, 567);
            g.drawString("Card Deck", 24, 584);
            if (boardHover == BoardHover.CardDeck) 
                g.drawImage(cardDeckOutlineHover, null, -36, 606);

            g.setPaint(Color.BLUE);
            g.fillRect(x, y, 100, 100);

            
            // g.drawRect(258, 540, 200, 70);
            // g.drawRect(458, 340, 220, 190);
            // g.drawRect(0, 540, 220, 190);
            // g.drawRect(780, 280, 290, 300);


            g.dispose();

        }

        private void update() {
            this.width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
            this.height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);


            if (this.x < 0 || this.x + 100 > width) {
                this.velocityX *= -1;
            }
           
            if (this.y < 0 || this.y + 100 > height) {
                this.velocityY *= -1;
            }

            this.x += this.velocityX;
            this.y += this.velocityY;
        }

        private class MouseEvents extends MouseAdapter {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                
                if (potionBrewingArea.contains(p)) 
                    System.out.println("Clicked Potion Brewing Area.");
                else if (inventoryArea.contains(p)) 
                    System.out.println("Clicked Inventory.");
                else if (cardArea.contains(p)) 
                    System.out.println("Clicked Card Deck.");
                else if (deductionBoardArea.contains(p)) 
                    System.out.println("Clicked Deduction Board.");  
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();

                if (potionBrewingArea.contains(p)) {
                    Window.frame.setCursor(Cursor.HAND_CURSOR);
                    boardHover = BoardHover.PotionBrewingArea;
                } else if (inventoryArea.contains(p)) {
                    Window.frame.setCursor(Cursor.HAND_CURSOR);
                    boardHover = BoardHover.Inventory;
                } else if (cardArea.contains(p)) {
                    Window.frame.setCursor(Cursor.HAND_CURSOR);
                    boardHover = BoardHover.CardDeck;
                } else if (deductionBoardArea.contains(p)) {
                    Window.frame.setCursor(Cursor.HAND_CURSOR);
                    boardHover = BoardHover.DeductionBoard;
                } else {
                    boardHover = BoardHover.None;
                    Window.frame.setCursor(Cursor.DEFAULT_CURSOR);

                }
            }
        }

        public void start() {
            this.timer.start();
        }
        
        public void stop() {
            this.timer.stop();
        }

        private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
            FontMetrics metrics = g.getFontMetrics(font);
            int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
            int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
            g.setFont(font);
            g.drawString(text, x, y);
        }

        private BufferedImage getScaledImage(BufferedImage image, int width, int height) {
            int imageWidth  = image.getWidth();
            int imageHeight = image.getHeight();
        
            double scaleX = (double)width/imageWidth;
            double scaleY = (double)height/imageHeight;
            AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
            AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
        
            return bilinearScaleOp.filter(
                image,
                new BufferedImage(width, height, image.getType()));
        }

        private void setCurrentPlayer(Graphics2D g, String name) {
            BufferedImage scaledTitle = getScaledImage(titleLarge, (int)(titleLarge.getWidth() * 0.75), (int)(titleLarge.getHeight() * 0.75));
            
            g.drawImage(scaledTitle, null, width / 2 - scaledTitle.getWidth() / 2, 10);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            
            drawCenteredString(
                g, 
                name, 
                new Rectangle(width / 2 - scaledTitle.getWidth() / 2, 
                10, 
                scaledTitle.getWidth(), scaledTitle.getHeight()), 
                new Font("Arial", Font.BOLD, 28)
            );

            
        }
}
