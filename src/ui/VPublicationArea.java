package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
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
    protected void mounted() {
        this.update();
    }

    @Override
    protected void render() {
    }
    
    private void update() {
        
        JPanel publicationPanel = createPublicationJPanel();
        JLabel text = new JLabel("Publication Area");
        text.setBounds(580, 20, 450, 50);
        text.setForeground(Color.WHITE);
        Font f3 = new Font(Font.DIALOG_INPUT, Font.BOLD, 30);
        text.setFont(f3);

        JLabel text1 = new JLabel("Alchemy Markers");
        text1.setBounds(1130, 320, 450, 50);
        text1.setForeground(Color.WHITE);
        Font f4 = new Font(Font.DIALOG_INPUT, Font.BOLD, 17);
        text1.setFont(f4);

        BufferedImage bg = null;
        try {
            bg = ImageIO.read(new File("./src/resources/image/table.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        int desiredWidth = 1450;
        int desiredHeight = 750;

        Image scaledBg = bg.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        JLabel bgPic = new JLabel(new ImageIcon(scaledBg));
        bgPic.setBounds(0, 0, desiredWidth, desiredHeight);

        panel.setLayout(null);
        panel.add(publicationPanel);
        panel.add(text);
        panel.add(text1);
        panel.add(bgPic);

        int startX = 60;
        int startY = 90;
        int imageWidth = 170;
        int imageHeight = 260;
        int gap = 30;

        String[] ingredientCardNames = {"birdClaw", "fern", "mandrakeRoot", "moonshade", "mushroom", "ravensFeather", "scorpionTail", "wartyToad"};
        
        for (int i = 0; i < ingredientCardNames.length; i ++) {
            int x = startX + (i % 4) * (imageWidth + gap);
            int y = startY + (i / 4) * (imageHeight + gap);

            String imagePath = "./src/resources/image/" + ingredientCardNames[i] + ".png";
            int a = i;
            String ingredientCardName = ingredientCardNames[i];
            addImageToPanel(i, publicationPanel, imagePath, x, y, imageWidth, imageHeight, (e -> {
                game.setCard(a);
                System.out.println(ingredientCardName + " is clicked!");
                
            }));
        }

        createButtonWithImage(publicationPanel, "./src/resources/image/marker1.png", 1070, 370, 75, 70,
                (e -> {
                    game.setMarker(1);
                    System.out.println("Clicked marker1!");
                    
                }));// molecule id:1

        createButtonWithImage(publicationPanel, "./src/resources/image/marker5.png", 1170, 370, 70, 70,
                (e -> {
                    game.setMarker(5);
                    System.out.println("Clicked marker5!");
                    
                }));// molecule id:5

        createButtonWithImage(publicationPanel, "./src/resources/image/marker0.png", 1270, 370, 75, 70,
                (e -> {
                    game.setMarker(0);
                    System.out.println("Clicked marker0!");
                    
                }));// molecule id: 0
        createButtonWithImage(publicationPanel, "./src/resources/image/marker3.png", 1070, 480, 75, 70,
                (e -> {
                    game.setMarker(3);
                    System.out.println("Clicked marker3!");
                    
                }));// molecule id:3

        createButtonWithImage(publicationPanel, "./src/resources/image/marker6.png", 1170, 480, 70, 70,
                (e -> {
                    game.setMarker(6);
                    System.out.println("Clicked marker6!");
                   
                }));// molecule id:6

        createButtonWithImage(publicationPanel, "./src/resources/image/marker7.png", 1270, 480, 75, 70,
                (e -> {
                    game.setMarker(7);
                    System.out.println("CLicked marker7!");
                    
                }));// molecule id:7

        createButtonWithImage(publicationPanel, "./src/resources/image/marker4.png", 1120, 590, 75, 70,
                (e -> {
                    game.setMarker(4);
                    System.out.println("Clicked marker4!");
                    
                }));// molecule id:4

        createButtonWithImage(publicationPanel, "./src/resources/image/marker2.png", 1220, 590, 75, 70,
                (e -> {
                    game.setMarker(2);
                    System.out.println("CLicked marker2!");
                   
                }));// molecule id :2
        
        
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

        JButton publishTheory = new JButton("Publish Theory");
        publishTheory.addActionListener(e -> game.publishTheory());
        publishTheory.setBounds(1130, 200, 150, 30);
        panel.add(publishTheory);

        JButton debunkTheory = new JButton("Debunk Theory");
        debunkTheory.addActionListener(e -> game.debunkTheory());
        debunkTheory.setBounds(1130, 100, 150, 30);
        panel.add(debunkTheory);

        panel.revalidate();
        panel.repaint();

        return publicationPanel;
    }

    private void addImageToPanel(int id, JPanel panel, String imagePath, int x, int y, int width, int height, ActionListener action) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (img != null) {
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JButton button = new JButton(icon);
            button.setBounds(x, y, width, height);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.addActionListener(action);
            panel.add(button);
        }
        
    }


    private void createButtonWithImage(JPanel panel, String imagePath, int x, int y, int width, int height,
            ActionListener action) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (img != null) {
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JButton button = new JButton(icon);
            button.setBounds(x, y, width, height);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);

            button.addActionListener(action);

            panel.add(button);
        }

    }    
    
}
