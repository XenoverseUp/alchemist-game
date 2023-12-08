package ui;

import java.awt.Cursor;

import domain.TheAlchemistGame;
import enums.Avatar;

public class VBoard extends VComponent {
    public VBoard(TheAlchemistGame game) { super(game); }

    Canvas canvas;

    @Override
    protected void mounted() {
        canvas.start();
    }

    @Override
    protected void render() {
        // game.createUser("Ata", Avatar.Celestial);
        // game.createUser("Can", Avatar.Serene);
        canvas = new Canvas(game);
        canvas.setBounds(0, 0,  Window.frame.getWidth(), Window.frame.getHeight());
        panel.add(canvas);
    }

    @Override
    protected void unmounted() {
        canvas.stop();
        Window.frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
