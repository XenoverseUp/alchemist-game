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

    public Player(int id, String name, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.inventory = new Inventory();
    }

    public void increaseReputation(int amount) {
        this.reputation += amount;
    }

    public void decreaseReputation(int amount) {
        this.reputation -= amount;
    }

    public void increaseSickness(int amount) {
        this.sickness += amount;
    }

    public void decreaseSickness(int amount) {
        this.sickness -= amount;
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


}
