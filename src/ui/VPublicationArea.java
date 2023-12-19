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
        Font f3  = new Font(Font.DIALOG_INPUT, Font.BOLD, 30);
        text.setFont(f3);

        JLabel text1 = new JLabel("Alchemy Markers");
        text1.setBounds(1130, 320, 450, 50);
        text1.setForeground(Color.WHITE);
        Font f4  = new Font(Font.DIALOG_INPUT, Font.BOLD, 17);
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
        int imageWidth = 190; 
        int imageHeight = 260; 
        int gap = 30;
        
        for (int i = 0; i < 8; i++) {
            int x = startX + (i % 4) * (imageWidth + gap);
            int y = startY + (i / 4) * (imageHeight + gap);
        
            String imagePath = "./src/resources/image/pcard" + (i + 1) + ".png";
            addImageToPanel(publicationPanel, imagePath, x, y, imageWidth, imageHeight);
        }
    
        createButtonWithImage(publicationPanel, "./src/resources/image/marker1.png", 1070, 370, 70, 70 /* action for button 1 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker2.png", 1170, 370, 70, 70 /* action for button 2 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker3.png", 1270, 370, 70, 70 /* action for button 3 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker4.png", 1070, 480, 70, 70 /* action for button 4 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker5.png", 1170, 480, 70, 70 /* action for button 5 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker6.png", 1270, 480, 70, 70 /* action for button 6 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker7.png", 1120, 590, 70, 70 /* action for button 7 */);
        createButtonWithImage(publicationPanel, "./src/resources/image/marker8.png", 1220, 590, 70, 70 /* action for button 8 */);
        
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
            
            JButton button = new JButton(icon);
            button.setBounds(x, y, width, height);
            button.setBorderPainted(false); 
            button.setContentAreaFilled(false); 
            button.setFocusPainted(false); 
    
            
            button.addActionListener(event -> {
                
                System.out.println("Button clicked: " + imagePath);
            });
    
            panel.add(button);
        }
    }

    private void createButtonWithImage(JPanel panel, String imagePath, int x, int y, int width, int height /* ,ActionListener action */) {
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
    
            
            //button.addActionListener(action);
    
            panel.add(button);
        }
    }
    }






