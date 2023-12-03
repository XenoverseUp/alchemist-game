package ui;

import java.util.Map;

import javax.swing.JFrame;

import enums.View;
import interfaces.Renderable;

public class Router {
    private View currentView;
    private Map<View, Renderable> views;

    public Router(View initialView, JFrame window, Map<View, Renderable> views) {
        this.views = views;
        this.setView(initialView);
    }

    public void setView(View view) {
        Window.window.getContentPane().removeAll();

        Renderable component = views.get(view);

        component.getContentPanel().setPreferredSize(Window.window.getSize());
        component.getContentPanel().setLocation(0, 0);
        component.getContentPanel().setVisible(true);

        Window.window.add(component.getContentPanel());
        Window.window.revalidate();
        Window.window.repaint();

        this.currentView = view;
    }

    public View getCurrentView() {
        return this.currentView;
    }
}
