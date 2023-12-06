package ui;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
        JPanel brewingPanel = createBrewingJPanel();
        JLabel text = new JLabel("Potion Brewing Area");
        text.setBounds(500, 10, 200, 30);

        BufferedImage bg = null;
        try {
            bg = ImageIO.read(new File("./src/resources/image/potion-brewing-area-bg.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel bgPic = new JLabel(new ImageIcon(bg));
        bgPic.setBounds(-200, -50, Window.frame.getWidth(), Window.frame.getHeight());

        panel.setLayout(null); // Use absolute positioning
        panel.add(brewingPanel);
        panel.add(text);
        panel.add(bgPic);
    }

    private JPanel createBrewingJPanel() {
        JPanel brewingPanel = new JPanel();
        brewingPanel.setBounds(-200, -50, Window.frame.getWidth(), Window.frame.getHeight());
        brewingPanel.setLayout(null); // Use absolute positioning
        brewingPanel.setOpaque(false);

        JButton back = new JButton("Back");
        back.setBounds(600, 500, 150, 30);
        back.addActionListener(event -> router.to(View.Board));

        JButton makePotion = new JButton("Make a Potion");
        makePotion.setBounds(850, 400, 150, 30);
        makePotion.addActionListener(event -> {
        });

        JButton doExperiment = new JButton("Do the experiment");
        doExperiment.setBounds(620, 350, 150, 30);
        doExperiment.addActionListener(event -> {
        });

        JButton selectIngredients = new JButton("Select Ingredients");
        selectIngredients.setBounds(370, 250, 150, 30);
        selectIngredients.addActionListener(event -> router.to(View.Inventory));

        brewingPanel.add(back);
        brewingPanel.add(doExperiment);
        brewingPanel.add(makePotion);
        brewingPanel.add(selectIngredients);

        return brewingPanel;
    }
}
