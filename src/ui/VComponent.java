package ui;

import java.awt.Color;
import javax.swing.JPanel;
import interfaces.Renderable;

/**
 * @abstract
 * 
 * This class initializes the JPanel for rendering in the router and implements
 * `getContentPanel` method. It also has an abstract `buildView` method that is responsible 
 * for filling in the panel.
 * 
 * This abstract class is suitable for views that don't have to access `TheAlchemistGame` 
 * game instance.
 * 
 * @see VStart.java
 * 
 */
public abstract class VComponent implements Renderable {
    protected JPanel panel = new JPanel();

    protected VComponent() {
        panel.setBackground(Color.WHITE);
        panel.setSize(Window.frame.getSize());
        panel.setLayout(null);
        this.render();
    }

    protected abstract void render();

    @Override
    public JPanel getContentPanel() {
        return this.panel;
    }
}
