package ui;

import domain.TheAlchemistGame;

public class VBoard extends VGameComponent {
    public VBoard(TheAlchemistGame game) { super(game); }

    Canvas canvas;

    @Override
    protected void mounted() {
        canvas.start();
    }

    @Override
    protected void render() {
        canvas = new Canvas();
        canvas.setBounds(0, 0,  Window.frame.getWidth(), Window.frame.getHeight());
        panel.add(canvas);
    }

    @Override
    protected void unmounted() {
        canvas.stop();
    }
}
