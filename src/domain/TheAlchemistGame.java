package domain;

import java.util.ArrayList;
import java.util.HashMap;

import enums.Avatar;
import enums.DeductionToken;
import enums.Potion;
import interfaces.ICurrentUserListener;

public class TheAlchemistGame {
    private Auth auth;
    private Board gameBoard;

    public TheAlchemistGame() {
    	auth = new Auth();
        gameBoard = new Board(auth);
    }

    public int createUser(String userName, Avatar a) {
       return auth.createUser(userName, a);
    }

    public void toggleCurrentUser() {
        gameBoard.toggleCurrentUser();
    }

    public Player getCurrentUser() {
        return auth.getCurrentPlayer();
    }

    public void initializeGame() {
        gameBoard.ingredientCardDeck.shuffle();
        gameBoard.dealCards();
        gameBoard.artifactCardDeck.shuffle();
        gameBoard.dealGolds();
    }

    public void forageIngredient() {
        gameBoard.forageIngredient();
    }

    public void transmuteIngredient(String ingredientName) {
        gameBoard.transmuteIngredient(ingredientName);
    }

    public int buyArtifact(String name) {
        return gameBoard.buyArtifact(name);
    }

    public void discardArtifact(String name) {
        gameBoard.getAuth().getCurrentPlayer().inventory.discardArtifactCard(name);
    }

    public ArrayList<ArtifactCard> getArtifactCardDeck(){
		return (ArrayList<ArtifactCard>)this.gameBoard.artifactCardDeck.getArtifactCardDeck().clone();
	}

    public int drawMysteryCard() {
        return this.gameBoard.drawMysteryCard();
    }

    public void addCurrentUserListener(ICurrentUserListener currentUserListener){
        gameBoard.getAuth().addCurrentUserListener(currentUserListener);
    }

    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn){
        return gameBoard.makeExperiment(ingredientName1, ingredientName2, testOn);
    }

    public void toggleDeductionTable(String ingredient, int coordinate){
        gameBoard.getAuth().getCurrentPlayer().deductionBoard.toggleDeductionTable(ingredient, coordinate);
    }

    public int[][] getDeductionTable() {
        return gameBoard.getAuth().getCurrentPlayer().deductionBoard.getDeductionTable();
    }

    public HashMap<String[], DeductionToken> getDeductionTokens() {
        return gameBoard.getAuth().getCurrentPlayer().deductionBoard.getDeductionTokens();
    }

    public ArrayList<Player> calculateWinner(){
        return this.gameBoard.getAuth().calculateWinner();
    }

}
