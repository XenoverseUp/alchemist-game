package domain;
import enums.Avatar;

public class Player {
    public int id;
    public String name;
    public Avatar avatar;
    private int sickness = 0;
    private int reputation = 0;
    

    public Player(int id, String name, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
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


}
