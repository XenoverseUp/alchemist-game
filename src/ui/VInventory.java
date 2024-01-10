package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.ArtifactCard;
import domain.TheAlchemistGame;
import enums.View;
import error.NotEnoughActionsException;
import ui.framework.VComponent;
import ui.util.WrapLayout;


public class VInventory extends VComponent {
    private JLabel titleText;
    private JLabel background;

    private JScrollPane scrollPane;
    private int scrollPosition = 0;

    private BufferedImage BHammer;
    private BufferedImage BArtifactCardTemplate;
    private BufferedImage BDiscardArtifactCard;


    public VInventory(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.Inventory);
        Image BClose = assetLoader.getClose();
        Image BTitle = assetLoader.getPageBanner();

        try {
            BHammer =  ImageIO.read(new File("./src/resources/image/HUD/hammer.png"));
            BArtifactCardTemplate = ImageIO.read(new File("./src/resources/image/artifactCard.png"));
            BDiscardArtifactCard = ImageIO.read(new File("./src/resources/image/HUD/discardArtifact.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel title = new JLabel(new ImageIcon(BTitle));
        title.setBounds(windowDimension.getWidth() / 2 - BTitle.getWidth(null) / 2 , -16, BTitle.getWidth(null), BTitle.getHeight(null));

        titleText = new JLabel("",  SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds(title.getBounds());

        JButton close = new JButton(new ImageIcon(BClose));
        close.setBounds(10, 10, BClose.getWidth(null), BClose.getHeight(null));
        close.addActionListener(e -> router.to(View.Board));

        background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());
        scrollPane.setPreferredSize(windowDimension.getSize());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(titleText);
        panel.add(title);
        panel.add(close);
        panel.add(scrollPane);
        panel.add(background);
    }

    @Override
    protected void mounted() {
        this.update();
    }

    @Override
    protected void unmounted() {
        this.scrollPosition = 0;
        scrollPane.getVerticalScrollBar().setValue(this.scrollPosition);
    }

    private void update() {
        this.scrollPosition = scrollPane.getVerticalScrollBar().getValue();
        scrollPane.setViewportView(null);

        this.titleText.setText(String.format("%s's Inventory", game.online.getCurrentUser().get("name")));

        JPanel cards = new JPanel(new WrapLayout(FlowLayout.CENTER, 24, 24));
        cards.setOpaque(false);

        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 20));
        marginTop.setOpaque(false);
        cards.add(marginTop);

        JLabel artifactsTitle = new JLabel("Artifact Cards", SwingConstants.CENTER);
        artifactsTitle.setFont(new Font("Crimson Pro", Font.BOLD, 36));
        artifactsTitle.setForeground(Color.WHITE);
        artifactsTitle.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
        cards.add(artifactsTitle);


        if (game.isOnline()) {
            if (game.online.getCurrentPlayerArtifacts().size() > 0)
                game.online
                    .getCurrentPlayerArtifacts()
                    .stream()
                    .map(name -> {
                        System.out.println(name);
                        ArtifactCard card = game.getArtifactCardDeck()
                            .stream()
                            .filter(c -> c.getName().equals(name))
                            .findFirst()
                            .get();

                        return this.generateArtifactCard(card.getName(), card.getDescription(), card.getPrice(), card.getVictoryPoints());
                    })
                    .forEach(cards::add);
        } else {
            if (game.getCurrentUser().inventory.getArtifactCards().size() > 0)
                game.getCurrentUser()
                    .inventory
                    .getArtifactCards()
                    .stream()
                    .map(card -> card.getName())
                    .map(name -> {
                        ArtifactCard card = game.getArtifactCardDeck()
                            .stream()
                            .filter(c -> c.getName().equals(name))
                            .findFirst()
                            .get();

                        return this.generateArtifactCard(card.getName(), card.getDescription(), card.getPrice(), card.getVictoryPoints());
                    })
                    .forEach(cards::add);
        }


        if ((game.isOnline() ? game.online.getCurrentPlayerArtifacts() : game.getCurrentUser().inventory.getArtifactCards()).size() == 0) {
            JLabel emptyIngredientsText = new JLabel("You don't have any artifact cards. Visit the card deck to buy.", SwingConstants.CENTER);
            emptyIngredientsText.setFont(new Font("Itim-Regular", Font.PLAIN, 18));
            emptyIngredientsText.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
            emptyIngredientsText.setForeground(Color.WHITE);
            cards.add(emptyIngredientsText);
        }
        

        JLabel ingredientsTitle = new JLabel("Ingredient Cards", SwingConstants.CENTER);
        ingredientsTitle.setFont(new Font("Crimson Pro", Font.BOLD, 36));
        ingredientsTitle.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
        ingredientsTitle.setForeground(Color.WHITE);
        cards.add(ingredientsTitle);


        if (game.isOnline()) {
            if (game.online.getCurrentPlayerIngredients().size() > 0)
                game.online
                    .getCurrentPlayerIngredients()
                    .stream()
                    .map(name -> this.generateIngredientCard(name))
                    .forEach(cards::add);
        } else {
            if (game.getCurrentUser().inventory.getIngredientCards().size() > 0)
                game.getCurrentUser()
                    .inventory
                    .getIngredientCards()
                    .stream()
                    .map(card -> card.getName())
                    .map(name -> this.generateIngredientCard(name))
                    .forEach(cards::add);
        }

        if ((game.isOnline() ? game.online.getCurrentPlayerIngredients() :game.getCurrentUser().inventory.getIngredientCards()).size() == 0) {
             JLabel emptyIngredientsText = new JLabel("You don't have any ingredient cards. Visit the card deck to forage.", SwingConstants.CENTER);
            emptyIngredientsText.setFont(new Font("Itim-Regular", Font.PLAIN, 18));
            emptyIngredientsText.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
            emptyIngredientsText.setForeground(Color.WHITE);
            cards.add(emptyIngredientsText);
        }

        JPanel marginBottom = new JPanel();
        marginBottom.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
        marginBottom.setOpaque(false);
        cards.add(marginBottom);

        scrollPane.setViewportView(cards);
        scrollPane.getVerticalScrollBar().setValue(this.scrollPosition);
        scrollPane.revalidate();
        scrollPane.repaint();
    }


