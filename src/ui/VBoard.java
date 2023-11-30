package ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import interfaces.Renderable;

public class VBoard implements Renderable {
    JPanel panel;

    public VBoard() {
        panel = new JPanel();
        
        JLabel logoOfApplication = new JLabel();
		logoOfApplication.setText("Board");
		logoOfApplication.setForeground(new Color(0,50,50));

        panel.add(logoOfApplication);
    }

    public JPanel getContentPanel() {
        return this.panel;
    }
}
