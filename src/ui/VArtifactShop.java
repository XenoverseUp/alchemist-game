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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.Game;
import enums.View;
import error.NotEnoughActionsException;
import ui.framework.VComponent;
import ui.util.WrapLayout;

public class VArtifactShop extends VComponent {
    private BufferedImage BMysteryCard = null;
    private BufferedImage BArtifactCardTemplate = null;

    public VArtifactShop(Game game) { super(game); }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.ArtifactShop);
        Image BClose = assetLoader.getClose();
        Image BTitle = assetLoader.getPageBanner();

        try {
            BArtifactCardTemplate = ImageIO.read(new File("./src/resources/image/artifactCard.png"));
            BMysteryCard = ImageIO.read(new File("./src/resources/image/mysteryCard.png"));
        } catch (IOException e) {
            System.out.println(e);
        }


        JLabel background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());

        JLabel title = new JLabel(new ImageIcon(BTitle));
        title.setBounds(windowDimension.getWidth() / 2 - BTitle.getWidth(null) / 2, -16, BTitle.getWidth(null), BTitle.getHeight(null));
        
        JLabel titleText = new JLabel("Artifact Shop", SwingConstants.CENTER);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 24));
        titleText.setForeground(Color.white);
        titleText.setBounds(title.getBounds());

        JButton closePic = new JButton(new ImageIcon(BClose.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(e -> router.navigateBack());

        JPanel mysteryCardPanel = createMysteryCardPanel();
        mysteryCardPanel.setBounds(0, 0, 664, windowDimension.getHeight());


        JScrollPane artifactContainer = createArtifactScrollContainer();
        artifactContainer.setBounds(664, 0, windowDimension.getWidth() - 664, windowDimension.getHeight());
        artifactContainer.setPreferredSize(new Dimension(windowDimension.getWidth() - 664, windowDimension.getHeight()));
        
        artifactContainer.revalidate();
        artifactContainer.repaint();

        panel.add(titleText);
        panel.add(title);
        panel.add(closePic);
        panel.add(mysteryCardPanel);
        panel.add(artifactContainer);
        panel.add(background);
        
    }

    private JPanel createMysteryCardPanel() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        JLabel title = new JLabel("Mystery Card", SwingConstants.CENTER);
        title.setFont(new Font("Crimson Pro", Font.BOLD, 32));
        title.setForeground(Color.white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mysteryCard = new JLabel(new ImageIcon(BMysteryCard));
        mysteryCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel(
            "<html><div style='text-align: center;'>Pick a mystery artifact card and prove your courage.</div></html>", 
            SwingConstants.CENTER
        );
        description.setMaximumSize(new Dimension(500, (int)description.getPreferredSize().getHeight()));
        description.setFont(new Font("Itim-Regular", Font.PLAIN, 14));
        description.setForeground(Color.white);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton drawMysteryButton = new JButton("Draw Mystery Card (5 Gold)");
        drawMysteryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        drawMysteryButton.addActionListener(e -> {
            int result;
            try {
                result =  game.getRegister().drawMysteryCard();
                if (result == 0) router.to(View.Inventory);
                else modal.info("Not enough money!", "Fuck you bitch.");
            } catch (NotEnoughActionsException e1) {
                modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
            }
           
        });

        container.add(Box.createVerticalGlue());
        container.add(title);
        container.add(Box.createVerticalStrut(12));
        container.add(description);
        container.add(Box.createVerticalStrut(24));
        container.add(mysteryCard);
        container.add(Box.createVerticalStrut(24));
        container.add(drawMysteryButton);
        container.add(Box.createVerticalGlue());
        
        return container;
    }

    private JScrollPane createArtifactScrollContainer() {
        JPanel container = new JPanel(new WrapLayout(FlowLayout.CENTER, 24, 24));
        container.setOpaque(false);

        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(windowDimension.getWidth() - 664, 8));
        marginTop.setOpaque(false);
        container.add(marginTop);

        JLabel title = new JLabel("Artifact Cards", SwingConstants.CENTER);
        title.setFont(new Font("Crimson Pro", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(windowDimension.getWidth() - 664, 100));
        container.add(title);

        game.getRegister().getArtifactCardDeck()
            .stream()
            .map(card -> this.generateArtifactCard(card.getName(), card.getDescription(), card.getPrice(), card.getVictoryPoints()))
            .forEach(container::add);

        JPanel marginBottom = new JPanel();
        marginBottom.setPreferredSize(new Dimension(windowDimension.getWidth() - 664, 20));
        marginBottom.setOpaque(false);
        container.add(marginBottom);

        
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        return scrollPane;
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

        JButton activateButton = new JButton("Buy");
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
            int result;
            try {
                result = game.getRegister().buyArtifact(name);
                if (result == 0) router.to(View.Inventory);
                else modal.info("Not enough money!", "Fuck you bitch.");
            } catch (NotEnoughActionsException e) {
                modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
            }

        });

        card.add(activateButton);
        card.add(bg);

        return card;
    }


    
}
