package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import ui.framework.Router;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import enums.View;
import ui.framework.Router;
import ui.framework.VComponent;
import domain.TheAlchemistGame;

public class VPublicationArea extends VComponent {

    private Router router = Router.getInstance();
    JPanel publicationPanel;

    public VPublicationArea(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void mounted() {
        this.update();

    }

    @Override
    protected void render() {
        publicationPanel = createPublicationJPanel();

        BufferedImage bg = null;
        try {
            bg = ImageIO.read(new File("src/resources/image/publication-area-bg.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        int desiredWidth = 1450;
        int desiredHeight = 750;

        Image scaledBg = bg.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        JLabel bgPic = new JLabel(new ImageIcon(scaledBg));
        bgPic.setBounds(0, 0, desiredWidth, desiredHeight);

        panel.setLayout(null);
        panel.add(publicationPanel);

        panel.add(bgPic);

        int startX = 60;
        int startY = 110;
        int imageWidth = 190;
        int imageHeight = 240;
        int gap = 80;

        String[] ingredientCardNames = { "birdClaw", "fern", "mandrakeRoot", "moonshade", "mushroom", "ravensFeather",
                "scorpionTail", "wartyToad" };

        for (int i = 0; i < ingredientCardNames.length; i++) {
            int x = startX + (i % 4) * (imageWidth + gap);
            int y = startY + (i / 4) * (imageHeight + gap);

            String imagePath = "./src/resources/image/" + ingredientCardNames[i] + ".png";
            int a = i;
            String ingredientCardName = ingredientCardNames[i];
            addImageToPanel(i, publicationPanel, imagePath, x, y, imageWidth, imageHeight, (e -> {
                game.setCard(a);
                System.out.println(ingredientCardName + " is clicked!");

            }));
        }

        createButtonWithImage(publicationPanel, "./src/resources/image/marker1.png", 1070, 370, 75, 70,
                (e -> {
                    game.setMarker(1);
                    System.out.println("Clicked marker1!");

                }));// molecule id:1

        createButtonWithImage(publicationPanel, "./src/resources/image/marker5.png", 1170, 370, 70, 70,
                (e -> {
                    game.setMarker(5);
                    System.out.println("Clicked marker5!");

                }));// molecule id:5

        createButtonWithImage(publicationPanel, "./src/resources/image/marker0.png", 1270, 370, 75, 70,
                (e -> {
                    game.setMarker(0);
                    System.out.println("Clicked marker0!");

                }));// molecule id: 0
        createButtonWithImage(publicationPanel, "./src/resources/image/marker3.png", 1070, 480, 75, 70,
                (e -> {
                    game.setMarker(3);
                    System.out.println("Clicked marker3!");

                }));// molecule id:3

        createButtonWithImage(publicationPanel, "./src/resources/image/marker6.png", 1170, 480, 70, 70,
                (e -> {
                    game.setMarker(6);
                    System.out.println("Clicked marker6!");

                }));// molecule id:6

        createButtonWithImage(publicationPanel, "./src/resources/image/marker7.png", 1270, 480, 75, 70,
                (e -> {
                    game.setMarker(7);
                    System.out.println("CLicked marker7!");

                }));// molecule id:7

        createButtonWithImage(publicationPanel, "./src/resources/image/marker4.png", 1120, 590, 75, 70,
                (e -> {
                    game.setMarker(4);
                    System.out.println("Clicked marker4!");

                }));// molecule id:4

        createButtonWithImage(publicationPanel, "./src/resources/image/marker2.png", 1220, 590, 75, 70,
                (e -> {
                    game.setMarker(2);
                    System.out.println("CLicked marker2!");

                }));// molecule id :2

    }

    private void update() {
        publicationPanel.removeAll();

        int startX = 60;
        int startY = 75;
        int imageWidth = 150;
        int imageHeight = 230;
        int gap = 85;

        String[] ingredientCardNames = { "birdClaw", "fern", "mandrakeRoot", "moonshade", "mushroom", "ravensFeather",
                "scorpionTail", "wartyToad" };

        for (int i = 0; i < ingredientCardNames.length; i++) {
            int x = startX + (i % 4) * (imageWidth + gap);
            int y = startY + (i / 4) * (imageHeight + gap);

            String imagePath = "./src/resources/image/" + ingredientCardNames[i] + ".png";
            int a = i;
            String ingredientCardName = ingredientCardNames[i];
            addImageToPanel(i, publicationPanel, imagePath, x, y, imageWidth, imageHeight, (e -> {
                game.setCard(a);
                System.out.println(ingredientCardName + " is clicked!");

            }));
        }

        createButtonWithImage(publicationPanel, "./src/resources/image/marker1.png", 1070, 370, 75, 70,
                (e -> {
                    game.setMarker(1);
                    System.out.println("Clicked marker1!");

                }));// molecule id:1

        createButtonWithImage(publicationPanel, "./src/resources/image/marker5.png", 1170, 370, 70, 70,
                (e -> {
                    game.setMarker(5);
                    System.out.println("Clicked marker5!");

                }));// molecule id:5

        createButtonWithImage(publicationPanel, "./src/resources/image/marker0.png", 1270, 370, 75, 70,
                (e -> {
                    game.setMarker(0);
                    System.out.println("Clicked marker0!");

                }));// molecule id: 0
        createButtonWithImage(publicationPanel, "./src/resources/image/marker3.png", 1070, 480, 75, 70,
                (e -> {
                    game.setMarker(3);
                    System.out.println("Clicked marker3!");

                }));// molecule id:3

        createButtonWithImage(publicationPanel, "./src/resources/image/marker6.png", 1170, 480, 70, 70,
                (e -> {
                    game.setMarker(6);
                    System.out.println("Clicked marker6!");

                }));// molecule id:6

        createButtonWithImage(publicationPanel, "./src/resources/image/marker7.png", 1270, 480, 75, 70,
                (e -> {
                    game.setMarker(7);
                    System.out.println("CLicked marker7!");

                }));// molecule id:7

        createButtonWithImage(publicationPanel, "./src/resources/image/marker4.png", 1120, 590, 75, 70,
                (e -> {
                    game.setMarker(4);
                    System.out.println("Clicked marker4!");

                }));// molecule id:4

        createButtonWithImage(publicationPanel, "./src/resources/image/marker2.png", 1220, 590, 75, 70,
                (e -> {
                    game.setMarker(2);
                    System.out.println("CLicked marker2!");

                }));// molecule id :2

        publicationPanel.revalidate();
        publicationPanel.repaint();

    }

    private JPanel createPublicationJPanel() {

        Color customColor = new Color(71, 46, 27);
        JPanel publicationPanel = new JPanel();
        publicationPanel.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());
        publicationPanel.setLayout(null); // Use absolute positioning
        publicationPanel.setOpaque(false);

        JButton back = new JButton("Back");
        back.setBounds(480, 690, 150, 30);
        back.addActionListener(event -> router.to(View.Board));
        back.setForeground(customColor);
        panel.add(back);

        JButton publishTheory = new JButton("Publish Theory");

        // Set the background color of the button to the custom color

        publishTheory.setFont(new Font("Cubano", Font.BOLD, 18));
        publishTheory.addActionListener(e -> {
            game.publishTheory();
            SwingUtilities.invokeLater(() -> {
                update();
            });
        });

        publishTheory.setBounds(1100, 170, 170, 60);

        publishTheory.setForeground(customColor);
        panel.add(publishTheory);

        JButton debunkTheory = new JButton("Debunk Theory");
        debunkTheory.setFont(new Font("Cubano", Font.BOLD, 18));
        debunkTheory.addActionListener(e -> {
            game.debunkTheory();
            SwingUtilities.invokeLater(() -> {
                update();
            });
        });
        debunkTheory.setBounds(1100, 250, 170, 60);
        debunkTheory.setForeground(customColor);
        panel.add(debunkTheory);

        panel.revalidate();
        panel.repaint();

        return publicationPanel;
    }

    private void addImageToPanel(int id, JPanel panel, String imagePath, int x, int y, int width, int height,
            ActionListener action) {
        BufferedImage img = null; // img -> publicationCard
        BufferedImage img2 = null; // img2 -> marker
        try {
            img = ImageIO.read(new File(imagePath));
            img2 = ImageIO.read(new File(game.getMarkerImagePath(id)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (img != null) {
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JButton button = new JButton(icon);
            button.setBounds(x, y, width, height);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.addActionListener(action);
            panel.add(button);

            if (img2 != null) {
                ImageIcon icon2 = new ImageIcon(img2.getScaledInstance(60, 56, Image.SCALE_SMOOTH));

                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(null);
                imagePanel.setBounds(x + width / 4 + 5, y + height + 10, 60, 56);
                imagePanel.setOpaque(false);

                JLabel imageLabel = new JLabel(icon2);
                imageLabel.setBounds(0, 0, 60, 56);
                imagePanel.add(imageLabel);

                panel.add(imagePanel);
            }
        }
    }

    private void createButtonWithImage(JPanel panel, String imagePath, int x, int y, int width, int height,
            ActionListener action) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (img != null) {
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JButton button = new JButton(icon);
            button.setBounds(x, y, width, height);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);

            button.addActionListener(action);

            panel.add(button);
        }

    }

}
