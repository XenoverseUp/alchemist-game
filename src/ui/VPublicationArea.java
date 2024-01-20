package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import ui.framework.AssetLoader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import enums.View;
import error.NotEnoughActionsException;
import ui.framework.VComponent;
import domain.Game;

public class VPublicationArea extends VComponent {
    JPanel publicationPanel;

    public VPublicationArea(Game game) {
        super(game);
    }

    @Override
    protected void mounted() {
        this.update();

    }

    @Override
    protected void render() {
        publicationPanel = createPublicationJPanel();

        BufferedImage bg = assetLoader.getPbBackground();
        if (bg == null) {
            System.out.println("Background image not available.");
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
        int startY = 75;
        int imageWidth = 150;
        int imageHeight = 230;
        int gap = 85;

        addImageToPanel(0, publicationPanel, "bird claw", startX, startY, imageWidth, imageHeight, (e -> {
            game.getRegister().setCard(0);
            System.out.print("bird claw is clicked!");
        }));
        addImageToPanel(1, publicationPanel, "fern", startX + imageWidth + gap, startY, imageWidth, imageHeight, (e -> {
            game.getRegister().setCard(1);
            System.out.println("fern is clicked!");
        }));
        addImageToPanel(2, publicationPanel, "mandrake root", startX + 2 * (imageWidth + gap), startY, imageWidth,
                imageHeight, (e -> {
                    game.getRegister().setCard(2);
                    System.out.println("mandrake root is clicked!");
                }));
        addImageToPanel(3, publicationPanel, "moonshade", startX + 3 * (imageWidth + gap), startY, imageWidth,
                imageHeight, (e -> {
                    game.getRegister().setCard(3);
                    System.out.println("moonshade is clicked!");
                }));
        addImageToPanel(4, publicationPanel, "mushroom", startX, startY + (imageHeight + gap), imageWidth, imageHeight,
                (e -> {
                    game.getRegister().setCard(4);
                    System.out.println("mushroom is clicked!");
                }));
        addImageToPanel(5, publicationPanel, "raven's feather", startX + (imageWidth + gap),
                startY + (imageHeight + gap), imageWidth, imageHeight, (e -> {
                    game.getRegister().setCard(5);
                    System.out.println("raven's feather is clicked!");
                }));
        addImageToPanel(6, publicationPanel, "scorpion tail", startX + 2 * (imageWidth + gap),
                startY + (imageHeight + gap), imageWidth, imageHeight, (e -> {
                    game.getRegister().setCard(6);
                    System.out.println("scorpion tail is clicked!");
                }));
        addImageToPanel(7, publicationPanel, "warty toad", startX + 3 * (imageWidth + gap),
                startY + (imageHeight + gap), imageWidth, imageHeight, (e -> {
                    game.getRegister().setCard(7);
                    System.out.println("warty toad is clicked!");
                }));

        createButtonWithImage(publicationPanel, 1, 1070, 370, 75, 70, (e -> {
            game.getRegister().setMarker(1);
            System.out.println("Clicked marker1!");
        })); // molecule id:1
        createButtonWithImage(publicationPanel, 5, 1170, 370, 70, 70, (e -> {
            game.getRegister().setMarker(5);
            System.out.println("Clicked marker5!");
        })); // molecule id:5
        createButtonWithImage(publicationPanel, 0, 1270, 370, 75, 70, (e -> {
            game.getRegister().setMarker(0);
            System.out.println("Clicked marker0!");
        })); // molecule id: 0
        createButtonWithImage(publicationPanel, 3, 1070, 480, 75, 70, (e -> {
            game.getRegister().setMarker(3);
            System.out.println("Clicked marker3!");
        })); // molecule id:3
        createButtonWithImage(publicationPanel, 6, 1170, 480, 70, 70, (e -> {
            game.getRegister().setMarker(6);
            System.out.println("Clicked marker6!");
        })); // molecule id:6
        createButtonWithImage(publicationPanel, 7, 1270, 480, 75, 70, (e -> {
            game.getRegister().setMarker(7);
            System.out.println("Clicked marker7!");
        })); // molecule id:7
        createButtonWithImage(publicationPanel, 4, 1120, 590, 75, 70, (e -> {
            game.getRegister().setMarker(4);
            System.out.println("Clicked marker4!");
        })); // molecule id:4
        createButtonWithImage(publicationPanel, 2, 1220, 590, 75, 70, (e -> {
            game.getRegister().setMarker(2);
            System.out.println("Clicked marker2!");
        })); // molecule id:2

    }

    private void update() {
        publicationPanel.removeAll();

        int startX = 60;
        int startY = 75;
        int imageWidth = 150;
        int imageHeight = 230;
        int gap = 85;

        addImageToPanel(0, publicationPanel, "bird claw", startX, startY, imageWidth, imageHeight, (e -> {
            game.getRegister().setCard(0);
            System.out.print("bird claw is clicked!");
        }));
        addImageToPanel(1, publicationPanel, "fern", startX + imageWidth + gap, startY, imageWidth, imageHeight, (e -> {
            game.getRegister().setCard(1);
            System.out.println("fern is clicked!");
        }));
        addImageToPanel(2, publicationPanel, "mandrake root", startX + 2 * (imageWidth + gap), startY, imageWidth,
                imageHeight, (e -> {
                    game.getRegister().setCard(2);
                    System.out.println("mandrake root is clicked!");
                }));
        addImageToPanel(3, publicationPanel, "moonshade", startX + 3 * (imageWidth + gap), startY, imageWidth,
                imageHeight, (e -> {
                    game.getRegister().setCard(3);
                    System.out.println("moonshade is clicked!");
                }));
        addImageToPanel(4, publicationPanel, "mushroom", startX, startY + (imageHeight + gap), imageWidth, imageHeight,
                (e -> {
                    game.getRegister().setCard(4);
                    System.out.println("mushroom is clicked!");
                }));
        addImageToPanel(5, publicationPanel, "raven's feather", startX + (imageWidth + gap),
                startY + (imageHeight + gap), imageWidth, imageHeight, (e -> {
                    game.getRegister().setCard(5);
                    System.out.println("raven's feather is clicked!");
                }));
        addImageToPanel(6, publicationPanel, "scorpion tail", startX + 2 * (imageWidth + gap),
                startY + (imageHeight + gap), imageWidth, imageHeight, (e -> {
                    game.getRegister().setCard(6);
                    System.out.println("scorpion tail is clicked!");
                }));
        addImageToPanel(7, publicationPanel, "warty toad", startX + 3 * (imageWidth + gap),
                startY + (imageHeight + gap), imageWidth, imageHeight, (e -> {
                    game.getRegister().setCard(7);
                    System.out.println("warty toad is clicked!");
                }));

        createButtonWithImage(publicationPanel, 1, 1070, 370, 75, 70, (e -> {
            game.getRegister().setMarker(1);
            System.out.println("Clicked marker1!");
        })); // molecule id:1
        createButtonWithImage(publicationPanel, 5, 1170, 370, 70, 70, (e -> {
            game.getRegister().setMarker(5);
            System.out.println("Clicked marker5!");
        })); // molecule id:5
        createButtonWithImage(publicationPanel, 0, 1270, 370, 75, 70, (e -> {
            game.getRegister().setMarker(0);
            System.out.println("Clicked marker0!");
        })); // molecule id: 0
        createButtonWithImage(publicationPanel, 3, 1070, 480, 75, 70, (e -> {
            game.getRegister().setMarker(3);
            System.out.println("Clicked marker3!");
        })); // molecule id:3
        createButtonWithImage(publicationPanel, 6, 1170, 480, 70, 70, (e -> {
            game.getRegister().setMarker(6);
            System.out.println("Clicked marker6!");
        })); // molecule id:6
        createButtonWithImage(publicationPanel, 7, 1270, 480, 75, 70, (e -> {
            game.getRegister().setMarker(7);
            System.out.println("Clicked marker7!");
        })); // molecule id:7
        createButtonWithImage(publicationPanel, 4, 1120, 590, 75, 70, (e -> {
            game.getRegister().setMarker(4);
            System.out.println("Clicked marker4!");
        })); // molecule id:4
        createButtonWithImage(publicationPanel, 2, 1220, 590, 75, 70, (e -> {
            game.getRegister().setMarker(2);
            System.out.println("Clicked marker2!");
        })); // molecule id:2

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
        back.setBounds(480, 650, 150, 30);
        back.addActionListener(event -> router.to(View.Board));
        back.setForeground(customColor);
        panel.add(back);

        JButton publishTheory = new JButton("Publish Theory");

        // Set the background color of the button to the custom color

        publishTheory.setFont(new Font("CrimsonPro", Font.BOLD, 18));
        publishTheory.addActionListener(e -> {
            try {
                game.getRegister().publishTheory();
                modal.info("Function call", "Publish theory");
            } catch (NotEnoughActionsException e1) {
                modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
            }
            SwingUtilities.invokeLater(() -> {
                update();
            });
        });

        publishTheory.setBounds(1100, 170, 170, 60);

        publishTheory.setForeground(customColor);
        panel.add(publishTheory);

        JButton debunkTheory = new JButton("Debunk Theory");
        debunkTheory.setFont(new Font("CrimsonPro", Font.BOLD, 18));
        debunkTheory.addActionListener(e -> {
            try {
                game.getRegister().debunkTheory();
                modal.info("Function call", "Debunk theory");
            } catch (NotEnoughActionsException e1) {
                modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
            }
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

    private void addImageToPanel(int id, JPanel panel, String ingredientCardName, int x, int y, int width, int height,
            ActionListener action) {
        AssetLoader assetLoader = AssetLoader.getInstance();
        BufferedImage img = assetLoader.getIngredientCard(ingredientCardName); // img -> publicationCard
        BufferedImage img2 = null;
        try {
            img2 = assetLoader.getMarkerImage(game.getRegister().getMarkerID(id));

        } catch (Exception e) {
            System.out.println("theory not published yet.");
        }
        if (img != null) {
            System.out.println("Loaded ingredient card image: " + ingredientCardName);
        } else {
            System.out.println("Failed to load ingredient card image: " + ingredientCardName);
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

    private void createButtonWithImage(JPanel panel, int markerId, int x, int y, int width, int height,
            ActionListener action) {
        AssetLoader assetLoader = AssetLoader.getInstance();
        BufferedImage img = assetLoader.getMarkerImage(markerId);

        if (img != null) {
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JButton button = new JButton(icon);
            button.setBounds(x, y, width, height);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);

            button.addActionListener(action);

            panel.add(button);
        } else {
            System.out.println("Failed to load marker image " + markerId);
        }

    }

}
