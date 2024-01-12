package ui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import domain.Game;
import enums.View;
import ui.framework.VComponent;

public class VFinalScore extends VComponent {

    public VFinalScore(Game game) { super(game); }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.FinalScore);
        JLabel background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, BBackground.getWidth(), BBackground.getHeight());

        panel.add(background);
    }
    
}
