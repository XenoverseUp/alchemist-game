package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
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

import domain.Game;
import enums.Avatar;
import enums.View;
import ui.framework.VComponent;

public class VLogin extends VComponent {
    private boolean isFirstPlayerReady = false;
    private boolean isSecondPlayerReady = false;

    public VLogin(Game game) { super(game); }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.Login);
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


        JPanel firstUserForm = createUserForm("First Alchemist", 0);
        JPanel secondUserForm = createUserForm("Second Alchemist", 1);

        firstUserForm.setBounds(41, 0, windowDimension.getWidth() / 2, windowDimension.getHeight());
        secondUserForm.setBounds(windowDimension.getWidth() / 2 - 39, 0, windowDimension.getWidth() / 2, windowDimension.getHeight());

        panel.add(secondUserForm);
        panel.add(firstUserForm);
        panel.add(back);
        panel.add(background);
    }

    private JPanel createUserForm(String title, int userIndex) {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);


    	// a text to inform user
        JLabel enterUsername = new JLabel();
        enterUsername.setText(title);
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
        nextButton.setForeground(new Color(255,50,50));
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
            if ((userIndex == 0 && this.isFirstPlayerReady == true) ||
                (userIndex == 1 && this.isSecondPlayerReady == true)) return;


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

            int result = game.getRegister().createUser(playerName, playerAvatar);

            switch (result) {
                case 0: {
                    if (userIndex == 0) this.isFirstPlayerReady = true;
                    else if (userIndex == 1) this.isSecondPlayerReady = true;

                    nextButton.setText("Ready");
                    userNameTextField.setEditable(false);
                    info.setText("Waiting for other alchemist.");
                    info.setForeground(Color.black);
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

            if (isFirstPlayerReady && isSecondPlayerReady) { 
                game.getRegister().initializeGame();
                router.to(View.Board);
            }

        });

        form.add(Box.createVerticalStrut(32));
        form.add(nextButton);
        form.add(Box.createVerticalGlue());

        return form;
    }
}
