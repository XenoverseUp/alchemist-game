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

public class VStart extends VComponent {
    protected void render() {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("./src/resources/image/start.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(new Rectangle(0,0,Window.window.getWidth(), Window.window.getHeight()));
        panel.add(background);

        JButton startButton = new JButton("Start");
        startButton.setBounds(new Rectangle(629, 632, 183, 60));
        panel.add(startButton);


        startButton.addActionListener(event -> {
            Window.router.setView(View.Login);
        });
    }
}