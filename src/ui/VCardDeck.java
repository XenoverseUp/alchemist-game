package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enums.View;
import error.NotEnoughActionsException;
import ui.framework.VComponent;
import domain.TheAlchemistGame;

public class VCardDeck extends VComponent {

    public VCardDeck(TheAlchemistGame game) {
        super(game);
    }
    
    @Override
    protected void render() {
        JPanel ingredientCardDeck = createCardDeck("ingredient", "Forage Ingredient");
        JPanel artifactCardDeck = createCardDeck("artifact", "Visit Artifact Shop");
        
        ingredientCardDeck.setBounds(0, 0, windowDimension.getWidth() / 2, windowDimension.getHeight());
        artifactCardDeck.setBounds(
            windowDimension.getWidth() / 2, 
            0, 
            windowDimension.getWidth() / 2,
            windowDimension.getHeight()
        );
        
        BufferedImage BBackground = assetLoader.getBackground(View.CardDeck);
        JLabel bgPic = new JLabel(new ImageIcon(BBackground));
        bgPic.setBounds(0,0, windowDimension.getWidth(), windowDimension.getHeight());


        Image BTitle = assetLoader.getPageBanner();
        JLabel title = new JLabel(new ImageIcon(BTitle));
        title.setBounds(windowDimension.getWidth() / 2 - BTitle.getWidth(null) / 2, -16, BTitle.getWidth(null), BTitle.getHeight(null));

        JLabel titleText = new JLabel("Card Deck", SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 24));
        titleText.setBounds(title.getBounds());

        Image close = assetLoader.getClose();
        JButton closePic = new JButton(new ImageIcon(close.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(e -> router.to(View.Board));

        panel.add(titleText);
        panel.add(title);
        panel.add(closePic);
        panel.add(ingredientCardDeck);
        panel.add(artifactCardDeck);
        panel.add(bgPic);
    }

    private JPanel createCardDeck(String type, String buttonText) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setOpaque(false);

        JPanel pile = new JPanel();
        pile.setMaximumSize(new Dimension(338, 428));
        pile.setPreferredSize(new Dimension(338, 428));
        pile.setOpaque(false);

        BufferedImage ingredientCardPile = null;
        BufferedImage artifactCardPile = null;
        
        try {
            if (type.equals("ingredient")) ingredientCardPile = ImageIO.read(new File("./src/resources/image/ingredientCardPile.png"));
            else artifactCardPile = ImageIO.read(new File("./src/resources/image/artifactCardPile.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        JLabel deckPic = new JLabel(new ImageIcon(
            type.equals("ingredient") 
                ? ingredientCardPile
                : artifactCardPile
        ));

        deckPic.setBounds(0, 0, pile.getWidth(), pile.getHeight());
        pile.add(deckPic);

        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(event -> {
            if (type == "ingredient") {
                try {
                    if (game.isOnline()) game.online.forageIngredient();
                    else game.forageIngredient();
                    router.to(View.Inventory);
                } catch (NotEnoughActionsException e) {
                   modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
                }
            } else router.to(View.ArtifactShop);
            
        });


        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(pile);
        cardPanel.add(Box.createVerticalStrut(24));
        cardPanel.add(button);
        cardPanel.add(Box.createVerticalGlue());

        return cardPanel;

    }

}
