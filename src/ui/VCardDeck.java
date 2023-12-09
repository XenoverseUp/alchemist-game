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
import enums.View;
import domain.IngredientCard;
import domain.TheAlchemistGame;

public class VCardDeck extends VComponent {
    private Router router = Router.getInstance();

    public VCardDeck(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        JPanel ingredientCardDeck = createCardDeck("ingredient", "Forage Ingredient");
        JPanel artifactCardDeck = createCardDeck("artifact", "Draw Artifact");

        ingredientCardDeck.setBounds(0, 0, Window.frame.getWidth() / 2, Window.frame.getHeight());
        artifactCardDeck.setBounds(Window.frame.getWidth() / 2, 0, Window.frame.getWidth() / 2,
                Window.frame.getHeight());

        BufferedImage bg = null;
        BufferedImage close = null;
        BufferedImage title = null;

        try {
            bg = ImageIO.read(new File("./src/resources/image/cardDeckBg.png"));
            close = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            title = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));

        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel bgPic = new JLabel(new ImageIcon(bg));
        bgPic.setBounds(0, 0, Window.frame.getWidth(), Window.frame.getHeight());

        JLabel titlePic = new JLabel(new ImageIcon(title.getScaledInstance((int) (title.getWidth() * 0.75),
                (int) (title.getHeight() * 0.75), Image.SCALE_SMOOTH)));
        titlePic.setBounds((int) (Window.frame.getWidth() / 2 - title.getWidth() / 2 * 0.75), -16,
                (int) (title.getWidth() * 0.75), (int) (title.getHeight() * 0.75));

        JLabel titleText = new JLabel("Card Deck");
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 24));
        titleText.setForeground(Color.white);
        titleText.setBounds(656, 16, 200, 20);

        JButton closePic = new JButton(new ImageIcon(close.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(event -> {
            router.to(View.Board);
        });

        panel.add(titleText);
        panel.add(titlePic);
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
            if (type == "ingredient") {
                ingredientCardPile = ImageIO.read(new File("./src/resources/image/ingredientCardPile.png"));
            } else
                artifactCardPile = ImageIO.read(new File("./src/resources/image/artifactCardPile.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        JLabel deckPic = new JLabel(new ImageIcon(
                type == "ingredient"
                        ? ingredientCardPile
                        : artifactCardPile));

        deckPic.setBounds(0, 0, pile.getWidth(), pile.getHeight());
        pile.add(deckPic);

        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(event -> {
            // add card to current user
            if (type == "ingredient")
                game.forageIngredient();
            else
                game.buyArtifact();

            router.to(View.Inventory);
        });

        // Adds the top three ingredient card of the ingredient deck
        if (type == "ingredient") {
            // check if elixir of insight is used
            if (true) {// !game.getCurrentUser().inventory.getArtifactCard("Elixir of
                       // Insight").equals(null)
                if (true) {// game.getCurrentUser().inventory.getArtifactCard("Elixir of
                           // Insight").getActivated()
                    JPanel insightCards = new JPanel();
                    insightCards.setLayout(new BoxLayout(insightCards, BoxLayout.X_AXIS));
                    insightCards.setBounds(20, 0, 50, 20);
                    insightCards.setOpaque(false);
                    JPanel card1 = createIngredientCard(game.getBoard().ingredientCardDeck.returnCard(1));
                    JPanel card2 = createIngredientCard(game.getBoard().ingredientCardDeck.returnCard(2));
                    JPanel card3 = createIngredientCard(game.getBoard().ingredientCardDeck.returnCard(3));
                    insightCards.add(card1);
                    insightCards.add(Box.createHorizontalStrut(5));
                    insightCards.add(card2);
                    insightCards.add(Box.createHorizontalStrut(5));
                    insightCards.add(card3);
                    cardPanel.add(insightCards);
                }
            }
        }

        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(pile);
        cardPanel.add(Box.createVerticalStrut(24));
        cardPanel.add(button);
        cardPanel.add(Box.createVerticalGlue());

        return cardPanel;

    }

    // Create a single visual of a card. May also need in inventory.
    private JPanel createIngredientCard(IngredientCard iCard) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setPreferredSize(new Dimension(80, 50));
        Image newImage = null;

        BufferedImage ingCard = null;
        try {
            ingCard = ImageIO.read(new File("./src/resources/image/ingredientCard.png"));
            newImage = ingCard.getScaledInstance(50, 70, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println(e);
        }
        JLabel cardPic = new JLabel(new ImageIcon(newImage));
        cardPic.setBounds(0, 0, cardPanel.getWidth(), cardPanel.getHeight());

        JLabel title = new JLabel(iCard.getName());
        title.setBounds(20, 20, 50, 20);

        cardPanel.add(title);
        cardPanel.add(cardPic);
        return cardPanel;
    }

}
