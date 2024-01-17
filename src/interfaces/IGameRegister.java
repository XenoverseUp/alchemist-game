package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.ArtifactCard;
import enums.Avatar;
import enums.DeductionToken;
import enums.GamePhase;
import error.NotEnoughActionsException;
import error.ServerSideException;

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

    public Avatar getCurrentPlayerAvatar();

    public int getCurrentPlayerGold();

    public int getCurrentPlayerReputation();

    public int getCurrentPlayerSickness();

    public List<String> getCurrentPlayerArtifacts();

    public List<String> getCurrentPlayerIngredients();

    public GamePhase getPhase();

    public void toggleCurrentUser();

    public int[][] getDeductionTable();

    public void toggleDeductionTable(String name, int tableIndex);

    public HashMap<String[], DeductionToken> getDeductionTokens();

    public void setCard(int i);

    public void setMarker(int i);

    public void publishTheory() throws NotEnoughActionsException;

    public void debunkTheory() throws NotEnoughActionsException;

    public int getMarkerID(int id);

    public void activateArtifact(String name) throws ServerSideException;

    public void removeArtifactCardAfterUsing(String string) throws ServerSideException;

    public void paralyseEveryone() throws ServerSideException;

    public boolean hasArtifactCard(String string) throws ServerSideException;

    public void swapAfterIndex(int first, int second, int third);

}
