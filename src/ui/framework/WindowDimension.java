package ui.framework;

import java.awt.Dimension;
import ui.Window;

public class WindowDimension {
    private static WindowDimension instance = null;

    public static synchronized WindowDimension getInstance() {
        if (instance == null)
            instance = new WindowDimension();
        
        return instance;
    }

    private WindowDimension() {}

    public Dimension getSize() {
        return Window.mainPanel.getPreferredSize();
    }

    public int getWidth() {
        return (int)getSize().getWidth();
    }

    public int getHeight() {
        return (int)getSize().getHeight();
    }

}
