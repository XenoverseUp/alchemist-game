package ui;

import java.util.Map;
import java.util.Stack;

import enums.View;

public class Router {
    private static Router instance = null;

    private View currentView;
    private Stack<View> history = new Stack<>();
    private Map<View, VComponent> views;


    public static synchronized Router getInstance() {
        if (instance == null)
            instance = new Router();
 
        return instance;
    }

    public void populate(Map<View, VComponent> views) {
        this.views = views;
        this.views.forEach((k, v) -> v.render());
    }

    public void to(View nextView) {
        if (currentView != null) {
            history.push(currentView);
            views.get(currentView).unmounted();
        }

        Window.frame.getContentPane().removeAll();
        VComponent component = views.get(nextView);

        component.getContentPanel().setPreferredSize(Window.frame.getSize());
        component.getContentPanel().setLocation(0, 0);
        component.getContentPanel().setVisible(true);

        Window.frame.add(component.getContentPanel());
        Window.frame.revalidate();
        Window.frame.repaint();

        component.mounted();

        this.currentView = nextView;
    }

    public void navigateBack() {
        View previous = history.pop();
        if (previous != null) 
            to(previous);
    }

    public View getCurrentView() { return this.currentView; }
    public View getPreviousView() { return this.history.peek(); }
    public boolean hasPreviousView() { return !history.isEmpty(); }

}
