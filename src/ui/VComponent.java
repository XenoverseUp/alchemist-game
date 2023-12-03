package ui;

import java.awt.Color;
import javax.swing.JPanel;
import interfaces.Renderable;

public abstract class VComponent implements Renderable {
    protected JPanel panel = new JPanel();

    protected VComponent() {
        panel.setBackground(Color.WHITE);
        panel.setSize(Window.window.getSize());
        panel.setLayout(null);
        this.buildView();
    }

    protected abstract void buildView();

    @Override
    public JPanel getContentPanel() {
        return this.panel;
    }
}
