package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import enums.Avatar;
import enums.DeductionToken;
import enums.GamePhase;
import enums.Potion;
import error.NotEnoughActionsException;
import error.WrongGameRoundException;
import interfaces.IGameRegister;

public class TheAlchemistGame implements IGameRegister {
    private Auth auth;
    private Board gameBoard;

    public TheAlchemistGame() {
    	auth = new Auth();
        gameBoard = new Board(auth);
    }

    @Override
    public int createUser(String userName, Avatar a) {
        return auth.createUser(userName, a);
    }
   
    public int createUser(int id, String name, Avatar avatar) {
        return auth.createUser(id, name, avatar);
    }
  
    public String getPlayerName(int id) {
        return this.auth.players.get(id).name;
    }

    @Override
    public String getCurrentPlayerName() {
        return auth.getCurrentPlayer().name;
    }

    @Override
    public List<String> getCurrentPlayerArtifacts() {
        return auth.getCurrentPlayer().inventory.getArtifactCards().stream().map(c -> c.getName()).toList();
    }

    @Override
    public List<String> getCurrentPlayerIngredients() {
        return auth.getCurrentPlayer().inventory.getIngredientCards().stream().map(c -> c.getName()).toList();
    }

    @Override
    public Map<String, String> getPlayerNames() {
        Map<String, String> names = new HashMap<String, String>();

        this.auth.players.forEach(p -> names.put(String.valueOf(p.id), p.name));

        return names;
    }

    @Override
    public void toggleCurrentUser() {
        gameBoard.toggleCurrentUser();
    }

    @Override
    public int initializeGame() {
        gameBoard.ingredientCardDeck.shuffle();
        gameBoard.dealCards();
        gameBoard.artifactCardDeck.shuffle();
        gameBoard.dealGolds();

        return 0;
    }

    @Override
    public void forageIngredient() throws NotEnoughActionsException {
        gameBoard.forageIngredient();
    }

    @Override
    public void transmuteIngredient(String ingredientName) throws NotEnoughActionsException {
        gameBoard.transmuteIngredient(ingredientName);
    }

    @Override
    public int buyArtifact(String name) throws NotEnoughActionsException {
        return gameBoard.buyArtifact(name);
    }

    @Override
    public void discardArtifact(String name) throws NotEnoughActionsException {
        gameBoard.discardArtifact(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<ArtifactCard> getArtifactCardDeck() {
		return (ArrayList<ArtifactCard>)this.gameBoard.artifactCardDeck.getArtifactCardDeck().clone();
	}

    @Override
    public int drawMysteryCard() throws NotEnoughActionsException {
        return this.gameBoard.drawMysteryCard();
    }

    public Potion makeExperiment(
        String ingredientName1, 
        String ingredientName2, 
        String testOn
    ) throws WrongGameRoundException, NotEnoughActionsException, Exception {
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

    @Override
    public GamePhase getPhase() {
        return gameBoard.getPhase();
    }
  
    public Player getCurrentPlayer() {
        return this.auth.getCurrentPlayer();
    }


    // NEW

    public HashMap<String, String> getPlayers() {
        HashMap<String, String> players = new HashMap<String, String>();
        this.auth.players.forEach(player -> players.put(String.valueOf(player.id), player.name));
        return players;
    }

    public Avatar getPlayerAvatar(int id) {
        return this.auth.getPlayerAvatar(id);
    }

    @Override
    public int getCurrentPlayerActions() {
        return this.getCurrentPlayer().leftActions;
    }

    @Override
    public int getCurrentPlayerGold() {
        return this.getCurrentPlayer().inventory.getGold();
    }

    @Override
    public int getCurrentPlayerReputation() {
        return this.getCurrentPlayer().getReputation();
    }

    @Override
    public int getCurrentPlayerSickness() {
        return this.getCurrentPlayer().getSickness();
    }
}
