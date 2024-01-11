package interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.ArtifactCard;
import enums.Avatar;
import enums.GamePhase;
import error.NotEnoughActionsException;

public interface IGameRegister {
    public int createUser(String userName, Avatar a);
    public int initializeGame();
    public Map<String, String> getPlayerNames();
    public Avatar getPlayerAvatar(int id);
    public void forageIngredient() throws NotEnoughActionsException;
    public int drawMysteryCard() throws NotEnoughActionsException;
    public ArrayList<ArtifactCard> getArtifactCardDeck();
    public void transmuteIngredient(String ingredientName) throws NotEnoughActionsException; 
    public int buyArtifact(String name) throws NotEnoughActionsException;
    public void discardArtifact(String name) throws NotEnoughActionsException;
    public String getCurrentPlayerName();
    public int getCurrentPlayerActions();
    public int getCurrentPlayerGold();
    public int getCurrentPlayerReputation();
    public int getCurrentPlayerSickness();
    public List<String> getCurrentPlayerArtifacts();
    public List<String> getCurrentPlayerIngredients();
    public GamePhase getPhase();
    public void toggleCurrentUser();
    public int[][] getDeductionTable();
}