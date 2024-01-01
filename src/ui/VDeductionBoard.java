package ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import domain.TheAlchemistGame;
import enums.View;
import ui.framework.VComponent;

public class VDeductionBoard extends VComponent {
    private BufferedImage deductionMarker;

    private BufferedImage redPlusMarker;
    private BufferedImage bluePlusMarker;
    private BufferedImage greenPlusMarker;
    private BufferedImage redMinusMarker;
    private BufferedImage blueMinusMarker;
    private BufferedImage greenMinusMarker;
    private BufferedImage neutralMarker;


    private JPanel markerButtons = new JPanel();
    private JPanel markers = new JPanel();
    private JPanel deductionTokens = new JPanel();

    
    public VDeductionBoard(TheAlchemistGame game) { super(game); }

    @Override
    protected void render() {
        BufferedImage background = null;
        BufferedImage layoutImg = null;
        BufferedImage close = null;
        BufferedImage titlePanel = null;
        

        try {
            background = ImageIO.read(new File("./src/resources/image/DeductionBoardBackground.png"));
            layoutImg = ImageIO.read(new File("./src/resources/image/DeductionBoardLayout.png"));
            deductionMarker = ImageIO.read(new File("./src/resources/image/HUD/DeductionBoardMarker.png"));
            redPlusMarker = ImageIO.read(new File("./src/resources/image/HUD/RedPlusMarker.png"));
            bluePlusMarker = ImageIO.read(new File("./src/resources/image/HUD/BluePlusMarker.png"));
            greenPlusMarker = ImageIO.read(new File("./src/resources/image/HUD/GreenPlusMarker.png"));
            redMinusMarker = ImageIO.read(new File("./src/resources/image/HUD/RedMinusMarker.png"));
            blueMinusMarker = ImageIO.read(new File("./src/resources/image/HUD/BlueMinusMarker.png"));
            greenMinusMarker = ImageIO.read(new File("./src/resources/image/HUD/GreenMinusMarker.png"));
            neutralMarker = ImageIO.read(new File("./src/resources/image/HUD/NeutralMarker.png"));
            close = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            titlePanel = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel bg = new JLabel(new ImageIcon(background));
        bg.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());

        JLabel layout = new JLabel(new ImageIcon(layoutImg));
        layout.setBounds(
            windowDimension.getWidth() / 2 - layoutImg.getWidth() / 2, 
            windowDimension.getHeight() / 2 - layoutImg.getHeight() / 2, 
            layoutImg.getWidth(), 
            layoutImg.getHeight()
        );

        JLabel titlePic = new JLabel(new ImageIcon(titlePanel.getScaledInstance((int)(titlePanel.getWidth() * 0.75), (int)(titlePanel.getHeight() * 0.75), Image.SCALE_SMOOTH)));
        titlePic.setBounds(172, -16, (int)(titlePanel.getWidth() * 0.75), (int)(titlePanel.getHeight() * 0.75));
        
        JLabel titleText = new JLabel("Deduction Board",  SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds(titlePic.getBounds());

        JButton closePic = new JButton(new ImageIcon(close.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(e -> router.to(View.Board));


        markerButtons.setBounds(783, 26, 648, 720);
        markerButtons.setOpaque(false);

        markers.setBounds(784, 26, 648, 720);
        markers.setOpaque(false);
      
        deductionTokens.setBounds(-26, 12, 700, windowDimension.getHeight());
        deductionTokens.setOpaque(false);
    
        this.panel.add(closePic);
        this.panel.add(titleText);
        this.panel.add(titlePic);
        this.panel.add(deductionTokens);
        this.panel.add(markers);
        this.panel.add(markerButtons);
        this.panel.add(layout);
        this.panel.add(bg);
    }


    @Override
    protected void mounted() {
        this.update();
        markerButtons.removeAll();
        markerButtons.setLayout(null);

        for (int i = 0; i < game.getDeductionTable().length; i++)
            for (int j = 0; j < game.getDeductionTable()[0].length; j++) {
                JButton toggleButton = new JButton("");
                toggleButton.setBounds(i * 75, j * 92, 72, 72);
                toggleButton.setOpaque(false);
                toggleButton.setContentAreaFilled(false);
                toggleButton.setBorderPainted(false);
                toggleButton.setFocusable(false);

                final int nameIndex = i;
                final int tableIndex = j;

                toggleButton.addActionListener(event -> {
                    String name = assetLoader.ingredientNames.get(nameIndex);
                    game.toggleDeductionTable(name, tableIndex);
                    this.update();
                });

                markerButtons.add(toggleButton);
            }

        markerButtons.revalidate();
        markerButtons.repaint();
    }

    private void update() {
        markers.removeAll();
        markers.setLayout(null);

        deductionTokens.removeAll();
        deductionTokens.setLayout(null);

        // HashMap<String[], DeductionToken> deductionTokens1 = new HashMap<>() {{
        //     put(new String[]{"mushroom", "fern"}, DeductionToken.BluePlus);
        //     put(new String[]{"mandrake root", "fern"}, DeductionToken.GreenMinus);
        //     put(new String[]{"scorpion tail", "bird claw"}, DeductionToken.RedPlus);
        //     // put(new String[]{"mushroom", "raven's feather"}, DeductionToken.RedMinus);
        //     // put(new String[]{"fern", "warty toad"}, DeductionToken.RedPlus);
        //     put(new String[]{"bird claw", "warty toad"}, DeductionToken.Neutral);
        //     put(new String[]{"bird claw", "moonshade"}, DeductionToken.BluePlus);
        //     put(new String[]{"mandrake root", "moonshade"}, DeductionToken.Neutral);
        //     put(new String[]{"mandrake root", "scorpion tail"}, DeductionToken.GreenMinus);
        //     put(new String[]{"raven's feather", "scorpion tail"}, DeductionToken.RedPlus);
        //     put(new String[]{"mushroom", "moonshade"}, DeductionToken.RedPlus);
        //     // put(new String[]{"warty toad", "scorpion tail"}, DeductionToken.BluePlus);
        //     // put(new String[]{"raven's feather", "bird claw"}, DeductionToken.GreenPlus);
        //     // put(new String[]{"raven's feather", "moonshade"}, DeductionToken.BlueMinus);
        //     // put(new String[]{"warty toad", "mandrake root"}, DeductionToken.GreenPlus);
        //     // put(new String[]{"moonshade", "fern"}, DeductionToken.BluePlus);
        //     // put(new String[]{"mushroom", "bird claw"}, DeductionToken.GreenPlus);
        //     put(new String[]{"mushroom", "warty toad"}, DeductionToken.BluePlus);
        //     put(new String[]{"fern", "bird claw"}, DeductionToken.GreenPlus);
        //     put(new String[]{"moonshade", "warty toad"}, DeductionToken.Neutral);
        //     put(new String[]{"bird claw", "mandrake root"}, DeductionToken.RedPlus);
        //     put(new String[]{"moonshade", "scorpion tail"}, DeductionToken.BluePlus);
        //     // put(new String[]{"raven's feather", "mandrake root"}, DeductionToken.RedPlus);
        //     // put(new String[]{"mushroom", "mandrake root"}, DeductionToken.RedMinus);
        //     // put(new String[]{"fern", "scorpion tail"}, DeductionToken.GreenPlus);
        //     put(new String[]{"raven's feather", "warty toad"}, DeductionToken.Neutral);
        //     put(new String[]{"mushroom", "scorpion tail"}, DeductionToken.GreenPlus);
        //     put(new String[]{"raven's feather", "fern"}, DeductionToken.RedPlus);
        // }};

        for (int i = 0; i < game.getDeductionTable().length; i++)
            for (int j = 0; j < game.getDeductionTable()[0].length; j++) {
                if (game.getDeductionTable()[j][i] == 0) continue;

                JLabel marker = new JLabel(new ImageIcon(deductionMarker));
                marker.setBounds(i * 75 + 7, j * 92 + 7, deductionMarker.getWidth(), deductionMarker.getHeight());

                markers.add(marker);
            }


        game.getDeductionTokens().forEach((k, v) -> {
            Point tokenLocation = getDeductionTokenLocation(k[0], k[1]);
            JLabel token = null;

            switch (v) {
                case RedPlus:
                    token = new JLabel(new ImageIcon(redPlusMarker));
                    break;
                case BluePlus:
                    token = new JLabel(new ImageIcon(bluePlusMarker));
                    break;
                case GreenPlus:
                    token = new JLabel(new ImageIcon(greenPlusMarker));
                    break;
                case RedMinus:
                    token = new JLabel(new ImageIcon(redMinusMarker));
                    break;
                case BlueMinus:
                    token = new JLabel(new ImageIcon(blueMinusMarker));
                    break;
                case GreenMinus:
                    token = new JLabel(new ImageIcon(greenMinusMarker));
                    break;
                case Neutral:
                    token = new JLabel(new ImageIcon(neutralMarker));
                    break;
            }


            token.setBounds(
                tokenLocation.x, 
                tokenLocation.y, 
                redPlusMarker.getWidth(), 
                redPlusMarker.getHeight()
            );
            deductionTokens.add(token);
        });

        markers.revalidate();
        markers.repaint();

        deductionTokens.revalidate();
        deductionTokens.repaint();
    }

    private Point getDeductionTokenLocation(String first, String second) {
        HashMap<String, Point> tokens = new HashMap<String, Point>() {{
            put("mushroom-fern", new Point(616, 67));
            put("mushroom-warty toad", new Point(524, 116));
            put("mushroom-bird claw", new Point(430, 160));
            put("mushroom-moonshade", new Point(336, 206));
            put("mushroom-mandrake root", new Point(248, 250));
            put("mushroom-scorpion tail", new Point(150, 300));
            put("mushroom-raven's feather", new Point(64, 340));
            put("fern-warty toad", new Point(614, 158));
            put("fern-bird claw", new Point(524, 206));
            put("fern-moonshade", new Point(430, 252));
            put("fern-mandrake root", new Point(336, 296));
            put("fern-scorpion tail", new Point(246, 344));
            put("fern-raven's feather", new Point(150, 394));
            put("warty toad-bird claw", new Point(614, 250));
            put("warty toad-moonshade", new Point(524, 300));
            put("warty toad-mandrake root", new Point(430, 342));
            put("warty toad-scorpion tail", new Point(336, 390));
            put("warty toad-raven's feather", new Point(246, 430));
            put("bird claw-moonshade", new Point(614, 344));
            put("bird claw-mandrake root", new Point(524, 390));
            put("bird claw-scorpion tail", new Point(430, 435));
            put("bird claw-raven's feather", new Point(339, 482));
            put("moonshade-mandrake root", new Point(616, 436));
            put("moonshade-scorpion tail", new Point(524, 482));
            put("moonshade-raven's feather", new Point(434, 524));
            put("mandrake root-scorpion tail", new Point(616, 529));
            put("mandrake root-raven's feather", new Point(524, 574));
            put("scorpion tail-raven's feather", new Point(618, 620));
        }};
        
        return tokens.containsKey(first + "-" + second) 
                ? tokens.get(first + "-" + second) 
                : tokens.get(second + "-" + first);
    }
}
