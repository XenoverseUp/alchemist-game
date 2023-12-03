package ui;

import java.util.Map;
import java.util.Stack;

import javax.swing.JFrame;

import enums.View;
import interfaces.IRenderable;

public class Router {
    private View currentView;
    private Stack<View> history = new Stack<>();
    private Map<View, IRenderable> views;

    public Router(View initialView, JFrame window, Map<View, IRenderable> views) {
        this.views = views;
        this.setView(initialView);
    }

    public void setView(View view) {
        history.push(currentView);

        Window.frame.getContentPane().removeAll();

        IRenderable component = views.get(view);

        component.getContentPanel().setPreferredSize(Window.frame.getSize());
        component.getContentPanel().setLocation(0, 0);
        component.getContentPanel().setVisible(true);

        Window.frame.add(component.getContentPanel());
        Window.frame.revalidate();
        Window.frame.repaint();

        this.currentView = view;
    }

    public void navigateBack() {
        View previous = history.pop();
        if (previous != null) 
            setView(previous);
    }

    public View getCurrentView() { return this.currentView; }
    public View getPreviousView() { return this.history.peek(); }
    public boolean hasPreviousView() { return !history.isEmpty(); }

}
