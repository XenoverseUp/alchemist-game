package ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import interfaces.Renderable;

public class VLogin implements Renderable {
    JPanel panel;

    public VLogin() {
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setSize(Window.window.getSize());
        this.buildView();
    }

    public void buildView() {
        JLabel nameApplication = new JLabel();
		nameApplication.setText("SuperDude");
		nameApplication.setForeground(new Color(255,50,50));
        
        JLabel nameApplication1 = new JLabel();
		nameApplication1.setText("SuperDsude");
		nameApplication1.setForeground(new Color(255,50,50));
        
        JButton button = new JButton("Hello");
		button.setText("SuperDsude");
		button.setForeground(new Color(255,50,50));
        
        JTextField tf = new JTextField(16);
		tf.setForeground(new Color(255,50,50));
        tf.setPreferredSize(new Dimension(200, 20));




        this.panel.add(nameApplication);
        this.panel.add(nameApplication1);
        this.panel.add(button);
        this.panel.add(tf);
        
    }

    public JPanel getContentPanel() {
        return this.panel;
    }
    
}
