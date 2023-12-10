package domain;

import enums.Avatar;
import interfaces.ICurrentUserListener;

public class TheAlchemistGame {
    private Auth auth;
    private Board gameBoard;

    public TheAlchemistGame() {
        auth = new Auth();
        gameBoard = new Board(auth);
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
        return auth.players.get(auth.currentUser);
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

    public void transmuteIngredient(int ingredientId) {
        gameBoard.transmuteIngredient(ingredientId);
    }

    public void buyArtifact() {
        gameBoard.buyArtifact();
    }

    public IngredientCard drawIngredientCard() {
        IngredientCard card = gameBoard.ingredientCardDeck.drawCard();
        auth.players.get(auth.currentUser).inventory.addIngredientCard(card);

        return card;
    }

    public ArtifactCard drawArtifactCard() {
        ArtifactCard card = gameBoard.artifactCardDeck.drawCard();
        auth.players.get(auth.currentUser).inventory.addArtifactCard(card);

        return card;
    }

    public int getPriceOfNextArtifact() {
        return gameBoard.artifactCardDeck.getPriceOfNextArtifact();
    }

    public void addCurrentUserListener(ICurrentUserListener currentUserListener) {

        gameBoard.getAuth().addCurrentUserListener(currentUserListener);
    }

    public Board getBoard() {
        return gameBoard;
    }

    public void publishATheory(String bookName, int markerID) {
        boolean success = gameBoard.publishATheory(bookName, markerID);
        if (success) {
            System.out.println("Success!");
        } else {
            System.out.println("Failed!");
        }
    }

}
