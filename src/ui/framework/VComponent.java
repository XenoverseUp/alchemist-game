package ui.framework;

import java.awt.Color;
import javax.swing.JPanel;

import domain.Game;
import interfaces.IRenderable;
import ui.framework.ModalController.Modal;

/**
 * @abstract
 * 
 * This class initializes the JPanel for rendering in the router and implements
 * `getContentPane` method. It also has an abstract `render` method that is responsible 
 * for filling in the panel on initialization (prerendering).
 * 
 * If you don't need game instance in the view, simply don't create any constructor. 
 * @see VStart.java
 * 
 * If you need the game instance, create a constructor that passes tha game instance to VComponent.
 * @see Login.java
 * 
 * This class has Router, WindowDimension and Modal initialized so that anything that extends it has access
 * to these shared resources.
 * 
 * 
 * NOTICE: `render` method is called right after the initialization of the VComponent. Which is 
 * the initialization of the program, not when the VComponent is rendered on the Window.
 * 
 */
public abstract class VComponent implements IRenderable {
    protected JPanel panel = new JPanel();
    protected Game game = null;
    protected Router router = Router.getInstance();
    protected WindowDimension windowDimension = WindowDimension.getInstance();
    protected Modal modal = ModalController.getModalInstance();
    protected AssetLoader assetLoader = AssetLoader.getInstance();

    protected VComponent() {
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
    }

    protected VComponent(Game game) {
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

    protected void listenBroadcast() {}

    @Override
    public JPanel getContentPane() {
        return this.panel;
    }
}
