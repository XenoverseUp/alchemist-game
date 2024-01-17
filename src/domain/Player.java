package domain;
import java.io.Serializable;

import enums.Avatar;
import enums.Potion;
import error.NotEnoughActionsException;

public class Player implements Serializable {
    public int id;
    public String name;
    public Avatar avatar;
    public Inventory inventory;
    private Integer score = null;
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

    public int getScore() {
        return this.score;
    }

    private void setExtraActions(int extraActions) {
        this.extraActions = extraActions;
    }

    public void checkLeftActions() throws NotEnoughActionsException {
        if (this.leftActions < 1){
            throw new NotEnoughActionsException();
        }
    }
    

    public void decreaseLeftActions() throws NotEnoughActionsException{
        if (this.leftActions > 0){
            this.leftActions -= 1;
        }
        else{
            throw new NotEnoughActionsException();
        }
    }

    public void calculateTotalActions(){
        this.leftActions = 2 + this.extraActions;
        this.extraActions = 0;
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

            if (testOn.equals("student")){
                this.inventory.spendGold(1);
                return;
            }
            switch (p) {
                case Health: {
                    decreaseSickness(1);
                    return;
                }
    
                case Poison: {
                    increaseSickness(1);
                    return;
                }
    
                case Wisdom: {
                    increaseReputation(1);
                    return;   
                }
    
                case Insanity: {
                    decreaseReputation(1);
    
                    return;   
                }
    
                case Speed: {
                    setExtraActions(1);
                    return;
                }
    
                case Paralysis: {
                    setExtraActions(-2);
                    return;
                }
    
                case Neutral: {
                    return;
                }
            }
    }

    public void sell(Potion p){
        switch(p) {
            case Poison:
            case Insanity:
            case Paralysis: {
                this.inventory.spendGold(2);
                return;
            }
            case Health:
            case Wisdom:
            case Speed:{
                this.inventory.addGold(3);
                return;
            }
            case Neutral:{
                this.inventory.addGold(1);
            }    
        }
    }

    public int[] calculateFinalScore(){ 
        int finalPoints = (this.reputation * 10);
        finalPoints += (this.inventory.getGold() / 3);
        int leftoverGold = this.inventory.getGold() % 3;
        int[] score =  {finalPoints, leftoverGold};
        this.score = finalPoints;
        return score;
    }

    
}
