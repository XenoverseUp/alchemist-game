package ui.framework;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import enums.View;

public class AssetLoader {
    private BufferedImage BBirdClaw;
    private BufferedImage BFern;
    private BufferedImage BMandrakeRoot;
    private BufferedImage BMoonshade;
    private BufferedImage BMushroom;
    private BufferedImage BRavensFeather;
    private BufferedImage BScorpionTail;
    private BufferedImage BWartyToad;

    private BufferedImage BBirdClawIcon;
    private BufferedImage BFernIcon;
    private BufferedImage BMandrakeRootIcon;
    private BufferedImage BMoonshadeIcon;
    private BufferedImage BMushroomIcon;
    private BufferedImage BRavensFeatherIcon;
    private BufferedImage BScorpionTailIcon;
    private BufferedImage BWartyToadIcon;

    private BufferedImage BPotionBrewingAreaBackground;

    private BufferedImage BClose;
    private BufferedImage BTitle;

    public ArrayList<String> ingredientNames = new ArrayList<>() {{
        add("mushroom");
        add("fern");
        add("warty toad");
        add("bird claw");
        add("moonshade");
        add("mandrake root");
        add("scorpion tail");
        add("raven's feather");
    }};

    static private AssetLoader instance = null;

    public static synchronized AssetLoader getInstance() {
        if (instance == null) 
            instance = new AssetLoader();

        return instance;
    }


    private AssetLoader() {
        try {
            BBirdClaw = ImageIO.read(new File("./src/resources/image/ingredientCards/birdClaw.png"));
            BFern = ImageIO.read(new File("./src/resources/image/ingredientCards/fern.png"));
            BMandrakeRoot = ImageIO.read(new File("./src/resources/image/ingredientCards/mandrakeRoot.png"));
            BMoonshade = ImageIO.read(new File("./src/resources/image/ingredientCards/moonshade.png"));
            BMushroom = ImageIO.read(new File("./src/resources/image/ingredientCards/mushroom.png"));
            BRavensFeather = ImageIO.read(new File("./src/resources/image/ingredientCards/ravensFeather.png"));
            BScorpionTail = ImageIO.read(new File("./src/resources/image/ingredientCards/scorpionTail.png"));
            BWartyToad = ImageIO.read(new File("./src/resources/image/ingredientCards/wartyToad.png"));
            
            BBirdClawIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/birdClawIcon.png"));
            BFernIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/fernIcon.png"));
            BMandrakeRootIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/mandrakeRootIcon.png"));
            BMoonshadeIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/moonshadeIcon.png"));
            BMushroomIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/mushroomIcon.png"));
            BRavensFeatherIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/ravensFeatherIcon.png"));
            BScorpionTailIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/scorpionTailIcon.png"));
            BWartyToadIcon = ImageIO.read(new File("./src/resources/image/ingredientCards/wartyToadIcon.png"));
            
            BPotionBrewingAreaBackground =  ImageIO.read(new File("./src/resources/image/pbaBackground.png"));
            
            BClose = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            BTitle = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public BufferedImage getIngredientCard(String name) {
        if (name.equalsIgnoreCase("bird claw")) return BBirdClaw;
        if (name.equalsIgnoreCase("fern")) return BFern;
        if (name.equalsIgnoreCase("mandrake root")) return BMandrakeRoot;
        if (name.equalsIgnoreCase("moonshade")) return BMoonshade;
        if (name.equalsIgnoreCase("mushroom")) return BMushroom;
        if (name.equalsIgnoreCase("raven's feather")) return BRavensFeather;
        if (name.equalsIgnoreCase("scorpion tail")) return BScorpionTail;
        if (name.equalsIgnoreCase("warty toad")) return BWartyToad;


        return null;
    }

    public Image getIngredientCard(String name, double scale) {
        if (name.equalsIgnoreCase("bird claw")) return this.scale(BBirdClaw, scale);
        if (name.equalsIgnoreCase("fern")) return this.scale(BFern, scale);
        if (name.equalsIgnoreCase("mandrake root")) return this.scale(BMandrakeRoot, scale);
        if (name.equalsIgnoreCase("moonshade")) return this.scale(BMoonshade, scale);
        if (name.equalsIgnoreCase("mushroom")) return this.scale(BMushroom, scale);
        if (name.equalsIgnoreCase("raven's feather")) return this.scale(BRavensFeather, scale);
        if (name.equalsIgnoreCase("scorpion tail")) return this.scale(BScorpionTail, scale);
        if (name.equalsIgnoreCase("warty toad")) return this.scale(BWartyToad, scale);

        return null;
    }

    public BufferedImage getIngredientIcon(String name) {
        if (name.equalsIgnoreCase("bird claw")) return BBirdClawIcon;
        if (name.equalsIgnoreCase("fern")) return BFernIcon;
        if (name.equalsIgnoreCase("mandrake root")) return BMandrakeRootIcon;
        if (name.equalsIgnoreCase("moonshade")) return BMoonshadeIcon;
        if (name.equalsIgnoreCase("mushroom")) return BMushroomIcon;
        if (name.equalsIgnoreCase("raven's feather")) return BRavensFeatherIcon;
        if (name.equalsIgnoreCase("scorpion tail")) return BScorpionTailIcon;
        if (name.equalsIgnoreCase("warty toad")) return BWartyToadIcon;

        return null;
    }

    public BufferedImage getBackground(View view) {
        switch (view) {
            case PotionBrewingArea:
                return BPotionBrewingAreaBackground;
            default:
                return null;
        }
    }

    public Image getCloseIcon() {
        return this.scale(BClose, 60, 60);
    }

    public Image getPageTitle() {
        return this.scale(BTitle, 0.75);
    }

    private Image scale(BufferedImage i, double scale) {
        return i.getScaledInstance((int)(i.getWidth() * scale), (int)(i.getHeight() * scale), Image.SCALE_SMOOTH);
    }
  
    private Image scale(BufferedImage i, int width, int height) {
        return i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

}
