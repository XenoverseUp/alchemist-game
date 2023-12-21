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

    public int getPriceOfNextArtifact() {
        return gameBoard.artifactCardDeck.getPriceOfNextArtifact();
    }

    public void addCurrentUserListener(ICurrentUserListener currentUserListener) {
        gameBoard.getAuth().addCurrentUserListener(currentUserListener);
    }

    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) {
        return gameBoard.makeExperiment(ingredientName1, ingredientName2, testOn);
    }

    public void publishTheory() {
        gameBoard.publishTheory();
    }

    public void debunkTheory(int cardID, int markerID) {
        gameBoard.debunkTheory(cardID, markerID);
    }

    public void setMarker(int id) {
        gameBoard.alchemyMarkerDeck.setChosen(id);
    }

    public void setCard(int id) {
        gameBoard.publicationCardDeck.setChosen(id);
    }

    public String getMarkerImagePath(int id) {
        AlchemyMarker marker = gameBoard.publicationCardDeck.getCard(id).getAlchemyMarker();
        if (marker != null) {
            return marker.getImagePath();
        } else {
            return "";
        }

    }
}
