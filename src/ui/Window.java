package ui;

import javax.swing.JFrame;
import java.awt.*;
import java.util.LinkedHashMap;

import domain.TheAlchemistGame;
import enums.View;
import interfaces.IRenderable;

public class Window {
    static JFrame frame;
    static Router router;

    public Window(String title, int width, int height, TheAlchemistGame game) {
        frame = new JFrame(title);
        frame.setSize(width, height);

        LinkedHashMap<View, IRenderable> views = new LinkedHashMap<>() {{
            put(View.Start, new VStart());
            put(View.Login, new VLogin(game));
            put(View.Board, new VBoard(game));
            put(View.About, new VHelp());
        }};

        router = new Router(View.Start, frame, views);
    }


    public void init() {
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
}
