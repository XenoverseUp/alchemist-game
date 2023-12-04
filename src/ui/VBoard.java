package ui;

import domain.TheAlchemistGame;

public class VBoard extends VGameComponent {
    public VBoard(TheAlchemistGame game) { super(game); }


    @Override
    protected void render() {
        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0,  Window.frame.getWidth(), Window.frame.getHeight());
        panel.add(canvas);
    }

   }
