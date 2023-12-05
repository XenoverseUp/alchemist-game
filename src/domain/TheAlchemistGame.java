package domain;

import enums.Avatar;
import javax.swing.SwingUtilities;

public class TheAlchemistGame {
    private Auth auth;
    private Board gameBoard;
    

    public TheAlchemistGame() {
    	auth = new Auth();
        gameBoard = new Board(auth);
    	
    }
    
    public void createUser(String userName, Avatar a) {
    	auth.createUser(userName, a);
    }
    
    public void toggleCurrentUser() {
    	System.out.println("Toggled");
    	gameBoard.toggleCurrentUser();
    }

    public Player getCurrentUser() {
        return auth.players.get(auth.currentUser);
    }

    public void initializeGame() {
    	
    	gameBoard = new Board(this.auth);
    	gameBoard.ingredientCardDeck.shuffle();
    	gameBoard.dealCards();
    	gameBoard.artifactCardDeck.shuffle();
    	gameBoard.dealGolds();
    	
    } 
    
    public void forageIngredient() {
    	
    	gameBoard.forageIngredient();
    	
    }

    public void transmuteIngredient(int ingredientId) {
    	gameBoard.transmuteIngredient(ingredientId);
    	
    }

    
    public void buyArtifact() {
    	gameBoard.buyArtifact();
    }
    

}
