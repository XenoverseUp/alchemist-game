package ui;

import domain.TheAlchemistGame;

public abstract class VGameComponent extends VComponent {
    protected TheAlchemistGame game;

    protected VGameComponent(TheAlchemistGame game) {
        super();
        this.game = game;
    }

    protected abstract void buildView();
}
