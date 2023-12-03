package UI;

import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;
import domain.TheAlchemistGame;

public class VBoard extends VGameComponent {
    public VBoard(TheAlchemistGame game) { super(game); }

    @Override
    protected void render() {
        // !TODO: Replace with Graphics2D canvas.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel text = new JLabel("Game Board");
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button = new JButton("Click");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(text);
        panel.add(button);
        panel.add(Box.createVerticalGlue());
    }
}
