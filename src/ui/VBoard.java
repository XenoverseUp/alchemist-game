package ui;

import java.awt.Cursor;
import java.util.HashMap;

import domain.Game;
import enums.BroadcastAction;
import enums.View;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import ui.framework.VComponent;

public class VBoard extends VComponent implements IBroadcastListener {
    private Canvas canvas;
    public VBoard(Game game) { super(game); }

    @Override
    protected void mounted() {
        canvas.start();
    }

    @Override
    protected void listenBroadcast() {
        if (game.isOnline()) game.addBroadcastListener(this);
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

    @Override
    public void onBroadcast(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        if (action == BroadcastAction.PLAYER_TOGGLED) canvas.togglePlayer();
        else if (action == BroadcastAction.GAME_FINISHED) {
            router.to(View.FinalScore);
        }
    }
}
