package ui;

import javax.swing.JLabel;

import domain.Game;
import domain.TheAlchemistGame;
import ui.framework.VComponent;

public class VPause extends VComponent {
    public VPause(Game game) {
        super(game);
    }

    @Override
    protected void render() {
        JLabel text = new JLabel("Pause Screen");
        text.setBounds(500, 10, 200, 30);

        panel.add(text);

    }
}
