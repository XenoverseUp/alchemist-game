package ui;

import javax.swing.JFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import domain.TheAlchemistGame;
import enums.View;
import java.awt.event.*;

public class Window {
    static JFrame frame;
    private Router router;

    public Window(String title, int width, int height, TheAlchemistGame game) {
        frame = new JFrame(title);
        frame.setSize(width, height);

        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (router.getCurrentView() == View.Pause) {
                        if (router.hasPreviousView()) 
                            router.navigateBack();
                    } else router.to(View.Pause);
                }
            }

            public void keyReleased(KeyEvent ke) {
            }

            public void keyTyped(KeyEvent ke) {
            }
        });

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/resources/font/Itim-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/resources/font/Cubano.ttf")));
        } catch (IOException|FontFormatException e) {

            System.err.println(e);
        }

        frame.setFont(new Font("Itim-Regular", Font.PLAIN, 12));

        LinkedHashMap<View, VComponent> views = new LinkedHashMap<>() {
            {
                put(View.Start, new VStart());
                put(View.Login, new VLogin(game));
                put(View.Board, new VBoard(game));
                put(View.Inventory, new VInventory(game));
                put(View.CardDeck, new VCardDeck(game));
                put(View.DeductionBoard, new VDeductionBoard(game));
                put(View.PotionBrewingArea, new VPotionBrewingArea(game));
                put(View.PublicationArea, new VPublicationArea(game));
                put(View.Pause, new VPause(game));
                put(View.Help, new VHelp());
            }
        };

        router = Router.getInstance();
        router.populate(views);
        router.to(View.Start);
    }

    public void init() {
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }

}
