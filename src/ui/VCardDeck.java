package ui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import enums.View;

import domain.TheAlchemistGame;

public class VCardDeck extends VComponent {
    private Router router = Router.getInstance();

    public VCardDeck(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        JPanel ingredientCardDeck = createCardDeck("Ingredient Card Deck");
        JPanel artifactCardDeck = createCardDeck("Artifact Card Deck");

        ingredientCardDeck.setBounds(0, 0, Window.frame.getWidth() / 2, Window.frame.getHeight());
        artifactCardDeck.setBounds(Window.frame.getWidth() / 2, 0, Window.frame.getWidth() / 2,
                Window.frame.getHeight());

        panel.add(ingredientCardDeck);
        panel.add(artifactCardDeck);
    }

    private JPanel createCardDeck(String t) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(t);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("Draw");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(event -> {
            // add card to current user
            router.to(View.Board);
        });

        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(24));
        // Card Deck
        cardPanel.add(Box.createVerticalStrut(24));
        cardPanel.add(button);
        cardPanel.add(Box.createVerticalGlue());

        return cardPanel;

    }

}
