package interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import domain.ArtifactCard;
import domain.Player;
import enums.ApplicationType;
import enums.Avatar;
import enums.DeductionToken;
import enums.Potion;

public interface IGameRegister {
    public int createUser(String userName, Avatar a) ;
    public void toggleCurrentUser() ;
    public Player getCurrentUser() ;
    public void setApplicationType(ApplicationType applicationType, int port) ;
    public void initializeConnection(int port) ;
    public void initializeGame() ;
    public void forageIngredient() ;
    public void transmuteIngredient(String ingredientName) ;
    public int buyArtifact(String name) ;
    public void discardArtifact(String name) ;
    public ArrayList<ArtifactCard> getArtifactCardDeck();
    public int drawMysteryCard() ;
    public void addCurrentUserListener(ICurrentUserListener currentUserListener);
    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) throws Exception;
    public void toggleDeductionTable(String ingredient, int coordinate);
    public int[][] getDeductionTable() ;
    public HashMap<String[], DeductionToken> getDeductionTokens() ;
    public ArrayList<Player> calculateWinner();
}
