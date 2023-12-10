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
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Player;
import domain.TheAlchemistGame;
import enums.View;
import interfaces.ICurrentUserListener;


public class VInventory extends VComponent implements ICurrentUserListener {
    private Router router = Router.getInstance();
    private JLabel titleText;
    private JLabel background;
    private BufferedImage ingredientCard;
    private BufferedImage hammer;
    private JScrollPane scrollPane;

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
            ingredientCard = ImageIO.read(new File("./src/resources/image/ingredientCard.png"));
            hammer =  ImageIO.read(new File("./src/resources/image/HUD/hammer.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel titlePic = new JLabel(new ImageIcon(title.getScaledInstance((int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75), Image.SCALE_SMOOTH)));
        titlePic.setBounds((int)(Window.frame.getWidth() / 2 - title.getWidth() / 2 * 0.75), -16, (int)(title.getWidth() * 0.75), (int)(title.getHeight() * 0.75));
        
        titleText = new JLabel("",  SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds((int)(Window.frame.getWidth() / 2 - 498 / 2 * 0.75), -16, (int)(498 * 0.75), (int)(109 * 0.75));

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
        int height = Window.frame.getHeight() - (Window.frame.getInsets().top + Window.frame.getInsets().bottom);

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
            .map(card -> this.generateCard(card.getName(), card.getColor(), card.getValue(), "ingredient", card.getId()))
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
        
        game.getCurrentUser()
            .inventory
            .getArtifactCards()
            .stream()
            .map(card -> this.generateCard(card.getName(), card.getEffectType(), card.getPrice(), "artifact", 0))
            .forEach(cards::add);


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


    private JPanel generateCard(String name, String colorValue, int value, String type, int id) {
        JPanel card = new JPanel(null);
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(ingredientCard.getWidth(), ingredientCard.getHeight()));

        JLabel bg = new JLabel(new ImageIcon(ingredientCard));
        bg.setBounds(0, 0, ingredientCard.getWidth(), ingredientCard.getHeight());
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBounds(new Rectangle(0, 126, ingredientCard.getWidth(), 100));
        content.setOpaque(false);

        JLabel title = new JLabel(name, SwingConstants.CENTER);
        title.setFont(new Font("Cubano", Font.PLAIN, 34));
        title.setForeground(new Color(62, 214, 42));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        JLabel color = new JLabel(colorValue, SwingConstants.CENTER);
        color.setFont(new Font("Cubano", Font.PLAIN, 20));
        color.setForeground(Color.WHITE);
        color.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(12));
        content.add(color);

        JLabel price = new JLabel(Integer.toString(value), SwingConstants.CENTER);
        price.setBounds(0, ingredientCard.getHeight() - 80, ingredientCard.getWidth(), 50);
        price.setForeground(new Color(241, 179, 43));
        price.setFont(new Font("Cubano", Font.PLAIN, 32));


        if (type == "ingredient") {    
            JButton transmuteButton = new JButton(new ImageIcon(hammer.getScaledInstance(hammer.getWidth() / 2, hammer.getHeight() / 2, Image.SCALE_SMOOTH)));
            transmuteButton.setBounds(10, 10, hammer.getWidth() / 2, hammer.getHeight() / 2); 
            transmuteButton.setOpaque(false);
            transmuteButton.setContentAreaFilled(false);
            transmuteButton.setBorderPainted(false);
            transmuteButton.setFocusable(false);
            transmuteButton.addActionListener(event -> {
                game.transmuteIngredient(id);
                this.update();            
            });   

            card.add(transmuteButton);
        }

        card.add(price);
        card.add(content);
        card.add(bg);

        return card;
    }


    @Override
    public void onCurrentUserChange() {
        this.update();
    }

}
