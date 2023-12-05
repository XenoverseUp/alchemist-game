package ui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import domain.TheAlchemistGame;


public class VInventory extends VGameComponent {
    private Router router = Router.getInstance();

    public VInventory(TheAlchemistGame game) { super(game); }
    
    @Override
    protected void render() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel text = new JLabel("Inventory");
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        
        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(event -> {
            router.navigateBack();
        });
        
        panel.add(Box.createVerticalGlue());
        panel.add(text);
        panel.add(Box.createVerticalStrut(10));
        panel.add(back);
        panel.add(Box.createVerticalGlue());
    }
    
    @Override
    protected void mounted() {
        // Note: Using `mounted` would be beneficial to retrieve the most recent data from game.
    }
}
