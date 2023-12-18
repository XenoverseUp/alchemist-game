package ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Point;
import java.awt.image.BufferedImage;

import domain.TheAlchemistGame;
import enums.DeductionToken;
import enums.Potion;

public class VDeductionBoard extends VComponent {
    private Router router = Router.getInstance();
    
    private BufferedImage background;
    private BufferedImage layoutImg;
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
    private JPanel alchemyMarkers = new JPanel();

    
    public VDeductionBoard(TheAlchemistGame game) { super(game); }

    @Override
    protected void render() {
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
        } catch (IOException e) {
            System.out.println(e);
        }

        int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
        int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);

        JLabel bg = new JLabel(new ImageIcon(background));
        bg.setBounds(0, 0, width, height);

        JLabel layout = new JLabel(new ImageIcon(layoutImg));
        layout.setBounds(
            84, 
            12,
            layoutImg.getWidth(), 
            layoutImg.getHeight()
        );

        markerButtons.setBounds(808, 12, 648, 720);
        markerButtons.setOpaque(false);

        markers.setBounds(808, 12, 648, 720);
        markers.setOpaque(false);
      
        alchemyMarkers.setBounds(0, 0, 700, Window.frame.getHeight());
        alchemyMarkers.setOpaque(false);

    
        this.panel.add(alchemyMarkers);
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

        String[] names = {
            "mushroom",
            "fern",
            "warty toad",
            "bird claw",
            "moonshade",
            "mandrake root",
            "scorpion tail",
            "raven's feather"
        };

        for (int i = 0; i < game.getDeductionTable().length; i++)
            for (int j = 0; j < game.getDeductionTable()[0].length; j++) {
                JButton toggleButton = new JButton("");
                toggleButton.setBounds(i * 75, j * 92, 70, 70);
                toggleButton.setOpaque(false);
                toggleButton.setContentAreaFilled(false);
                toggleButton.setBorderPainted(false);
                toggleButton.setFocusable(false);

                final int finalI = i;
                final int finalJ = j;

                toggleButton.addActionListener(event -> {
                    game.toggleDeductionTable(names[finalI], finalJ);
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

        alchemyMarkers.removeAll();
        alchemyMarkers.setLayout(null);

        // HashMap<String[], DeductionToken> deductionTokens = new HashMap<>() {{
        //     put(new String[]{"mushroom", "fern"}, DeductionToken.BluePlus);
        //     put(new String[]{"mandrake root", "fern"}, DeductionToken.GreenMinus);
        //     put(new String[]{"scorpion tail", "bird claw"}, DeductionToken.RedPlus);
        //     put(new String[]{"mushroom", "raven's feather"}, DeductionToken.RedMinus);
        //     put(new String[]{"fern", "warty toad"}, DeductionToken.RedPlus);
        //     put(new String[]{"bird claw", "warty toad"}, DeductionToken.Neutral);
        //     put(new String[]{"bird claw", "moonshade"}, DeductionToken.BluePlus);
        //     put(new String[]{"mandrake root", "moonshade"}, DeductionToken.Neutral);
        //     put(new String[]{"mandrake root", "scorpion tail"}, DeductionToken.GreenMinus);
        //     put(new String[]{"raven's feather", "scorpion tail"}, DeductionToken.RedPlus);
        //     put(new String[]{"mushroom", "moonshade"}, DeductionToken.RedPlus);
        //     put(new String[]{"warty toad", "scorpion tail"}, DeductionToken.BluePlus);
        //     put(new String[]{"raven's feather", "bird claw"}, DeductionToken.GreenPlus);
        //     put(new String[]{"raven's feather", "moonshade"}, DeductionToken.BlueMinus);
        //     put(new String[]{"warty toad", "mandrake root"}, DeductionToken.GreenPlus);
        //     put(new String[]{"moonshade", "fern"}, DeductionToken.BluePlus);
        //     put(new String[]{"mushroom", "bird claw"}, DeductionToken.GreenPlus);
        //     put(new String[]{"mushroom", "warty toad"}, DeductionToken.BluePlus);
        //     put(new String[]{"fern", "bird claw"}, DeductionToken.GreenPlus);
        //     put(new String[]{"moonshade", "warty toad"}, DeductionToken.Neutral);
        //     put(new String[]{"bird claw", "mandrake root"}, DeductionToken.RedPlus);
        //     put(new String[]{"moonshade", "scorpion tail"}, DeductionToken.BluePlus);
        //     put(new String[]{"raven's feather", "mandrake root"}, DeductionToken.RedPlus);
        //     put(new String[]{"mushroom", "mandrake root"}, DeductionToken.RedMinus);
        //     put(new String[]{"fern", "scorpion tail"}, DeductionToken.GreenPlus);
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
            Point markerPosition = getAlchemyMarkerLocation(k[0], k[1]);
            JLabel alchemyMarker = null;

            switch (v) {
                case RedPlus:
                    alchemyMarker = new JLabel(new ImageIcon(redPlusMarker));
                    break;
                case BluePlus:
                    alchemyMarker = new JLabel(new ImageIcon(bluePlusMarker));
                    break;
                case GreenPlus:
                    alchemyMarker = new JLabel(new ImageIcon(greenPlusMarker));
                    break;
                case RedMinus:
                    alchemyMarker = new JLabel(new ImageIcon(redMinusMarker));
                    break;
                case BlueMinus:
                    alchemyMarker = new JLabel(new ImageIcon(blueMinusMarker));
                    break;
                case GreenMinus:
                    alchemyMarker = new JLabel(new ImageIcon(greenMinusMarker));
                    break;
                case Neutral:
                    alchemyMarker = new JLabel(new ImageIcon(neutralMarker));
                    break;
            }


            alchemyMarker.setBounds(
                markerPosition.x, 
                markerPosition.y, 
                redPlusMarker.getWidth(), 
                redPlusMarker.getHeight()
            );
            alchemyMarkers.add(alchemyMarker);
        });

        markers.revalidate();
        markers.repaint();

        alchemyMarkers.revalidate();
        alchemyMarkers.repaint();
    }

    private Point getAlchemyMarkerLocation(String first, String second) {
        HashMap<String, Point> data = new HashMap<>() {{
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
        
        return data.containsKey(first + "-" + second) 
                ? data.get(first + "-" + second) 
                : data.get(second + "-" + first);
    }
}
