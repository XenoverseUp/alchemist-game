package domain;
import enums.Avatar;
import enums.Potion;

public class Player {
    public int id;
    public String name;
    public Avatar avatar;
    public Inventory inventory;
    private int sickness = 0;
    private int reputation = 0;
    public int leftActions = 2;
    public int extraActions = 0;
    protected DeductionBoard deductionBoard;
    protected PlayerBoard playerBoard;

    public Player(int id, String name, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.inventory = new Inventory();
        this.deductionBoard = new DeductionBoard();
        this.playerBoard = new PlayerBoard();
    }

    public void increaseReputation(int amount) {
        this.reputation += amount;
    }

    public void decreaseReputation(int amount) {
        this.reputation -= amount;
    }

    public void increaseSickness(int amount) {
        this.sickness += amount;

        if (this.sickness == 3){
            this.sickness = 0;
            this.inventory.setGold(0);
        }
    }

    public void decreaseSickness(int amount) {
        if (amount <= this.sickness){
            this.sickness -= amount;
        }
    }

    public void setSickness(int value) {
        this.sickness = value;
    }

    public int getSickness() {
        return sickness;
    }

    public int getReputation() {
        return reputation;
    }

    private void setExtraActions(int extraActions) {
        this.extraActions = extraActions;
    }


    public void use(Potion p, String testOn) throws Exception {
       /* 
        * @requires: p and testOn are not NULL,
        *            testOn = "self" or "student" only
        * 
        * @effects: if testOn is not "self" or "student" throws Exception
        *           if testOn is student, the gold of the player is decreased by 1
        *           if testOn is self, below effects imply.
        *           if p is Healt, sickness is of the player decreased by 1
        *           if p is Poison, sickness of the player increased by 1
        *           if p is Wisdom, reputation of the player is increased by 1
        *           if p is Insanity, reputation of the player is decreased by 1
        *           if p is Speed, extra actions set to 1
        *           if p is Paralysis, extra actions set to -2
        *           if p is Neutral, nothing happens.          
        */
        if (!(testOn.equals("self") || testOn.equals("student")))
            throw new Exception("You can test your potion on only yourself of your student.");

        switch (p) {
            case Health: {
                if (testOn.equals("self")) decreaseSickness(1);
                else this.inventory.spendGold(1);
                return;
            }

            case Poison: {
                if (testOn.equals("self")) increaseSickness(1);
                else this.inventory.spendGold(1);
                return;
            }

            case Wisdom: {
                if (testOn.equals("self")) increaseReputation(1);
                else this.inventory.spendGold(1);
                return;   
            }

            case Insanity: {
                if (testOn.equals("self")) decreaseReputation(1);
                else this.inventory.spendGold(1);
                return;   
            }

            case Speed: {
                if (testOn.equals("self")) setExtraActions(1);
                else this.inventory.spendGold(1);
                return;
            }

            case Paralysis: {
                if (testOn.equals("self")) setExtraActions(-2);
                else this.inventory.spendGold(1);
                return;
            }

            case Neutral: {
                if (testOn.equals("student")) this.inventory.spendGold(1);
                return;
            }
        }
    }

    public int[] calculateFinalScore(){ 
        int finalPoints = (this.reputation * 10);
        finalPoints += (this.inventory.getGold() / 3);
        int leftoverGold = this.inventory.getGold() % 3;
        int[] score =  {finalPoints, leftoverGold};
        return score;
    }




}
