package ui;

import java.util.HashMap;

import javax.swing.JLabel;

import domain.TheAlchemistGame;
import enums.BroadcastAction;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import ui.framework.VComponent;

public class VLobby extends VComponent implements IBroadcastListener {
    public VLobby(TheAlchemistGame game) { 
        super(game);
    }
    private JLabel id;

    @Override
    protected void render() {
        id = new JLabel();
        id.setBounds(0, 0, 200, 30);

        panel.add(id);
    }

    @Override
    protected void mounted() {
        game.addBroadcastListener(this);
    }

    @Override
    public void onBroadcast(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        if (action == BroadcastAction.PLAYER_CREATED) 
            id.setText(String.format("You are the %s.", game.getId() == 0 ? "host" : "client #" + game.getId()));
    }
}
