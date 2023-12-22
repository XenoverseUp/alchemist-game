package ui.framework;

import java.awt.Color;
import javax.swing.JPanel;

import domain.TheAlchemistGame;
import interfaces.IRenderable;

/**
 * @abstract
 * 
 * This class initializes the JPanel for rendering in the router and implements
 * `getContentPanel` method. It also has an abstract `render` method that is responsible 
 * for filling in the panel.
 * 
 * This abstract class is suitable for views that don't have to access `TheAlchemistGame` 
 * game instance.
 * 
 * @see VStart.java
 * 
 * 
 * NOTICE: `render` method is called right after the initialization of the VComponent. Which is 
 * the initialization of the program, not when the VComponent is rendered on the Window.
 * 
 */
public abstract class VComponent implements IRenderable {
    protected JPanel panel = new JPanel();
    protected TheAlchemistGame game = null;
    protected Router router = Router.getInstance();
    protected WindowDimension windowDimension = WindowDimension.getInstance();

    protected VComponent() {
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
    }

    protected VComponent(TheAlchemistGame game) {
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        this.game = game;
    }

    protected abstract void render();

    /**
     * To run a piece of code when the VComponent is shown to users, override the `mounted` method.
     * @see VBoard.java
     */
    protected void mounted() {}
    
    
    /**
     * To run some code when a VComponent is unmounted, override the `unmounted` method.
     * @see VBoard.java
     */
    protected void unmounted() {}

    @Override
    public JPanel getContentPane() {
        return this.panel;
    }
}
