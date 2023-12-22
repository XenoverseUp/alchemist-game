package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enums.View;
import ui.framework.VComponent;
import domain.TheAlchemistGame;

public class VPublicationArea extends VComponent {

    public VPublicationArea(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        JPanel publicationPanel = createPublicationJPanel();
        JLabel text = new JLabel("Publication Area");
        text.setBounds(500, 10, 200, 30);

        BufferedImage bg = null;
        try {
            bg = ImageIO.read(new File("./src/resources/image/fantastic-library-bg.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel bgPic = new JLabel(new ImageIcon(bg));
        bgPic.setBounds(-200, -50, windowDimension.getWidth(), windowDimension.getHeight());

        panel.setLayout(null); // Use absolute positioning
        panel.add(publicationPanel);
        panel.add(text);
        panel.add(bgPic);
    }

    private JPanel createPublicationJPanel() {
        JPanel publicationPanel = new JPanel();
        publicationPanel.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());
        publicationPanel.setLayout(null); // Use absolute positioning
        publicationPanel.setOpaque(false);

        JButton back = new JButton("Back");
        back.setBounds(480, 650, 150, 30);
        back.addActionListener(event -> router.to(View.Board));

        JButton debunkTheory = new JButton("Display Available Publication Cards");
        debunkTheory.setBounds(400, 370, 300, 30);
        debunkTheory.addActionListener(event -> {
            router.to(View.DeductionBoard);
        });

        publicationPanel.add(back);
        publicationPanel.add(debunkTheory);

        return publicationPanel;
    }

}
