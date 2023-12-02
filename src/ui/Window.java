package ui;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;

import domain.TheAlchemistGame;

public class Window {

    static JFrame window;
    private TheAlchemistGame game;

    public Window(String title, int width, int height, TheAlchemistGame game) {
    	this.game = game;
        window = new JFrame(title);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
 
       
        
        VLogin loginView = new VLogin(game);
        JPanel loginPanel = loginView.getContentPanel();
        loginPanel.setVisible(true);
        
        
        window.add(loginPanel);
        window.setVisible(true);
       


    }

    public void setRegister(TheAlchemistGame g) {
        this.game = g;
    }






    
    
}
