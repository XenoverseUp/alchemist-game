package ui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import domain.TheAlchemistGame;

public class VDeductionBoard extends VComponent {
    private Router router = Router.getInstance();
    private BufferedImage background;
    private BufferedImage layoutImg;
    private BufferedImage deductionMarker;
    private JPanel markerButtons = new JPanel();

    
    public VDeductionBoard(TheAlchemistGame game) { super(game); }

    @Override
    protected void render() {
        try {
            background = ImageIO.read(new File("./src/resources/image/DeductionBoardBackground.png"));
            layoutImg = ImageIO.read(new File("./src/resources/image/DeductionBoardLayout.png"));
            deductionMarker = ImageIO.read(new File("./src/resources/image/HUD/DeductionBoardMarker.png"));
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


        this.panel.add(markerButtons);
        this.panel.add(layout);
        this.panel.add(bg);
    }


    @Override
    protected void mounted() {
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
                JButton toggleButton = new JButton(Integer.toString(i) + Integer.toString(j));
                toggleButton.setBounds(i * 75, j * 92, 70, 70);

                final int finalI = i;
                final int finalJ = j;

                toggleButton.addActionListener(event -> {
                    game.toggleDeductionTable(names[finalI], finalJ);
                });

                markerButtons.add(toggleButton);
            }

        markerButtons.revalidate();
        markerButtons.repaint();

    }
}
