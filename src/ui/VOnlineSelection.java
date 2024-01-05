package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import domain.TheAlchemistGame;
import enums.View;
import ui.framework.VComponent;
import ui.util.RotatedLabel;
import ui.util.RotatedLabel.Direction;

public class VOnlineSelection extends VComponent {
    public VOnlineSelection(TheAlchemistGame game) { super(game); }

    private enum FormState {
        Host,
        Join
    }

    private FormState formState = FormState.Host;
    private JButton host, join;
    private RotatedLabel hostText, joinText;
    private JPanel modalContent;

    @Override
    protected void mounted() {
        formState = FormState.Host;
        this.update();
    }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.OnlineSelection);
        JLabel background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, BBackground.getWidth(), BBackground.getHeight());

        BufferedImage BLeftArrow = assetLoader.getLeftArrow();
        JButton back = new JButton(new ImageIcon(BLeftArrow));
        back.setBounds(36, 36, BLeftArrow.getWidth(), BLeftArrow.getHeight());
        back.addActionListener(e -> router.navigateBack());
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        back.setFocusable(false);

        BufferedImage BOldFabric = assetLoader.getOldFabric();
        JLabel modalBackground = new JLabel(new ImageIcon(BOldFabric));
        modalBackground.setBounds(
            windowDimension.getWidth() / 2 - BOldFabric.getWidth() / 2, 
            windowDimension.getHeight() / 2 - BOldFabric.getHeight() / 2, 
            BOldFabric.getWidth(), 
            BOldFabric.getHeight()
        );

        modalContent = new JPanel(null);
        modalContent.setBounds(modalBackground.getBounds());
        modalContent.setOpaque(false);
        

        BufferedImage BSelectionStripe = assetLoader.getSelectionStripe();
        host = new JButton(new ImageIcon(BSelectionStripe)); 
        host.setBounds(382, 214, BSelectionStripe.getWidth(), BSelectionStripe.getHeight());
        host.setOpaque(false);
        host.setContentAreaFilled(false);
        host.setBorderPainted(false);
        host.setFocusable(false);
        
        hostText = new RotatedLabel("Host", SwingConstants.CENTER);
        hostText.setVerticalAlignment(SwingConstants.NORTH);
        hostText.setDirection(Direction.VERTICAL_UP);
        hostText.setFont(new Font("Crimson Pro", Font.BOLD, 28));
        hostText.setForeground(Color.white);
        hostText.setBounds(388, 214, BSelectionStripe.getWidth(), BSelectionStripe.getHeight());

        host.addActionListener(e -> {
            if (this.formState == FormState.Host) return;
            this.formState = FormState.Host;
            this.update();
        });
        
        join = new JButton(new ImageIcon(BSelectionStripe)); 
        join.setBounds(382, 326, BSelectionStripe.getWidth(), BSelectionStripe.getHeight());
        join.setOpaque(false);
        join.setContentAreaFilled(false);
        join.setBorderPainted(false);
        join.setFocusable(false);
        
        joinText = new RotatedLabel("Join", SwingConstants.CENTER);
        joinText.setVerticalAlignment(SwingConstants.NORTH);
        joinText.setDirection(Direction.VERTICAL_UP);
        joinText.setFont(new Font("Crimson Pro", Font.BOLD, 28));
        joinText.setForeground(Color.white);
        joinText.setBounds(388, 326, BSelectionStripe.getWidth(), BSelectionStripe.getHeight());

        join.addActionListener(e -> {
            if (this.formState == FormState.Join) return;
            this.formState = FormState.Join;
            this.update();
        });


        panel.add(modalContent);
        panel.add(modalBackground);
        panel.add(back);
        panel.add(hostText);
        panel.add(host);
        panel.add(joinText);
        panel.add(join);
        panel.add(background);
    }

    private void update() {
        int stripeDisplacement = 10;
        modalContent.removeAll();
        modalContent.setLayout(null);

        if (this.formState == FormState.Host) {
            host.setBounds(382 - stripeDisplacement, 214, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            hostText.setBounds(386 - (int)(stripeDisplacement * 0.75), 214, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            hostText.setForeground(new Color(238, 219, 0));
            
            join.setBounds(382, 326, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            joinText.setBounds(386, 326, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            joinText.setForeground(new Color(194, 178, 0));

            modalContent.add(createForm(FormState.Host));
        } else {
            join.setBounds(382 - stripeDisplacement, 326, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            joinText.setBounds(386 - (int)(stripeDisplacement * 0.75), 326, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            joinText.setForeground(new Color(238, 219, 0));

            
            host.setBounds(382, 214, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            hostText.setBounds(386, 214, assetLoader.getSelectionStripe().getWidth(), assetLoader.getSelectionStripe().getHeight());
            hostText.setForeground(new Color(194, 178, 0));

            modalContent.add(createForm(FormState.Join));
        }

        modalContent.revalidate();
        modalContent.repaint();

    }

    private JPanel createForm(FormState type) {
        JPanel form = new JPanel();
        form.setPreferredSize(new Dimension(modalContent.getWidth(), modalContent.getHeight()));
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setBounds(0,0, modalContent.getWidth(), modalContent.getHeight());

        JLabel title = new JLabel(String.format("<html><div style='text-align: center;'>%s A Game</div><html>", type.toString()), SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Itim-Regular", Font.BOLD, 32));
        title.setForeground(Color.white);
        
        String hostDescription = "Host a game to share a session with your friends and play online. Pick a 4-digit port number to create a session.";
        String joinDescription = "Join a game to play with your friends in an existing session. Enter the 4-digit port number you got from the host.";
        
        JLabel description = new JLabel(String.format("<html><div style='text-align: center;'>%s</div><html>", type == FormState.Host ? hostDescription : joinDescription), SwingConstants.CENTER);
        description.setMaximumSize(new Dimension(modalContent.getWidth() - 250, modalContent.getHeight()));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setFont(new Font("Crimson Pro", Font.PLAIN, 16));
        description.setForeground(Color.white);

        JFormattedTextField portInput = null;

        try {
            portInput = new JFormattedTextField(new MaskFormatter("####"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        portInput.setFont(new Font("Itim-Regular", Font.PLAIN, 32));
        portInput.setForeground(new Color(238, 219, 0));
        portInput.setMaximumSize(new Dimension(120, 50));
        portInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        portInput.setHorizontalAlignment(JTextField.CENTER);
        portInput.setBackground(new Color(80, 58, 24));
        portInput.setBorder(BorderFactory.createMatteBorder(8, 0, 8, 0, new Color(80, 58, 24)));
        portInput.grabFocus();

        final JFormattedTextField p = portInput;

        portInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                p.setCaretPosition(0);
            }
        });
        
        
        JButton primaryAction = new JButton(type == FormState.Host ? "Create Session" : "Enter Lobby");
        primaryAction.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        primaryAction.addActionListener(e -> {
            if (type == FormState.Host) {
                int result = game.createServer(Integer.parseInt(p.getText()));
                if (result == 0) {
                    router.to(View.Lobby);
                    return;
                }
                
                modal.info("Port is not available!", "This port is already in use by another game session or system. Please pick another port.");
            } else if (type == FormState.Join) {
                int result = game.connectToServer(Integer.parseInt(p.getText()));
                if (result == 0) {
                    router.to(View.Lobby);
                    return;
                }

                modal.info("No game session on this port!", "This port doesn't have any game sessions available. Please pick another port.");
            }              
        });


        form.add(Box.createVerticalGlue());
        form.add(title);
        form.add(Box.createVerticalStrut(16));
        form.add(description);
        form.add(Box.createVerticalStrut(28));
        form.add(portInput);
        form.add(Box.createVerticalStrut(20));
        form.add(primaryAction);
        form.add(Box.createVerticalGlue());

        return form;
    }
}
