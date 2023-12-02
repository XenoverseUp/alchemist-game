package domain;

import enums.Avatar;

public class TheAlchemistGame {
    public Auth auth;
    public Board gameBoard;
    

    public TheAlchemistGame() {
    	auth = new Auth();
    	
    }
    
    public void createUser(String userName, Avatar a) {
    	auth.createUser(userName, a);
    }

    public void initializeGame() {
    	
    	gameBoard = new Board(this.auth);
    	gameBoard.ingredientCardDeck.shuffle();
    	gameBoard.artifactCardDeck.shuffle();
    	
    	for (Player p: gameBoard.getAuth().players) {
    		p.inventory.addGold(3);
    	}
    } 
}
