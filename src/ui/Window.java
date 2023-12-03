package ui;

import javax.swing.JFrame;
import java.awt.*;
import java.util.LinkedHashMap;

import domain.TheAlchemistGame;
import enums.View;
import interfaces.Renderable;

public class Window {
    static JFrame window;
    static Router router;

    public Window(String title, int width, int height, TheAlchemistGame game) {
        window = new JFrame(title);
        window.setSize(width, height);

        LinkedHashMap<View, Renderable> views = new LinkedHashMap<>() {{
            put(View.Start, new VStart());
            put(View.Login, new VLogin(game));
            put(View.Board, new VBoard(game));
        }};

        router = new Router(View.Start, window, views);
    }


    public void init() {
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        window.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
}
