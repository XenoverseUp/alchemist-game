package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.TheAlchemistGame;
import enums.View;
import interfaces.ICurrentUserListener;


public class VInventory extends VComponent implements ICurrentUserListener {
    private Router router = Router.getInstance();
    private JLabel titleText;
    private JLabel background;
    
    private JScrollPane scrollPane;

    private BufferedImage BHammer;
    private BufferedImage BBirdClaw;
    private BufferedImage BFern;
    private BufferedImage BMandrakeRoot;
    private BufferedImage BMoonshade;
    private BufferedImage BMushroom;
    private BufferedImage BRavensFeather;
    private BufferedImage BScorpionTail;
    private BufferedImage BWartyToad;



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
            bg = ImageIO.read(new File("./src/resources/image/inventoryBg.png"));
            close = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            title = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
            BHammer =  ImageIO.read(new File("./src/resources/image/HUD/hammer.png"));
            BBirdClaw = ImageIO.read(new File("./src/resources/image/ingredientCards/birdClaw.png"));
            BFern = ImageIO.read(new File("./src/resources/image/ingredientCards/fern.png"));
            BMandrakeRoot = ImageIO.read(new File("./src/resources/image/ingredientCards/mandrakeRoot.png"));
            BMoonshade = ImageIO.read(new File("./src/resources/image/ingredientCards/moonshade.png"));
            BMushroom = ImageIO.read(new File("./src/resources/image/ingredientCards/mushroom.png"));
            BRavensFeather = ImageIO.read(new File("./src/resources/image/ingredientCards/ravensFeather.png"));
            BScorpionTail = ImageIO.read(new File("./src/resources/image/ingredientCards/scorpionTail.png"));
            BWartyToad = ImageIO.read(new File("./src/resources/image/ingredientCards/wartyToad.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel titlePic = new JLabel(new ImageIcon(title.getScaledInstance((int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75), Image.SCALE_SMOOTH)));
        titlePic.setBounds((int)(Window.frame.getWidth() / 2 - title.getWidth() / 2 * 0.75), -16, (int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75));
        
        titleText = new JLabel("",  SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds(titlePic.getBounds());

        JButton closePic = new JButton(new ImageIcon(close.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        closePic.setBounds(10, 10, 60, 60);
        closePic.addActionListener(e -> router.to(View.Board));

        background = new JLabel(new ImageIcon(bg));
        background.setBounds(0, 0, Window.frame.getWidth(), Window.frame.getHeight());

        int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);
        int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);


        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, width, height);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(titleText);
        panel.add(titlePic);
        panel.add(closePic);  
        panel.add(scrollPane);
        panel.add(background);
    }
    
    @Override
    protected void mounted() {
        this.update();
    }

    private void update() {
        scrollPane.setViewportView(null);
        int width = Window.frame.getWidth() - (Window.frame.getInsets().left + Window.frame.getInsets().right);

        this.titleText.setText(String.format("%s's Inventory", game.getCurrentUser().name));
        
        JPanel cards = new JPanel(new WrapLayout(FlowLayout.CENTER, 24, 24));
        cards.setOpaque(false);

        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(width - 100, 20));
        marginTop.setOpaque(false);
        cards.add(marginTop);

        JLabel ingredientsTitle = new JLabel("Ingredient Cards", SwingConstants.CENTER);
        ingredientsTitle.setFont(new Font("Itim-Regular", Font.BOLD, 36));
        ingredientsTitle.setPreferredSize(new Dimension(width - 100, 100));
        ingredientsTitle.setForeground(Color.WHITE);
        cards.add(ingredientsTitle);
        
        game.getCurrentUser()
            .inventory
            .getIngredientCards()
            .stream()
            .map(card -> this.generateCard("ingredient", card.getName()))
            .forEach(cards::add);

        if (game.getCurrentUser().inventory.getIngredientCards().size() == 0) {
             JLabel emptyIngredientsText = new JLabel("You don't have any ingredient cards. Visit the card deck to forage.", SwingConstants.CENTER);
            emptyIngredientsText.setFont(new Font("Itim-Regular", Font.PLAIN, 18));
            emptyIngredientsText.setPreferredSize(new Dimension(width - 100, 100));
            emptyIngredientsText.setForeground(Color.WHITE);
            cards.add(emptyIngredientsText);
        }
        
        JLabel artifactsTitle = new JLabel("Artifact Cards", SwingConstants.CENTER);
        artifactsTitle.setFont(new Font("Itim-Regular", Font.BOLD, 36));
        artifactsTitle.setForeground(Color.WHITE);
        artifactsTitle.setPreferredSize(new Dimension(width - 100, 100));
        cards.add(artifactsTitle);
        
        // game.getCurrentUser()
        //     .inventory
        //     .getArtifactCards()
        //     .stream()
        //     .map(card -> this.generateCard(card.getName(), card.getDescription(), card.getPrice(), "artifact"))
        //     .forEach(cards::add);


        if (game.getCurrentUser().inventory.getArtifactCards().size() == 0) {
             JLabel emptyIngredientsText = new JLabel("You don't have any artifact cards. Visit the card deck to buy.", SwingConstants.CENTER);
            emptyIngredientsText.setFont(new Font("Itim-Regular", Font.PLAIN, 18));
            emptyIngredientsText.setPreferredSize(new Dimension(width - 100, 100));
            emptyIngredientsText.setForeground(Color.WHITE);
            cards.add(emptyIngredientsText);
        }

        JPanel marginBottom = new JPanel();
        marginBottom.setPreferredSize(new Dimension(width - 100, 100));
        marginBottom.setOpaque(false);
        cards.add(marginBottom);

        scrollPane.setViewportView(cards);
        scrollPane.revalidate();
        scrollPane.repaint();
    }


