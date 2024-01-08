package domain;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import enums.ApplicationType;
import enums.Avatar;
import enums.DeductionToken;
import enums.GamePhase;
import enums.Potion;
import error.HostDoesNotExistsException;
import error.NotEnoughActionsException;
import interfaces.ICurrentUserListener;
import net.ClientSideConnection;
import net.Server;

public class TheAlchemistGame {
    private Auth auth;
    private Board gameBoard;
    private ApplicationType applicationType = ApplicationType.Host;
    private ServerSocket serverSocket = null;
    private ClientSideConnection csc = null;

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

    public void setApplicationType(ApplicationType applicationType, int port) {
        this.applicationType = applicationType;
    }

    public void initializeGame() {
        gameBoard.ingredientCardDeck.shuffle();
        gameBoard.dealCards();
        gameBoard.artifactCardDeck.shuffle();
        gameBoard.dealGolds();
    }

    public void forageIngredient() throws NotEnoughActionsException   {
        gameBoard.forageIngredient();
    }

    public void transmuteIngredient(String ingredientName) throws NotEnoughActionsException {
        gameBoard.transmuteIngredient(ingredientName);
    }

    public int buyArtifact(String name) throws NotEnoughActionsException {
        return gameBoard.buyArtifact(name);
    }

    public void discardArtifact(String name) {
        gameBoard.getAuth().getCurrentPlayer().inventory.discardArtifactCard(name);
    }

    public ArrayList<ArtifactCard> getArtifactCardDeck(){
		return (ArrayList<ArtifactCard>)this.gameBoard.artifactCardDeck.getArtifactCardDeck().clone();
	}

    public int drawMysteryCard() throws NotEnoughActionsException {
        return this.gameBoard.drawMysteryCard();
    }

    public void addCurrentUserListener(ICurrentUserListener currentUserListener){
        gameBoard.getAuth().addCurrentUserListener(currentUserListener);
    }

    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) throws Exception {
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

    public GamePhase getPhase(){
        return gameBoard.getPhase();
    }

    public int getCurrentLeftActions(){
        return gameBoard.getCurrentLeftActions();
    }

    public int createServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            Server server = new Server(serverSocket, this);
            server.start();
            this.connectToServer(port);
        } catch (BindException e) {
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    public int connectToServer(int port) {
        try {
            csc = new ClientSideConnection(port);
            csc.listen();
        } catch (HostDoesNotExistsException e) {
            return 1;
        }

        return 0;
    }

    public int getId() {
        if (csc != null) return csc.getId();
        return 0;
    }


}
