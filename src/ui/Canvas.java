package ui;

import javax.imageio.ImageIO;
import javax.swing.*;

import domain.Game;

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
import java.util.HashMap;

import enums.Avatar;
import enums.BoardHover;
import enums.BroadcastAction;
import enums.GamePhase;
import enums.View;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import ui.framework.AssetLoader;
import ui.framework.Router;
import ui.framework.WindowDimension;

public class Canvas extends JPanel implements IBroadcastListener {
    private Router router = Router.getInstance();
    private WindowDimension windowDimension = WindowDimension.getInstance();
    private AssetLoader assetLoader = AssetLoader.getInstance();
    
    private Timer timer;
    private int FPS = 80;
    private Game game;
    private BoardHover boardHover = BoardHover.None;
    
    private int x = 0;
    private int y = 0;
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


    public Canvas(Game game) {
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

        if (game.isOnline() && !game.getOnlineRegister().isYourTurn()) {
            g.drawImage(assetLoader.getBlurredBoard(), null, 0, 0);
            
            g.setPaint(Color.WHITE);
            drawCenteredString(
                g, 
                String.format("It's %s's turn.", game.getRegister().getCurrentPlayerName()), 
                new Rectangle(0, 300, windowDimension.getWidth(), 100), 
                new Font("Crimson Pro", Font.BOLD, 32)
            );
            
            drawCenteredString(
                g, 
                String.format("Wait till all players pass their turns.", game.getRegister().getCurrentPlayerName()), 
                new Rectangle(0, 370, windowDimension.getWidth(), 30), 
                new Font("Crimson Pro", Font.PLAIN, 16)
            );

            g.drawImage(assetLoader.getAvatarImage(game.getRegister().getCurrentPlayerAvatar()), null, x, y);
            g.dispose();

            return;
        }


        // Draw background
        g.drawImage(bg, null, -20, -30);

        // Draw sidebar
        int sidebarWidth = 220;

        g.setPaint(new Color(25, 25, 25));
        g.fillRect(windowDimension.getWidth() - sidebarWidth, 0, sidebarWidth, windowDimension.getHeight());

        g.drawImage(verticalStrip, null, windowDimension.getWidth() - sidebarWidth - 3, 0);

        g.setPaint(Color.white);
        g.setFont(new Font("Itim-Regular", Font.BOLD, 18));

        g.drawString(game.getRegister().getCurrentPlayerName(), 1248, 35);

        
        
        g.setFont(new Font("Itim-Regular", Font.PLAIN, 14));

        
        g.drawString(Integer.toString(game.getRegister().getCurrentPlayerActions()) + " left actions", 1248, 65);
        g.drawString(Integer.toString(game.getRegister().getCurrentPlayerGold()) + " golds", 1248, 85);
        g.drawString(Integer.toString(game.getRegister().getCurrentPlayerReputation()) + " reputations", 1248, 105);
        g.drawString(Integer.toString(game.getRegister().getCurrentPlayerSickness()) + " sickness", 1248, 125);


        // Draw title
        setCurrentPlayer(g, game.getRegister().getCurrentPlayerName());
        
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
        

        if(game.getRegister().getPhase() != GamePhase.FirstRound){
            g.drawImage(pbaOutline, null, 799, 294);
            g.drawString("Potion Brewing Area", 863, 311);
            if (boardHover == BoardHover.PotionBrewingArea) 
                g.drawImage(pbaOutlineHover, null, 787, 318);
        }
      
        
        g.drawImage(cardDeckOutline, null, -24, 567);
        g.drawString("Card Deck", 22, 584);
        if (boardHover == BoardHover.CardDeck) 
            g.drawImage(cardDeckOutlineHover, null, -36, 606);
        
        if(game.getRegister().getPhase() != GamePhase.FirstRound){
            g.drawImage(publicationAreaOutline, null, 520, 175);
            g.drawString("Publication Area", 532, 192);
            if (boardHover == BoardHover.PublicationArea) 
                g.drawImage(publicationAreaOutlineHover, null, 611, 175);
        }


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
        

        g.drawImage(assetLoader.getAvatarImage(game.getRegister().getCurrentPlayerAvatar()), null, x, y);

        g.dispose();

    }

    private void update() {
        if (this.x < 0 || this.x + 130 > windowDimension.getWidth()) {
            this.velocityX *= -1;
        }
        
        if (this.y < 0 || this.y + 130 > windowDimension.getHeight()) {
            this.velocityY *= -1;
        }

        this.x += this.velocityX;
        this.y += this.velocityY;
    }

    private class MouseEvents extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (game.isOnline() && !game.getOnlineRegister().isYourTurn()) return;

            Point p = e.getPoint();

            if (publicationArea.contains(p)) {
                if (game.getRegister().getPhase() != GamePhase.FirstRound) 
                    router.to(View.PublicationArea);
            } else if (potionBrewingArea.contains(p)) {
                if (game.getRegister().getPhase() != GamePhase.FirstRound) 
                    router.to(View.PotionBrewingArea);
            } else if (inventoryArea.contains(p)) 
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
            if (game.isOnline() && !game.getOnlineRegister().isYourTurn()) return;


            Point p = e.getPoint();

            if (new Rectangle(1250, 675,175, 50).contains(p)) { 
                game.getRegister().toggleCurrentUser();
            }
            
            Window.frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            nextButtonPressed = false;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (game.isOnline() && !game.getOnlineRegister().isYourTurn()) return;

            Point p = e.getPoint();

            if (publicationArea.contains(p) && game.getRegister().getPhase() != GamePhase.FirstRound) {
                Window.frame.getContentPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
                boardHover = BoardHover.PublicationArea;
            } else if (potionBrewingArea.contains(p) && game.getRegister().getPhase() != GamePhase.FirstRound) {
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
        if (game.isOnline()) game.addBroadcastListener(this);
        this.timer.start();
    }
    
    public void stop() {
        if (game.isOnline()) game.removeBroadcastListener(this);
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
        
        g.drawImage(scaledTitle, null, windowDimension.getWidth() / 2 - scaledTitle.getWidth() / 2, -16);
        g.setFont(new Font("Itim-Regular", Font.BOLD, 24));
        
        drawCenteredString(
            g, 
            name, 
            new Rectangle(windowDimension.getWidth() / 2 - scaledTitle.getWidth() / 2, 
            -16, 
            scaledTitle.getWidth(), scaledTitle.getHeight()), 
            new Font("Itim-Regular", Font.BOLD, 28)
        );

        
    }

    @Override
    public void onBroadcast(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        if (action == BroadcastAction.PLAYER_TOGGLED) game.getOnlineRegister().revalidateCache();
    }
}
