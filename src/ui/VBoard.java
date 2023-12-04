package ui;

import domain.TheAlchemistGame;

public class VBoard extends VGameComponent {
    public VBoard(TheAlchemistGame game) { super(game); }
    
    Canvas canvas;

    @Override
    protected void render() {
        int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
        int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);
        canvas = new Canvas();
        canvas.setBounds(0, 0, width, height);
        panel.add(canvas);
    }

   }
