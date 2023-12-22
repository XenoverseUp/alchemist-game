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

import domain.TheAlchemistGame;
import enums.View;
import ui.framework.VComponent;
import ui.util.WrapLayout;

public class VArtifactShop extends VComponent {
    private BufferedImage BMysteryCard = null;

    public VArtifactShop(TheAlchemistGame game) { super(game); }

    @Override
    protected void render() {
        BufferedImage bg = null;
        BufferedImage close = null;
        BufferedImage title = null;
        BufferedImage BArtifactCardTemplate = null;

        try {
            bg = ImageIO.read(new File("./src/resources/image/cardDeckBg.png"));
            close = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            title = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
            BArtifactCardTemplate = ImageIO.read(new File("./src/resources/image/artifactCard.png"));
            BMysteryCard = ImageIO.read(new File("./src/resources/image/mysteryCard.png"));
        } catch (IOException e) {
            System.out.println(e);
        }


        JLabel bgPic = new JLabel(new ImageIcon(bg));
        bgPic.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());

        JLabel titlePic = new JLabel(new ImageIcon(title.getScaledInstance((int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75), Image.SCALE_SMOOTH)));
        titlePic.setBounds((int)(windowDimension.getWidth() / 2 - title.getWidth() / 2 * 0.75), -16, (int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75));
        
        JLabel titleText = new JLabel("Artifact Shop", SwingConstants.CENTER);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 24));
        titleText.setForeground(Color.white);
        titleText.setBounds(titlePic.getBounds());

        JButton closePic = new JButton(new ImageIcon(close.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(e -> router.navigateBack());

        JPanel mysteryCardPanel = createMysteryCardPanel();
        mysteryCardPanel.setBounds(0, 0, 664, windowDimension.getHeight());


        JPanel artifactContainer = createArtifactScrollContainer();
        artifactContainer.setBounds(664, 0, windowDimension.getWidth() - 664, windowDimension.getHeight());
        

        panel.add(titleText);
        panel.add(titlePic);
        panel.add(closePic);
        panel.add(mysteryCardPanel);
        panel.add(artifactContainer);
        panel.add(bgPic);
        
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
            int result = game.drawMysteryCard();
            if (result == 0) router.to(View.Inventory);
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

    private JPanel createArtifactScrollContainer() {
        JPanel container = new JPanel(new WrapLayout());
        container.setOpaque(false);

        return container;
    }
    
}
