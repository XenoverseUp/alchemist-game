package ui.framework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ModalController {
    private static JPanel overlay = null;
    
    private static JPanel infoPopover = null;
    private static JLabel infoPopoverTitle = null;
    private static JLabel infoPopoverDescription = null;
    
    public static JPanel generateOverlay(int windowWidth, int windowHeight) {
        int overlayOpacity = 90;

        if (overlay == null) {
            overlay = new JPanel() {
                protected void paintComponent(Graphics g) {
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };

            overlay.setBackground(new Color(0, 0, 0, overlayOpacity));
            overlay.setBounds(0, 0, windowWidth, windowHeight);
            overlay.setVisible(false);
        }

        overlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close();
            }
        });

        return overlay;
    }
    
    public static JPanel generateInfoPopover(int windowWidth, int windowHeight) {
        if (infoPopover == null) {
            infoPopover = new JPanel(null);
            infoPopover.addMouseListener(new MouseAdapter() {});
            infoPopover.setFocusable(false);

            BufferedImage BPopover = null;
            try {
                BPopover = ImageIO.read(new File("./src/resources/image/HUD/popover.png"));
            } catch (IOException e) {
                System.out.println(e);
            }

            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setOpaque(false);
            infoPopoverTitle = new JLabel();
            infoPopoverTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoPopoverTitle.setFont(new Font("Crimson Pro", Font.BOLD, 24));
            infoPopoverTitle.setForeground(new Color(41, 31, 25));
            infoPopoverTitle.setMaximumSize(new Dimension(BPopover.getWidth() - 400, 99));
            infoPopoverTitle.setHorizontalAlignment(SwingConstants.CENTER);

            
            infoPopoverDescription = new JLabel();
            infoPopoverDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoPopoverDescription.setFont(new Font("Crimson Pro", Font.PLAIN, 18));
            infoPopoverDescription.setMaximumSize(new Dimension(360, 9999));
            infoPopoverDescription.setHorizontalAlignment(SwingConstants.CENTER);


            JButton defaultActionButton = new JButton("Ok!");
            defaultActionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            defaultActionButton.setFocusable(false);
            defaultActionButton.addActionListener(e -> close());
            
            content.setPreferredSize(new Dimension(BPopover.getWidth(), BPopover.getHeight()));
            content.setBounds(0, 0, BPopover.getWidth() / 2, BPopover.getHeight() / 2);

            content.add(Box.createVerticalGlue());
            content.add(infoPopoverTitle);
            content.add(Box.createVerticalStrut(12));
            content.add(infoPopoverDescription);
            content.add(Box.createVerticalStrut(32));
            content.add(defaultActionButton);
            content.add(Box.createVerticalGlue());
            
            infoPopover.add(content);

            JLabel popoverBackground = new JLabel(new ImageIcon(BPopover.getScaledInstance(BPopover.getWidth() / 2, BPopover.getHeight() / 2, Image.SCALE_SMOOTH)));
            popoverBackground.setBounds(0, 0, BPopover.getWidth() / 2, BPopover.getHeight() / 2);
            infoPopover.add(popoverBackground);
               
            infoPopover.setVisible(false);
            infoPopover.setOpaque(false);
            infoPopover.setBounds(windowWidth / 2 - BPopover.getWidth() / 4, windowHeight / 2 - BPopover.getHeight() / 4, BPopover.getWidth() / 2, BPopover.getHeight() / 2);
        }


        return infoPopover;
    }

    public static void close() {
        infoPopover.setVisible(false);
        overlay.setVisible(false);
    }


    /**
     * Client instance for controlling the modal.
     */
    private static Modal modalInstance = null;

    public static synchronized Modal getModalInstance() {
        if (modalInstance == null) 
            modalInstance = new Modal();

        return modalInstance;
    }

    public static class Modal {
        public void info(String title, String description) {
            infoPopoverTitle.setText(String.format("<html><div style='text-align: center;'>%s</div></html>", title));
            infoPopoverDescription.setText(String.format("<html><div style='text-align: center;'>%s</div></html>", description));
            infoPopover.setVisible(true);
            overlay.setVisible(true);
        }

        public void dismiss() {
            close();
        }
    }
}
