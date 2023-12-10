package ui;

import javax.swing.JLabel;
import domain.TheAlchemistGame;

public class VPause extends VComponent {
    public VPause(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void render() {
        JLabel text = new JLabel("Pause Screen");
        text.setBounds(500, 10, 200, 30);

        panel.add(text);

    }
}
