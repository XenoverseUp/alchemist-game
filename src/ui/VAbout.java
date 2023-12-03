package ui;

import javax.swing.JButton;
import javax.swing.JLabel;

public class VAbout extends VComponent {

    @Override
    protected void render() {
        JLabel text = new JLabel("About");
        text.setBounds(0,0,50,10);

        JButton back = new JButton("Back");
        back.setBounds(0, 50, 50, 20);
        back.addActionListener(event -> {
            Window.router.navigateBack();
        });

        panel.add(text);
        panel.add(back);
    }

}
