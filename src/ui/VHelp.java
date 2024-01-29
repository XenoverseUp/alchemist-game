package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

import ui.framework.VComponent;


public class VHelp extends VComponent {
    @Override
    protected void render() {
        
        JLabel title = new JLabel("KU Alchemists: The Academic Concoction - Help Page");
        title.setBounds(190, 10, 1000, 30);
        title.setForeground(Color.BLACK);;
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Itim-Regular", Font.BOLD, 25));
        panel.add(title);


        JLabel introduction = new JLabel("<html>Immerse yourself in the mysterious domain of KU Alchemists: The Academic Concoction," +
        "where being the most revered alchemist in the academic realm requires a combination of skill,logic and chance.<br>" +
        "Earn your position as a master alchemist by discovering the mysteries of magical substances, concocting potent potions," +
        "and publishing theories.</html>");
        introduction.setBounds(10, 30, 10000000, 90);
        panel.add(introduction);
       
        JLabel Game_Objects = new JLabel("<html>Game Objects:<br><br>" + "Here are some of the features of the game board that will serve as your alchemical playground:<br>" + 
            "- An inventory of all ingredients is kept in the Ingredient Storage.<br>" +
            "- The area where you can mix items to make potions is called the Potion Brewing Area.<br>" +
            "- You can keep track of all the published works supporting your beliefs with the Publication Track.<br>" +
            "- Ingredient qualities can be inferred using the Deduction Board.<br>" + 
            "- A Player's avatar on the game board can be represented by a Token, and these Tokens are used to keep track of the Player's location and resources.<br>" +
            "- Each Ingredient has its own distinct identity, name and set of attributes; they are the building blocks of potions.<br>" + 
            "- Your brewing ability will be put to use in the form of Potions, which are unique inventions with their own recipes and values.<br>" + 
            "- Players can publish their hypotheses using Publication Cards, which come with different requirements and prizes.<br>" + 
            "- Artifact Cards are a kind of special power-up card that can be used to provide the player special powers or effects.<br>" + 
            "- Players can depict their hypotheses regarding the characteristics of ingredients on the Deduction Board using Alchemy Markers.<html>");
        Game_Objects.setBounds(10, 30, 10000000, 380);
        panel.add(Game_Objects);
        
        JLabel Game_Features = new JLabel("<html>Key Features of the Game:<br><br>" + 
            "- Get in charge of the action by pausing and resuming the game whenever you'd like.<br>" +
            "- Help Screen (Which you are in right now), is here to explain how everything works in the game.<br>" + 
            "- At the beginning of the game in the login screen, players can choose an avatar and a nickname.<br>" + 
            "- The last screen before the game ends, reveals the scores of the players and who won the game.<html>");
        Game_Features.setBounds(10, 40, 10000000, 710);
        panel.add(Game_Features);
    


        JLabel How_To_Play = new JLabel("<html>How To Play:<br><br>" + 
            "A two-player game where each round costs roughly three minutes and players take turns doing different tasks.<br>" + 
            "In 1st round, gather materials, turn them into gold, buy objects and do experiments.<br>" + 
            "In addition to 1st round, sell potions and publish hypptheses in the 2nd round.<br>" + 
            "Round 3 expands the array of actions from Rounds 1 and 2, including the capacity to support or refute beliefs.<br><br>" +
            "Final Scores: Final scores are determined by a combination of gold pieces, artifact cards and reputation points.<br><br>" + 
             "While you embark on your alchemical adventure, be sure to read each section thoroughly for detailed information and to come back to this help page for any questions you may have. May the most skilled alchemist triumph!<br>Best of luck!<html>");
        How_To_Play.setBounds(10, 30, 10000000, 1060);
        panel.add(How_To_Play);  
        

        //BufferedImage img = null;
        //try {
            //img = ImageIO.read(new File("./src/resources/image/blurred-bg.png"));
        //} catch (IOException e) {
            //System.out.println(e);
        //}

        //JLabel background = new JLabel(new ImageIcon(img));
        //background.setBounds(new Rectangle(0,0,Window.frame.getWidth(), Window.frame.getHeight()));
        //panel.add(background);





    }

}