    private JPanel generateCard(String type, String name) {

        BufferedImage cardBuffer = null;

        if (type.equals("ingredient")) {
            if (name.equals("bird claw")) cardBuffer = BBirdClaw;
            else if (name.equals("fern")) cardBuffer = BFern;
            else if (name.equals("mandrake root")) cardBuffer = BMandrakeRoot;
            else if (name.equals("moonshade")) cardBuffer = BMoonshade;
            else if (name.equals("mushroom")) cardBuffer = BMushroom;
            else if (name.equals("raven's feather")) cardBuffer = BRavensFeather;
            else if (name.equals("scorpion tail")) cardBuffer = BScorpionTail;
            else if (name.equals("warty toad")) cardBuffer = BWartyToad;
        }
            
        JPanel card = new JPanel(null);
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(cardBuffer.getWidth(), cardBuffer.getHeight()));

        JLabel bg = new JLabel(new ImageIcon(cardBuffer));
        bg.setBounds(0, 0, cardBuffer.getWidth(), cardBuffer.getHeight());

        if (type.equals("ingredient")) {            
            JButton transmuteButton = new JButton(new ImageIcon(BHammer.getScaledInstance(BHammer.getWidth() / 2, BHammer.getHeight() / 2, Image.SCALE_SMOOTH)));
            transmuteButton.setBounds(cardBuffer.getWidth() / 2 - BHammer.getWidth() / 4 , cardBuffer.getHeight() - BHammer.getHeight() / 2 - 36, BHammer.getWidth() / 2, BHammer.getHeight() / 2); 
            transmuteButton.setOpaque(false);
            transmuteButton.setContentAreaFilled(false);
            transmuteButton.setBorderPainted(false);
            transmuteButton.setFocusable(false);
            transmuteButton.addActionListener(event -> {
                game.transmuteIngredient(name);
                this.update();            
            });   
            
            card.add(transmuteButton);
        }
        
        card.add(bg);

        return card;
    }


    @Override
    public void onCurrentUserChange() {
        this.update();
    }

}
