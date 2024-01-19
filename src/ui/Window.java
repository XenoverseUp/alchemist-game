package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import domain.Game;
import enums.Avatar;
import enums.View;
import ui.framework.ModalController;
import ui.framework.Router;
import ui.framework.VComponent;

import java.awt.event.*;

public class Window {
    static JFrame frame;
    private JPanel modalLayer;
    public static JPanel mainPanel;
    private Router router;
    private Game game;

    public Window(String title, int width, int height, Game game) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/resources/font/Itim-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/resources/font/CrimsonPro.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }

        this.game = game;
        frame = new JFrame(title);

        modalLayer = new JPanel(null);
        modalLayer.setSize(width, height);
        modalLayer.setPreferredSize(new Dimension(width, height));
        modalLayer.add(ModalController.generateInfoPopover(width, height));
        modalLayer.add(ModalController.generateOverlay(width, height));

        mainPanel = new JPanel();
        mainPanel.setSize(width, height);
        mainPanel.setPreferredSize(new Dimension(width, height));
        mainPanel.setBackground(Color.red);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        mainPanel.setBounds(0, 0, width, height);

        modalLayer.add(mainPanel);

        frame.add(modalLayer);
        frame.pack();

        mainPanel.setFont(new Font("Itim-Regular", Font.PLAIN, 12));


        LinkedHashMap<View, VComponent> views = new LinkedHashMap<View, VComponent>() {
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
                put(View.ArtifactShop, new VArtifactShop(game));
                put(View.OnlineSelection, new VOnlineSelection(game));
                put(View.Lobby, new VLobby(game));
                put(View.OnlineLogin, new VOnlineLogin(game));
                put(View.ElixirOfInsight, new VElixirofInsight(game));
                put(View.FinalScore, new VFinalScore(game));
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
        frame.setFocusable(true);
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (router.getCurrentView() == View.Pause) {
                        if (router.hasPreviousView())
                            router.navigateBack();
                    } else
                        router.to(View.Pause);
                }

                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    if (router.getCurrentView() == View.Help) {
                        if (router.hasPreviousView())
                            router.navigateBack();
                    } else
                        router.to(View.Help);
                }

                // NOTICE: Development Cheat Code
                if (ke.getKeyCode() == KeyEvent.VK_ENTER && router.getCurrentView() == View.Start) {
                    game.getLocalRegister().createUser("Can", Avatar.Celestial);
                    game.getLocalRegister().createUser("Ata", Avatar.Serene);
                    game.getLocalRegister().initializeGame();
                    router.to(View.Board);
                }

            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
    }
}
