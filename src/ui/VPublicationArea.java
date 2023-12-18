package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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

public class VPublicationArea extends VComponent {
    private Router router = Router.getInstance();

    public VPublicationArea(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        JPanel publicationPanel = createPublicationJPanel();
        JLabel text = new JLabel("Publication Area");
        text.setBounds(580, 20, 450, 50);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("SANS_SERIF", Font.BOLD, 30));

        BufferedImage bg = null;
        try {
            bg = ImageIO.read(new File("./src/resources/image/table.jpeg"));
        } catch (IOException e) {
            System.out.println(e);
        }

        Image scaledBg = bg.getScaledInstance((int)(bg.getWidth() * 1.49), (int)(bg.getHeight() * 1.3), Image.SCALE_SMOOTH);
        JLabel bgPic = new JLabel(new ImageIcon(scaledBg));
        bgPic.setBounds(0, 0, Window.frame.getWidth(), Window.frame.getHeight());

        panel.setLayout(null); 
        panel.add(publicationPanel);
        panel.add(text);
        panel.add(bgPic);

        int startX = 60; 
        int startY = 90;
        int imageWidth = 190; 
        int imageHeight = 260; 
        int gap = 30;
        
        for (int i = 0; i < 8; i++) {
            int x = startX + (i % 4) * (imageWidth + gap);
            int y = startY + (i / 4) * (imageHeight + gap);
        
            String imagePath = "./src/resources/image/pcard" + (i + 1) + ".png";
            addImageToPanel(publicationPanel, imagePath, x, y, imageWidth, imageHeight);
        }
        
    }

    private JPanel createPublicationJPanel() {
        JPanel publicationPanel = new JPanel();
        publicationPanel.setBounds(0, 0, Window.frame.getWidth(), Window.frame.getHeight());
        publicationPanel.setLayout(null); // Use absolute positioning
        publicationPanel.setOpaque(false);

        JButton back = new JButton("Back");
        back.setBounds(480, 690, 150, 30);
        back.addActionListener(event -> router.to(View.Board));
        publicationPanel.add(back);

        return publicationPanel;
    }

    private void addImageToPanel(JPanel panel, String imagePath, int x, int y, int width, int height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (img != null) {
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(icon);
            label.setBounds(x, y, width, height);
            panel.add(label);
        }
    }





}
