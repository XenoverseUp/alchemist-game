package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout.Alignment;
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
import interfaces.Renderable;

public class VLogin implements Renderable, ActionListener {
	TheAlchemistGame game;
    JPanel panel;
    JLabel enterUsername1;
    JButton nextButton;
    JTextField userNameTextField;
    ButtonGroup avatarsButtonGroup;
    int formIndex = 0;
    

    public VLogin(TheAlchemistGame game) {
    	this.game = game;
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setSize(Window.window.getSize());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.buildView();
    }

    public void buildView() {
    	// a text to inform user
        enterUsername1 = new JLabel();
        enterUsername1.setText("Enter a name for Alchemist 1: ");
        enterUsername1.setFont(new Font("Arial", Font.BOLD, 18));
        enterUsername1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // text box to enter the username
        userNameTextField = new JTextField(16);
        userNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));;
        userNameTextField.setMaximumSize(new Dimension(200, 20));
        userNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameTextField.setHorizontalAlignment(JTextField.CENTER);
        userNameTextField.setBorder(new CompoundBorder(new LineBorder(new Color(200,200,200)),new EmptyBorder(6, 4, 6, 4)));

        // button to pass to the next page
        nextButton = new JButton("Next");
        nextButton.setForeground(new Color(255,50,50));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(this);
        
        // add information text and name text box to the page
        this.panel.add(Box.createVerticalGlue());
        this.panel.add(enterUsername1);
        this.panel.add(Box.createVerticalStrut(24));
        this.panel.add(userNameTextField);
        this.panel.add(Box.createVerticalStrut(24));

        // group of radio buttons to select an avatar
        avatarsButtonGroup = new ButtonGroup();

        for (Avatar a: Avatar.values()) {
        	
        	JRadioButton avatar = new JRadioButton(a.toString());
        	avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        	avatarsButtonGroup.add(avatar);
        	this.panel.add(avatar);
        	
        }
        this.panel.add(Box.createVerticalStrut(32));
        this.panel.add(nextButton);
        this.panel.add(Box.createVerticalGlue());
        
    }

    public JPanel getContentPanel() {
        return this.panel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == nextButton) {
			
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
			
		}
		
	}
    
}
