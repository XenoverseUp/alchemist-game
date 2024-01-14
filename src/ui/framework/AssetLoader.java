package ui.framework;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import enums.Avatar;
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

    private BufferedImage BThunderous;
    private BufferedImage BMystical;
    private BufferedImage BCelestial;
    private BufferedImage BCrimson;
    private BufferedImage BLuminous;
    private BufferedImage BGalactic;
    private BufferedImage BEnigmatic;
    private BufferedImage BSerene;

    private BufferedImage BThunderousBig;
    private BufferedImage BMysticalBig;
    private BufferedImage BCelestialBig;
    private BufferedImage BCrimsonBig;
    private BufferedImage BLuminousBig;
    private BufferedImage BGalacticBig;
    private BufferedImage BEnigmaticBig;
    private BufferedImage BSereneBig;

    private BufferedImage BPotionBrewingAreaBackground;
    private BufferedImage BOnlineSelectionBackground;
    private BufferedImage BLobbyBackground;
    private BufferedImage BCardDeckBackground;
    private BufferedImage BArtifactShopBackground;
    private BufferedImage BInventoryBackground;
    private BufferedImage BBlurredBoard;
    private BufferedImage BOnlineLoginBackground;
    private BufferedImage BLoginBackground;
    private BufferedImage BFinalBackground;

    private BufferedImage BClose;
    private BufferedImage BTitle;
    private BufferedImage BCheckMark;
    private BufferedImage BOldFabric;
    private BufferedImage BSelectionStripe;
    private BufferedImage BLeftArrow;
    private BufferedImage BStartButton;
    private BufferedImage BStartButtonPressed;
    private BufferedImage BFinishButton;
    private BufferedImage BFinishButtonPressed;
    private BufferedImage BNameRibbonYellow;
    private BufferedImage BNameRibbonGreen;
    private BufferedImage BNameRibbonRed;
    private BufferedImage BNameRibbonBlue;

    private BufferedImage BCrown;

    public ArrayList<String> ingredientNames = new ArrayList<String>() {{
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
            
            BThunderous = ImageIO.read(new File("./src/resources/image/avatars/Thunderous.png"));
            BMystical = ImageIO.read(new File("./src/resources/image/avatars/Mystical.png"));
            BCelestial = ImageIO.read(new File("./src/resources/image/avatars/Celestial.png"));
            BCrimson = ImageIO.read(new File("./src/resources/image/avatars/Crimson.png"));
            BLuminous = ImageIO.read(new File("./src/resources/image/avatars/Luminous.png"));
            BGalactic = ImageIO.read(new File("./src/resources/image/avatars/Galactic.png"));
            BEnigmatic = ImageIO.read(new File("./src/resources/image/avatars/Enigmatic.png"));
            BSerene = ImageIO.read(new File("./src/resources/image/avatars/Serene.png"));
            
            BThunderousBig = ImageIO.read(new File("./src/resources/image/avatars/ThunderousBig.png"));
            BMysticalBig = ImageIO.read(new File("./src/resources/image/avatars/MysticalBig.png"));
            BCelestialBig = ImageIO.read(new File("./src/resources/image/avatars/CelestialBig.png"));
            BCrimsonBig = ImageIO.read(new File("./src/resources/image/avatars/CrimsonBig.png"));
            BLuminousBig = ImageIO.read(new File("./src/resources/image/avatars/LuminousBig.png"));
            BGalacticBig = ImageIO.read(new File("./src/resources/image/avatars/GalacticBig.png"));
            BEnigmaticBig = ImageIO.read(new File("./src/resources/image/avatars/EnigmaticBig.png"));
            BSereneBig = ImageIO.read(new File("./src/resources/image/avatars/SereneBig.png"));

            BPotionBrewingAreaBackground =  ImageIO.read(new File("./src/resources/image/pbaBackground.png"));
            BOnlineSelectionBackground =  ImageIO.read(new File("./src/resources/image/onlineSelectionBackground.png"));
            BLobbyBackground =  ImageIO.read(new File("./src/resources/image/lobbyBackground.png"));
            BCardDeckBackground =  ImageIO.read(new File("./src/resources/image/cardDeckBg.png"));
            BArtifactShopBackground =  ImageIO.read(new File("./src/resources/image/cardDeckBg.png"));
            BInventoryBackground = ImageIO.read(new File("./src/resources/image/inventoryBg.png"));
            BBlurredBoard = ImageIO.read(new File("./src/resources/image/notTurnBackground.png"));
            BOnlineLoginBackground = ImageIO.read(new File("./src/resources/image/onlineLoginBackground.png"));
            BLoginBackground = ImageIO.read(new File("./src/resources/image/loginBackground.png"));
            BFinalBackground =  ImageIO.read(new File("./src/resources/image/finalBackground.png"));
            
            BClose = ImageIO.read(new File("./src/resources/image/HUD/closeButton.png"));
            BTitle = ImageIO.read(new File("./src/resources/image/HUD/title_large.png"));
            BCheckMark = ImageIO.read(new File("./src/resources/image/HUD/checkMark.png"));
            BOldFabric = ImageIO.read(new File("./src/resources/image/HUD/oldFabric.png"));
            BSelectionStripe = ImageIO.read(new File("./src/resources/image/HUD/selectionStripe.png"));
            BLeftArrow = ImageIO.read(new File("./src/resources/image/HUD/leftArrow.png"));

            BStartButton = ImageIO.read(new File("./src/resources/image/HUD/startButton.png"));
            BStartButtonPressed = ImageIO.read(new File("./src/resources/image/HUD/startButtonPressed.png"));
            BFinishButton = ImageIO.read(new File("./src/resources/image/HUD/finalButton.png"));
            BFinishButtonPressed = ImageIO.read(new File("./src/resources/image/HUD/finalButtonPressed.png"));
            BNameRibbonYellow = ImageIO.read(new File("./src/resources/image/HUD/nameRibbonYellow.png"));
            BNameRibbonGreen = ImageIO.read(new File("./src/resources/image/HUD/nameRibbonGreen.png"));
            BNameRibbonRed = ImageIO.read(new File("./src/resources/image/HUD/nameRibbonRed.png"));
            BNameRibbonBlue = ImageIO.read(new File("./src/resources/image/HUD/nameRibbonBlue.png"));

            BCrown = ImageIO.read(new File("./src/resources/image/HUD/Crown.png"));

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

    public BufferedImage getAvatarImage(Avatar avatar) {
        switch (avatar) {
            case Thunderous:
                return BThunderous;
            case Mystical:
                return BMystical;
            case Celestial:
                return BCelestial;
            case Crimson:
                return BCrimson;
            case Luminous:
                return BLuminous;
            case Galactic:
                return BGalactic;
            case Enigmatic:
                return BEnigmatic;
            case Serene:
                return BSerene;
        }

        return BThunderous;
    }

    public BufferedImage getAvatarImageBig(Avatar avatar) {
        switch (avatar) {
            case Thunderous:
                return BThunderousBig;
            case Mystical:
                return BMysticalBig;
            case Celestial:
                return BCelestialBig;
            case Crimson:
                return BCrimsonBig;
            case Luminous:
                return BLuminousBig;
            case Galactic:
                return BGalacticBig;
            case Enigmatic:
                return BEnigmaticBig;
            case Serene:
                return BSereneBig;
        }

        return BThunderousBig;
    }

    public BufferedImage getNameRibbon(int i) {
        switch (i) {
            case 1: return BNameRibbonBlue;
            case 2: return BNameRibbonRed;
            case 3: return BNameRibbonGreen;
            default: return BNameRibbonYellow;
        }
    }

    public Color getNameRibbonColor(int i) {
        switch (i) {
            case 1: return new Color(0, 55, 66);
            case 2: return new Color(76, 43, 32);
            case 3: return new Color(47, 55, 16);
            default: return new Color(63, 33, 0);
        }
    }

    public BufferedImage getStartButton(boolean pressed) {
        return !pressed ? BStartButton : BStartButtonPressed;
    }

    public BufferedImage getFinishButton(boolean pressed) {
        return !pressed ? BFinishButton : BFinishButtonPressed;
    }

    

    public BufferedImage getBackground(View view) {
        switch (view) {
            case PotionBrewingArea:
                return BPotionBrewingAreaBackground;
            case OnlineSelection:
                return BOnlineSelectionBackground;
            case Lobby:
                return BLobbyBackground;
            case CardDeck:
                return BCardDeckBackground;
            case ArtifactShop:
                return BArtifactShopBackground;
            case Inventory:
                return BInventoryBackground;
            case OnlineLogin:
                return BOnlineLoginBackground;
            case Login:
                return BLoginBackground;
            case FinalScore:
                return BFinalBackground;
            default:
                return null;
        }
    }

    public BufferedImage getCrown(){
        return BCrown;
    }

    public Image getClose() { return this.scale(BClose, 60, 60); }
    public Image getPageBanner() { return this.scale(BTitle, 0.75); }
    public BufferedImage getCheckMark() { return BCheckMark; }    
    public BufferedImage getOldFabric() { return BOldFabric;  }
    public BufferedImage getSelectionStripe() { return BSelectionStripe;  }
    public BufferedImage getLeftArrow() { return BLeftArrow;  }
    public BufferedImage getBlurredBoard() { return BBlurredBoard;  }

    private Image scale(BufferedImage i, double scale) { 
        return i.getScaledInstance((int)(i.getWidth() * scale), (int)(i.getHeight() * scale), Image.SCALE_SMOOTH); 
    }
  
    private Image scale(BufferedImage i, int width, int height) { 
        return i.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
    }

}
