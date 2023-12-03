package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import domain.TheAlchemistGame;
import enums.Avatar;
import enums.View;

public class VLogin extends VGameComponent {
    private boolean isFirstPlayerReady = false;
    private boolean isSecondPlayerReady = false;
    
    public VLogin(TheAlchemistGame game) { super(game); }

    @Override
    protected void render() {
        JPanel firstUserForm = createUserForm("Enter a name for the first Alchemist:", 0);
        JPanel secondUserForm = createUserForm("Enter a name for the second Alchemist:", 1);

        firstUserForm.setBounds(0, 0, Window.frame.getWidth() / 2, Window.frame.getHeight());
        secondUserForm.setBounds(Window.frame.getWidth() / 2 - 1, 0, Window.frame.getWidth() / 2, Window.frame.getHeight());

        this.panel.add(secondUserForm);
        this.panel.add(firstUserForm);
    }

    private JPanel createUserForm(String title, int userIndex) {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(255,255,255));


    	// a text to inform user
        JLabel enterUsername = new JLabel();
        enterUsername.setText(title);
        enterUsername.setFont(new Font("Arial", Font.BOLD, 18));
        enterUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // text box to enter the username
        JTextField userNameTextField = new JTextField(16);
        userNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));;
        userNameTextField.setMaximumSize(new Dimension(200, 20));
        userNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameTextField.setHorizontalAlignment(JTextField.CENTER);
        userNameTextField.setBorder(new CompoundBorder(new LineBorder(new Color(200,200,200)),new EmptyBorder(6, 4, 6, 4)));

        // button to pass to the next page
        JButton nextButton = new JButton("Next");
        nextButton.setForeground(new Color(255,50,50));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // add information text and name text box to the page
        form.add(Box.createVerticalGlue());
        form.add(enterUsername);
        form.add(Box.createVerticalStrut(24));
        form.add(userNameTextField);
        form.add(Box.createVerticalStrut(24));

        // group of radio buttons to select an avatar
        ButtonGroup avatarsButtonGroup = new ButtonGroup();

        for (Avatar a: Avatar.values()) {
        	JRadioButton avatar = new JRadioButton(a.toString());
        	avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        	avatarsButtonGroup.add(avatar);
        	form.add(avatar);
        	
        }

        nextButton.addActionListener(event -> {
            String playerName = userNameTextField.getText();
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

            game.createUser(playerName, playerAvatar);
            // !TODO: Add error handling

            if (userIndex == 0) this.isFirstPlayerReady = true;
            else if (userIndex == 1) this.isSecondPlayerReady = true;

            if (isFirstPlayerReady && isSecondPlayerReady) Window.router.setView(View.Board);

            nextButton.setText("Ready");
		    
        });

        form.add(Box.createVerticalStrut(32));
        form.add(nextButton);
        form.add(Box.createVerticalGlue());

        return form;
    }
}
