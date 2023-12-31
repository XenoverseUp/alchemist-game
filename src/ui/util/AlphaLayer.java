package ui.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

/**
 *  Related StackOverflow thread: 
 *  https://stackoverflow.com/questions/72494708/how-to-create-a-semi-transparent-jbutton
 */

public class AlphaLayer extends LayerUI<JComponent> {
    private BufferedImage mOffscreenImage;
    private BufferedImageOp mOperation;
    
    private float alpha;

    public AlphaLayer(float alpha) {            
        this.alpha = alpha;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();

        if (w == 0 || h == 0) {
            return;
        }

        // Only create the off-screen image if the one we have
        // is the wrong size.
        if (mOffscreenImage == null
                || mOffscreenImage.getWidth() != w
                || mOffscreenImage.getHeight() != h) {
            mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D ig2 = mOffscreenImage.createGraphics();
        ig2.setComposite(AlphaComposite.SrcOver.derive(alpha));
        ig2.setClip(g.getClip());
        super.paint(ig2, c);
        ig2.dispose();

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(mOffscreenImage, mOperation, 0, 0);
    }
}