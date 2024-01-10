package ui;

import java.awt.Cursor;

import domain.Game;
import domain.TheAlchemistGame;
import ui.framework.VComponent;

public class VBoard extends VComponent {
    private Canvas canvas;
    public VBoard(Game game) { super(game); }

    @Override
    protected void mounted() {
        canvas.start();
    }

    @Override
    protected void render() {
        canvas = new Canvas(game);
        canvas.setBounds(0, 0,  windowDimension.getWidth(), windowDimension.getHeight());
        panel.add(canvas);
    }

    @Override
    protected void unmounted() {
        canvas.stop();
        Window.frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
