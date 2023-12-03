package ui;

import domain.TheAlchemistGame;

/**
 * @abstract
 * 
 * This class initializes the `TheAlchemistGame` instance. It also has an abstract `buildView` method 
 * that is responsible for filling in the panel.
 * 
 * This abstract class is suitable for views that have to access `TheAlchemistGame` game instance.
 * 
 * @see VLogin.java
 * 
 */

public abstract class VGameComponent extends VComponent {
    protected TheAlchemistGame game;

    protected VGameComponent(TheAlchemistGame game) {
        super();
        this.game = game;
    }

    protected abstract void render();
}
