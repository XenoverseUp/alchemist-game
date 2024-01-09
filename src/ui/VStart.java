package ui;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import enums.View;
import ui.framework.VComponent;

public class VStart extends VComponent {

    @Override
    protected void render() {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("./src/resources/image/start.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(new Rectangle(0, 0, windowDimension.getWidth(), windowDimension.getHeight()));
        
        JButton quickStartButton = new JButton();
        quickStartButton.setBounds(new Rectangle(452, 630, 308, 65));
        quickStartButton.setOpaque(false);
        quickStartButton.setContentAreaFilled(false);
        quickStartButton.setBorderPainted(false);
        quickStartButton.setFocusable(false);
        
        quickStartButton.addActionListener(event -> 
            router.to(View.Login)
        );
        
        JButton onlineButton = new JButton();
        onlineButton.setBounds(new Rectangle(776, 630, 214, 65));
        onlineButton.setOpaque(false);
        onlineButton.setContentAreaFilled(false);
        onlineButton.setBorderPainted(false);
        onlineButton.setFocusable(false);
        
        onlineButton.addActionListener(event -> 
            router.to(View.OnlineSelection)
        );

        panel.add(onlineButton);
        panel.add(quickStartButton);
        panel.add(background);
    }
}
