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

public class VPause extends VComponent {
    private Router router = Router.getInstance();

    public VPause(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        JPanel brewingPanel = createBrewingJPanel();
        JLabel text = new JLabel("Pause Screen");
        text.setBounds(500, 10, 200, 30);

        panel.add(text);

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
        makePotion.addActionListener(event -> { // will call combine, addPotion
        });

        JButton doExperiment = new JButton("Do the experiment");
        doExperiment.setBounds(620, 350, 150, 30);
        doExperiment.addActionListener(event -> { // will call make experiment
        });

        JButton selectIngredients = new JButton("Select Ingredients");
        selectIngredients.setBounds(370, 250, 150, 30);
        selectIngredients.addActionListener(event -> router.to(View.Inventory)); // may call remove ingredient card?

        brewingPanel.add(back);
        brewingPanel.add(doExperiment);
        brewingPanel.add(makePotion);
        brewingPanel.add(selectIngredients);

        return brewingPanel;
    }
}
