package ui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import enums.View;

import domain.TheAlchemistGame;

public class VPotionBrewingArea extends VComponent {
    private Router router = Router.getInstance();

    public VPotionBrewingArea(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel brewingPanel = createBrewingJPanel();
        brewingPanel.setBounds(0, 0, Window.frame.getWidth(), Window.frame.getHeight());
        JLabel text = new JLabel("Potion Brewing Area");
        text.setAlignmentX(500);

        BufferedImage bg = null;
        BufferedImage close = null;
        BufferedImage title = null;
        try {
            bg = ImageIO.read(new File("./src/resources/image/potion-brewing-area-bg.jpg"));
            close = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            title = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel bgPic = new JLabel(new ImageIcon(bg));
        bgPic.setBounds(0, 0, Window.frame.getWidth(), Window.frame.getHeight());

        panel.add(bgPic);
        panel.add(brewingPanel);

    }

    private JPanel createBrewingJPanel() {
        JPanel brewingPanel = new JPanel();
        brewingPanel.setOpaque(false);

        JPanel pile = new JPanel();
        pile.setMaximumSize(new Dimension(338, 428));
        pile.setPreferredSize(new Dimension(338, 428));
        pile.setOpaque(false);

        JButton back = new JButton("Back");
        back.setBounds(600, 500, 150, 30);
        back.addActionListener(event -> {
            router.to(View.Board);
        });

        JButton makePotion = new JButton("Make a Potion");
        makePotion.setBounds(600, 200, 150, 30);
        makePotion.addActionListener(event -> {

        });

        JButton selectIngredients = new JButton("Select Ingredients");
        selectIngredients.setBounds(400, 150, 100, 30);
        selectIngredients.addActionListener(event -> {
            router.to(View.Inventory);
        });

        JLabel text = new JLabel("Potion Brewing Area");
        text.setAlignmentX(500);

        // panel.add(Box.createVerticalGlue());
        panel.add(text);

        // panel.add(Box.createVerticalStrut(10));
        panel.add(back);
        // panel.add(Box.createVerticalGlue());
        panel.add(makePotion);
        panel.add(selectIngredients);

        return brewingPanel;

    }

}