    private JPanel generateIngredientCard(String name) {
        BufferedImage cardBuffer = assetLoader.getIngredientCard(name);

        JPanel card = new JPanel(null);
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(cardBuffer.getWidth(), cardBuffer.getHeight()));

        JLabel bg = new JLabel(new ImageIcon(cardBuffer));
        bg.setBounds(0, 0, cardBuffer.getWidth(), cardBuffer.getHeight());

        JButton transmuteButton = new JButton(new ImageIcon(BHammer.getScaledInstance(BHammer.getWidth() / 2, BHammer.getHeight() / 2, Image.SCALE_SMOOTH)));
        transmuteButton.setBounds(cardBuffer.getWidth() / 2 - BHammer.getWidth() / 4 , cardBuffer.getHeight() - BHammer.getHeight() / 2 - 36, BHammer.getWidth() / 2, BHammer.getHeight() / 2);
        transmuteButton.setOpaque(false);
        transmuteButton.setContentAreaFilled(false);
        transmuteButton.setBorderPainted(false);
        transmuteButton.setFocusable(false);
        transmuteButton.addActionListener(event -> {
            try {
                if (game.isOnline()) game.online.transmuteIngredient(name);
                else game.transmuteIngredient(name);
                this.update();
            } catch (NotEnoughActionsException e) {
                modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
            }
        });

        card.add(transmuteButton);
        card.add(bg);

        return card;
    }


    private JPanel generateArtifactCard(String name, String desc, int price, int vPoints) {
        JPanel card = new JPanel(null);
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(BArtifactCardTemplate.getWidth(), BArtifactCardTemplate.getHeight()));

        JLabel bg = new JLabel(new ImageIcon(BArtifactCardTemplate));
        bg.setBounds(0, 0, BArtifactCardTemplate.getWidth(), BArtifactCardTemplate.getHeight());


        int maxTitleWidth = 208;
        JLabel title = new JLabel(String.format("<html><div style='text-align: center;'>%s</div></html>", name), SwingConstants.CENTER);
        title.setBounds((int)card.getPreferredSize().getWidth() / 2 - maxTitleWidth / 2, 68, maxTitleWidth, 68);
        title.setFont(new Font("Crimson Pro", Font.PLAIN, 28));
        title.setForeground(Color.white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        int maxDescriptionWidth = 183;
        JLabel description = new JLabel(
            String.format("<html><div style='text-align: center;'>%s</div></html>", desc), 
            SwingConstants.CENTER
        );
        description.setBounds((int)card.getPreferredSize().getWidth() / 2 - maxDescriptionWidth / 2, 129, maxDescriptionWidth, 100);
        description.setMaximumSize(new Dimension(183, Integer.MAX_VALUE));
        description.setFont(new Font("Crimson Pro", Font.PLAIN, 16));
        description.setForeground(Color.white);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(description);


        JButton discard = new JButton(new ImageIcon(BDiscardArtifactCard));
        discard.setBounds(
            (int)card.getPreferredSize().getWidth() / 2 - BDiscardArtifactCard.getWidth() / 2,
            48,
            BDiscardArtifactCard.getWidth(), 
            BDiscardArtifactCard.getHeight()
        );

        discard.addActionListener(event -> {
            try {
                if (game.isOnline()) game.online.discardArtifact(name);
                else game.discardArtifact(name);
                this.update();  
            } catch (NotEnoughActionsException e) {
                modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
            }
                      
        });

        discard.setBorderPainted(false); 
        discard.setContentAreaFilled(false); 
        discard.setFocusPainted(false); 
        discard.setOpaque(false);
        card.add(discard);

        JLabel cost = new JLabel(Integer.toString(price));
        cost.setBounds(108, 267, 100, 36);
        cost.setFont(new Font("Crimson Pro", Font.PLAIN, 32));
        cost.setForeground(Color.white);
        card.add(cost);
        
        JLabel victoryPoint = new JLabel(Integer.toString(vPoints));
        victoryPoint.setBounds(236, 267, 100, 36);
        victoryPoint.setFont(new Font("Crimson Pro", Font.PLAIN, 32));
        victoryPoint.setForeground(Color.white);
        card.add(victoryPoint);

        JButton activateButton = new JButton("Activate");
        activateButton.setBounds(
            BArtifactCardTemplate.getWidth() / 2 - 92 / 2,
            BArtifactCardTemplate.getHeight() - 88,
            92,
            30
        );
        activateButton.setFont(new Font("Crimson Pro", Font.BOLD, 13));
        activateButton.setForeground(Color.white);
        activateButton.setBackground(new Color(9, 11, 14));
        activateButton.setOpaque(true);
        activateButton.setFocusable(false);
        activateButton.setBorderPainted(false);

        activateButton.addActionListener(event -> {
            // game.activateArtifact(name);
            this.update();
        });

        card.add(activateButton);
        card.add(bg);

        return card;
    }
}
