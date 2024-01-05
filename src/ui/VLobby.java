package ui;

import javax.swing.JLabel;

import domain.TheAlchemistGame;
import ui.framework.VComponent;

public class VLobby extends VComponent {
    public VLobby(TheAlchemistGame game) { super(game); }
    private JLabel id;

    @Override
    protected void render() {
        id = new JLabel();
        id.setBounds(0, 0, 200, 30);

        panel.add(id);
    }

    @Override
    protected void mounted() {
        id.setText(String.format("You are the %s.", game.getId() == 0 ? "host" : "client #" + game.getId()));
    }
}
