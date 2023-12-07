package ui;


import javax.imageio.ImageIO;
import javax.swing.*;

import domain.TheAlchemistGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import enums.BoardHover;
import enums.View;

public class Canvas extends JPanel {
        private Timer timer;
        private int FPS = 80;
        private TheAlchemistGame game;
        private BoardHover boardHover = BoardHover.None;
        private Router router = Router.getInstance();
        
        private int x = 0;
        private int y = 0;
        private int width, height;
        private int velocityX = 2;
        private int velocityY = 3;
        
        private MouseAdapter mouseAdapter = new MouseEvents();
        private boolean nextButtonPressed = false;

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
        private BufferedImage publicationAreaOutline = null;
        private BufferedImage publicationAreaOutlineHover = null;
        private BufferedImage buttonSprite = null;
        private BufferedImage buttonPressedSprite = null;
        private BufferedImage verticalStrip = null;



        private Rectangle deductionBoardArea = new Rectangle(258, 440, 200, 170);
        private Rectangle inventoryArea = new Rectangle(458, 340, 220, 190);
        private Rectangle cardArea = new Rectangle(0, 540, 220, 190);
        private Rectangle potionBrewingArea = new Rectangle(780, 280, 290, 200);
        private Rectangle publicationArea = new Rectangle(620, 180, 200, 190);


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
                cardDeckOutline = ImageIO.read(new File("./src/resources/image/HUD/CardDeckOutline.png"));
                cardDeckOutlineHover = ImageIO.read(new File("./src/resources/image/HUD/CardDeckOutlineHover.png"));
                publicationAreaOutline = ImageIO.read(new File("./src/resources/image/HUD/PublicationAreaOutline.png"));
                publicationAreaOutlineHover = ImageIO.read(new File("./src/resources/image/HUD/PublicationAreaOutlineHover.png"));
                buttonSprite = ImageIO.read(new File("./src/resources/image/HUD/button.png"));
                buttonPressedSprite = ImageIO.read(new File("./src/resources/image/HUD/buttonPressed.png"));
                verticalStrip = ImageIO.read(new File("./src/resources/image/HUD/SidebarStrip.png"));
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

            g.drawImage(verticalStrip, null, 1230, 0);

            g.setPaint(Color.white);
            g.setFont(new Font("Itim-Regular", Font.BOLD, 18));
            g.drawString("Beril", 1248, 35);

            
            // Draw title
            setCurrentPlayer(g, game.getCurrentUser().name);
            
            // Draw outlines
            g.setPaint(Color.BLACK);
            g.setFont(new Font("Itim-Regular", Font.BOLD, 12));
            
            g.drawImage(inventoryOutline, null, 434, 316);
            g.drawString("Inventory", 528, 333);
            if (boardHover == BoardHover.Inventory) 
                g.drawImage(inventoryOutlineHover, null, 422, 348);
                
            g.drawImage(deductionOutline, null, 188, 388);
            g.drawString("Deduction Board", 200, 405);
            if (boardHover == BoardHover.DeductionBoard) 
                g.drawImage(deductionOutlineHover, null, 227, 381);
           
            g.drawImage(pbaOutline, null, 799, 294);
            g.drawString("Potion Brewing Area", 863, 311);
            if (boardHover == BoardHover.PotionBrewingArea) 
                g.drawImage(pbaOutlineHover, null, 787, 318);
            
            g.drawImage(cardDeckOutline, null, -24, 567);
            g.drawString("Card Deck", 22, 584);
            if (boardHover == BoardHover.CardDeck) 
                g.drawImage(cardDeckOutlineHover, null, -36, 606);
           
           
            g.drawImage(publicationAreaOutline, null, 520, 175);
            g.drawString("Publication Area", 532, 192);
            if (boardHover == BoardHover.PublicationArea) 
                g.drawImage(publicationAreaOutlineHover, null, 611, 175);

            // Fill Sidebar

            if (nextButtonPressed) {
                g.setPaint(new Color(200, 200, 200));
                BufferedImage scaledButton = getScaledImage(buttonPressedSprite, 175, 50);
                g.drawImage(scaledButton, null, 1250, 675);
                drawCenteredString(g, "Next Turn", new Rectangle(1250, 678, 175, 50), new Font("Itim-Regular", Font.BOLD, 18));
            } else {
                g.setPaint(Color.WHITE);
                BufferedImage scaledButton = getScaledImage(buttonSprite, 175, 50);
                g.drawImage(scaledButton, null, 1250, 675);
                drawCenteredString(g, "Next Turn", new Rectangle(1250, 670, 175, 50), new Font("Itim-Regular", Font.BOLD, 18));
            }
            

            g.setPaint(Color.ORANGE);
            g.fillOval(x, y, 100, 100);

            
            // g.drawRect(258, 540, 200, 70);
            // g.drawRect(458, 340, 220, 190);
            // g.drawRect(0, 540, 220, 190);
            // g.drawRect(780, 280, 290, 300);
            // g.drawRect(620, 180, 200, 190);


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

                if (publicationArea.contains(p)) 
                    router.to(View.PublicationArea);
                else if (potionBrewingArea.contains(p)) 
                    router.to(View.PotionBrewingArea);
                else if (inventoryArea.contains(p)) 
                    router.to(View.Inventory);
                else if (cardArea.contains(p)) 
                    router.to(View.CardDeck);
                else if (deductionBoardArea.contains(p)) 
                    router.to(View.DeductionBoard);
                else if (new Rectangle(1250, 675,175, 50).contains(p)) { 
                    // Next Button
                    nextButtonPressed = true;
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                if (new Rectangle(1250, 675,175, 50).contains(p)) { 
                    game.toggleCurrentUser();
                }
                
                Window.frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                nextButtonPressed = false;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();

                if (publicationArea.contains(p)) {
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    boardHover = BoardHover.PublicationArea;
                } else if (potionBrewingArea.contains(p)) {
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    boardHover = BoardHover.PotionBrewingArea;
                } else if (inventoryArea.contains(p)) {
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    boardHover = BoardHover.Inventory;
                } else if (cardArea.contains(p)) {
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    boardHover = BoardHover.CardDeck;
                } else if (deductionBoardArea.contains(p)) {
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    boardHover = BoardHover.DeductionBoard;
                } else {
                    boardHover = BoardHover.None;
                    Window.frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
            
            g.drawImage(scaledTitle, null, width / 2 - scaledTitle.getWidth() / 2, -16);
            g.setFont(new Font("Itim-Regular", Font.BOLD, 24));
            
            drawCenteredString(
                g, 
                name, 
                new Rectangle(width / 2 - scaledTitle.getWidth() / 2, 
                -16, 
                scaledTitle.getWidth(), scaledTitle.getHeight()), 
                new Font("Itim-Regular", Font.BOLD, 28)
            );

            
        }
}