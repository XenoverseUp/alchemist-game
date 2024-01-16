package ui.framework;

import java.util.Map;
import java.util.Stack;

import enums.View;
import ui.Window;

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
        this.views.forEach((k, v) -> {
            v.render();
        });
    }

    public void to(View nextView) {
        if (currentView != null) {
            if (currentView != View.Help && currentView != View.Pause) 
                history.push(currentView);
            views.get(currentView).unmounted();
        }


        Window.mainPanel.removeAll();
        VComponent component = views.get(nextView);

        component.getContentPane().setPreferredSize(Window.mainPanel.getSize());
        component.getContentPane().setLocation(0, 0);
        component.getContentPane().setVisible(true);

        Window.mainPanel.add(component.getContentPane());
        Window.mainPanel.revalidate();
        Window.mainPanel.repaint();

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

    public void activateListeners() {
        this.views.forEach((k, v) -> v.listenBroadcast());
    }

}
