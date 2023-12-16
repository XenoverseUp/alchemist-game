package domain;

import enums.Avatar;
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
        getCurrentUser().leftActions = 2 + getCurrentUser().extraActions;
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

    public void buyArtifact() {
        gameBoard.buyArtifact();
    }

    public IngredientCard drawIngredientCard() {
        IngredientCard card = gameBoard.ingredientCardDeck.drawCard();
        auth.getCurrentPlayer().inventory.addIngredientCard(card);

        return card;
    }

    public ArtifactCard drawArtifactCard() {
        ArtifactCard card = gameBoard.artifactCardDeck.drawCard();
        auth.getCurrentPlayer().inventory.addArtifactCard(card);

        return card;
    }

    public int getPriceOfNextArtifact(){
        return gameBoard.artifactCardDeck.getPriceOfNextArtifact();
    }

    public void addCurrentUserListener(ICurrentUserListener currentUserListener){
        gameBoard.getAuth().addCurrentUserListener(currentUserListener);
    }

    public void publishATheory(String bookName, int markerID) {
        boolean success = gameBoard.publishATheory(bookName, markerID);
        if (success) {
            System.out.println("Success!");
        } else {
            System.out.println("Failed!");
        }
    }

    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn){
        return gameBoard.makeExperiment(ingredientName1, ingredientName2, testOn);
    }

}
