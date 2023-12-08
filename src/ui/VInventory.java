package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.Player;
import domain.TheAlchemistGame;
import enums.View;
import interfaces.ICurrentUserListener;


public class VInventory extends VComponent implements ICurrentUserListener {
    private Router router = Router.getInstance();

    public VInventory(TheAlchemistGame game) { 
        super(game); 
        game.addCurrentUserListener(this);
    }
    
    @Override
    protected void render() {
        BufferedImage bg = null;
        BufferedImage close = null;
        BufferedImage title = null;
       
        try {
            // bg = ImageIO.read(new File("./src/resources/image/cardDeckBg.png"));
            close = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            title = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel titlePic = new JLabel(new ImageIcon(title.getScaledInstance((int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75), Image.SCALE_SMOOTH)));
        titlePic.setBounds((int)(Window.frame.getWidth() / 2 - title.getWidth() / 2 * 0.75), -16, (int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75));
        

        JButton closePic = new JButton(new ImageIcon(close.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(event -> {
            router.to(View.Board);
        });

        
        panel.add(titlePic);
        panel.add(closePic);

        
    }
    
    @Override
    protected void mounted() {
        int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
        int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);

        JLabel titleText = new JLabel(String.format("%s's Inventory", game.getCurrentUser().name),  SwingConstants.CENTER);

        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 24));
        titleText.setForeground(Color.red);
        titleText.setBounds((int)(Window.frame.getWidth() / 3 - 498 / 2 * 0.75), -16, (int)(498 * 0.75), (int)(109 * 0.75));
        
        JPanel cards = new JPanel();
        cards.setLayout(new BoxLayout(cards, BoxLayout.Y_AXIS));
        
        IntStream.rangeClosed(1, 100)
            .mapToObj(String::valueOf)
            .map(number -> new JLabel("Label " + number))
            .forEach(cards::add);
        
        JScrollPane scrollPane = new JScrollPane(cards);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(30, 80, width - 60, height - 110);
        scrollPane.setPreferredSize(new Dimension(width - 60, height - 110));

        System.err.println("Hello");


        panel.add(titleText);
        panel.add(scrollPane);
    }

    @Override
    public void onCurrentUserChangeEvent() {
        // TODO Auto-generated method stub
        System.out.println("I ınvented observer pattern d'dem");
    }

}
