package ui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import domain.Game;
import enums.Avatar;
import enums.View;
import ui.framework.VComponent;

public class VOnlineLogin extends VComponent {
    public VOnlineLogin(Game game) { super(game); }

    @Override
    protected void render() {
        JLabel background = new JLabel(new ImageIcon(assetLoader.getBackground(View.OnlineLogin)));
        background.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);


    	// a text to inform user
        JLabel enterUsername = new JLabel();
        enterUsername.setText("Pick Your Alchemist");
        enterUsername.setFont(new Font("Itim-Regular", Font.BOLD, 18));
        enterUsername.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Info text
        JLabel info = new JLabel("Please enter a name for the alchemist.");
        info.setFont(new Font("Itim-Regular", Font.PLAIN, 12));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);


        // text box to enter the username
        JTextField userNameTextField = new JTextField("Name");
        userNameTextField.setFont(new Font("Itim-Regular", Font.PLAIN, 12));
        userNameTextField.setForeground(Color.gray);
        userNameTextField.setMaximumSize(new Dimension(200, 30));
        userNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameTextField.setHorizontalAlignment(JTextField.CENTER);
        userNameTextField.setBorder(
            new CompoundBorder(
                new LineBorder(new Color(200,200,200)), 
                new EmptyBorder(6, 4, 6, 4)
            )
        );

        userNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userNameTextField.getText().equals("Name")) {
                    userNameTextField.setText("");
                    userNameTextField.setForeground(Color.BLACK);
                    userNameTextField.setFont(new Font("Itim-Regular", Font.PLAIN, 16));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userNameTextField.getText().isEmpty()) {
                    userNameTextField.setForeground(Color.GRAY);
                    userNameTextField.setText("Name");
                    userNameTextField.setFont(new Font("Itim-Regular", Font.PLAIN, 12));
                }
            }
        });


        // button to pass to the next page
        JButton nextButton = new JButton("Next");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add information text and name text box to the page
        form.add(Box.createVerticalGlue());
        form.add(enterUsername);
        form.add(Box.createVerticalStrut(12));
        form.add(info);
        form.add(Box.createVerticalStrut(24));
        form.add(userNameTextField);
        form.add(Box.createVerticalStrut(24));

        // group of radio buttons to select an avatar
        ButtonGroup avatarsButtonGroup = new ButtonGroup();

        for (Avatar a: Avatar.values()) {
        	JRadioButton avatar = new JRadioButton(a.toString());

            if (a == Avatar.Thunderous) avatar.setSelected(true);

            avatar.setFont(new Font("Itim-Regular", Font.PLAIN, 12));
        	avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        	avatarsButtonGroup.add(avatar);
        	form.add(avatar);
        }

        nextButton.addActionListener(event -> {
            String playerName = userNameTextField.getText().equals("Name") ? "" : userNameTextField.getText();
            String avatarName = null;
            Avatar playerAvatar = null;

            for (Enumeration<AbstractButton> buttons = avatarsButtonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton avatarButton = buttons.nextElement();

                if(avatarButton.isSelected()) {
                    avatarName = avatarButton.getText();
                }
            }

            for (Avatar a: Avatar.values()) {
                if(a.toString().equals(avatarName)) {
                    playerAvatar = a;
                }
            }

            if (playerName.equals("") || playerName == null) {
                info.setText("Name cannot be empty.");
                info.setForeground(Color.red);
                return;
            }

            int result = game.getRegister().createUser(playerName, playerAvatar);

            switch (result) {
                case 0: {
                    userNameTextField.setEditable(false);
                    router.to(View.Lobby);
                    break;
                }
                case 1: {
                    info.setText(String.format("There is already a player named %s.", playerName));
                    info.setForeground(Color.red);
                    break;
                }
                case 2: {
                    info.setText("Name cannot be empty.");
                    info.setForeground(Color.red);
                    break;
                }
                case 3: {
                    info.setText(String.format("%s is already taken. Pick another avatar.", avatarName));
                    info.setForeground(Color.red);
                    break;
                }
            }
           
        });

        form.add(Box.createVerticalStrut(32));
        form.add(nextButton);
        form.add(Box.createVerticalGlue());

        form.setBounds(windowDimension.getWidth() / 4, 0, windowDimension.getWidth() / 2, windowDimension.getHeight());
        

        panel.add(form);
        panel.add(background);
    }

    @Override
    protected void mounted() {
        router.activateListeners();
        this.reset();
    }

    public void reset() {
        panel.removeAll();;
        render();
        panel.revalidate();
        panel.repaint();
    }
    
}

