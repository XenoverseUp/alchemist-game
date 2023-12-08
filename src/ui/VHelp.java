package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VHelp extends VComponent {


    @Override
    protected void render() {
        
        BufferedImage help_img = null;

        try {
            help_img = ImageIO.read(new File("./src/resources/image/blurred-bg.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        
        JLabel help_bg = new JLabel(new ImageIcon(help_img));
        help_bg.setBounds(new Rectangle(0,0,Window.frame.getWidth(), Window.frame.getHeight()));
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(new Rectangle(0,0,Window.frame.getWidth(), Window.frame.getHeight()));

        layeredPane.add(help_bg, JLayeredPane.DEFAULT_LAYER);
        
        
        JLabel title = new JLabel("KU Alchemists: The Academic Concoction - Help Page");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel PTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        PTitle.add(title);

        PTitle.setBounds(0, 10, panel.getWidth(), 30);
        layeredPane.add(PTitle, JLayeredPane.PALETTE_LAYER);
        PTitle.setForeground(Color.WHITE);
        
        


       
        // Create and style the introduction text area
        JTextArea Introduction = new JTextArea(
            "Immerse yourself in the mysterious domain of \"KU Alchemists: The Academic Concoction,\" where being the most revered alchemist in the " +
            "academic realm requires a combination of skill, logic and chance. Earn your position as a master " +
            "alchemist by discovering the mysteries of magical substances, concocting potent potions, " +
            "and publishing theories."
        );
        Introduction.setFont(new Font("Serif", Font.PLAIN, 14));
        Introduction.setWrapStyleWord(true);
        Introduction.setLineWrap(true);
        Introduction.setOpaque(false);
        Introduction.setEditable(false);
        Introduction.setForeground(Color.WHITE);

        int introY = PTitle.getY() + PTitle.getHeight() + 20;

        Introduction.setBounds(20, introY, panel.getWidth() - 40, 60);
        panel.add(Introduction);

        JTextArea Game_Objects = new JTextArea(
            "Game Objects:\n\n" + "Here are some of the features of the game board that will serve as your alchemical playground:\n" + 
            "- An inventory of all ingredients is kept in the Ingredient Storage.\n" +
            "- The area where you can mix items to make potions is called the Potion Brewing Area.\n" +
            "- You can keep track of all the published works supporting your beliefs with the Publication Track.\n" +
            "- Ingredient qualities can be inferred using the Deduction Board.\n" + 
            "- A Player's avatar on the game board can be represented by a Token, and these Tokens are used to keep track of the Player's location and resources.\n" +
            "- Each Ingredient has its own distinct identity, name and set of attributes; they are the building blocks of potions.\n" + 
            "- Your brewing ability will be put to use in the form of Potions, which are unique inventions with their own recipes and values.\n" + 
            "- Players can publish their hypotheses using Publication Cards, which come with different requirements and prizes.\n" + 
            "- Artifact Cards are a kind of special power-up card that can be used to provide the player special powers or effects.\n" + 
            "- Players can depict their hypotheses regarding the characteristics of ingredients on the Deduction Board using Alchemy Markers."
        );
        Game_Objects.setFont(new Font("Serif", Font.BOLD, 14));
        Game_Objects.setWrapStyleWord(true);
        Game_Objects.setLineWrap(true);
        Game_Objects.setOpaque(false);
        Game_Objects.setEditable(false);
        Game_Objects.setForeground(Color.WHITE);

        int Y_gameobjects = Introduction.getY() + Introduction.getHeight() + 20;

        Game_Objects.setBounds(20, Y_gameobjects, panel.getWidth() - 40, 260); // Adjust the height as necessary
        

        JTextArea Game_Features = new JTextArea(
            "Key Features of the Game:\n\n" + 
            "- Get in charge of the action by pausing and resuming the game whenever you'd like.\n" +
            "- Help Screen (Which you are in right now), is here to explain how everything works in the game.\n" + 
            "- At the beginning of the game in the login screen, players can choose an avatar and a nickname.\n" + 
            "- The last screen before the game ends, reveals the scores of the players and who won the game."
        );
        Game_Features.setFont(new Font("Serif", Font.BOLD, 14));
        Game_Features.setWrapStyleWord(true);
        Game_Features.setLineWrap(true);
        Game_Features.setOpaque(false);
        Game_Features.setEditable(false);
        Game_Features.setForeground(Color.WHITE);

        int Y_gamefeatures = Introduction.getY() + Introduction.getHeight() + 265;

        Game_Features.setBounds(20, Y_gamefeatures, panel.getWidth() - 40, 260); // Adjust the height as necessary
        

        JTextArea How_To_Play = new JTextArea(
            "How To Play:\n\n" + 
            "A two-player game where each round costs roughly three minutes and players take turns doing different tasks.\n" + 
            "In 1st round, gather materials, turn them into gold, buy objects and do experiments.\n" + 
            "In addition to 1st round, sell potions and publish hypptheses in the 2nd round.\n" + 
            "Round 3 expands the array of actions from Rounds 1 and 2, including the capacity to support or refute beliefs.\n\n" +
            "Final Scores: Final scores are determined by a combination of gold pieces, artifact cards and reputation points.\n\n" + 
             "While you embark on your alchemical adventure, be sure to read each section thoroughly for detailed information and to come back to this help page for any questions you may have. May the most skilled alchemist triumph! Best of luck!"

        );
        How_To_Play.setFont(new Font("Serif", Font.BOLD, 14));
        How_To_Play.setWrapStyleWord(true);
        How_To_Play.setLineWrap(true);
        How_To_Play.setOpaque(false);
        How_To_Play.setEditable(false);

        How_To_Play.setForeground(Color.WHITE);
        int Y_howtoplay = Introduction.getY() + Introduction.getHeight() + 390;

        How_To_Play.setBounds(20, Y_howtoplay, panel.getWidth() - 40, 260); // Adjust the height as necessary
        panel.add(Game_Features);
        panel.add(How_To_Play);
        panel.add(Game_Objects);

        // Ensure the panel updates to display the new components
        panel.add(layeredPane);
        panel.revalidate();
        panel.repaint();




    }

}
