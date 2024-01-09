package domain;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import enums.ApplicationType;
import enums.Avatar;
import enums.DeductionToken;
import enums.GamePhase;
import enums.Potion;
import error.HostDoesNotExistsException;
import interfaces.IBroadcastListener;
import error.NotEnoughActionsException;
import error.ServerSideException;
import error.WrongGameRoundException;
import interfaces.ICurrentUserListener;
import net.Client;
import net.Server;
import net.util.JON;

public class TheAlchemistGame {
    private Auth auth;
    private Board gameBoard;
    private ApplicationType applicationType = ApplicationType.Local;
    private ServerSocket serverSocket = null;
    private Client client = null;
    public OnlineRegister online;

    public TheAlchemistGame() {
    	auth = new Auth();
        gameBoard = new Board(auth);
    }

    public int createUser(String userName, Avatar a) {
        return auth.createUser(userName, a);
    }
   
    public int createUser(int id, String name, Avatar avatar) {
        return auth.createUser(id, name, avatar);
    }
  
    public String getPlayerName(int id) {
        return this.auth.players.get(id).name;
    }

    public void toggleCurrentUser() {
        gameBoard.toggleCurrentUser();
    }

    public Player getCurrentUser() {
        return auth.getCurrentPlayer();
    }

    public void setApplicationType(ApplicationType applicationType) {
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

    public void discardArtifact(String name) throws NotEnoughActionsException {
        gameBoard.discardArtifact(name);
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

    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) throws WrongGameRoundException, NotEnoughActionsException, Exception {
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

    public GamePhase getPhase() {
        return gameBoard.getPhase();
    }
  

    // NEW

    public boolean isOnline() {
        return this.applicationType == ApplicationType.Online;
    }

    public HashMap<String, String> getPlayers() {
        HashMap<String, String> players = new HashMap<String, String>();
        this.auth.players.forEach(player -> players.put(String.valueOf(player.id), player.name));
        return players;
    }

    public Avatar getPlayerAvatar(int id) {
        return this.auth.getPlayerAvatar(id);
    }

    /** NETWORKING */

    public ApplicationType getApplicationType() {
        return applicationType;
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
            client = new Client(port);
            client.listen();
            setApplicationType(ApplicationType.Online);
            online = new OnlineRegister();
        } catch (HostDoesNotExistsException e) {
            return 1;
        }

        return 0;
    }

    public int connectToServer(String host, int port) {
        try {
            client = new Client(host, port);
            client.listen();
            setApplicationType(ApplicationType.Online);
            online = new OnlineRegister();
        } catch (HostDoesNotExistsException e) {
            return 1;
        }

        return 0;
    }

    public void addBroadcastListener(IBroadcastListener component) {
        if (this.applicationType == ApplicationType.Online && client != null)
            client.addBroadcastListener(component);
    }

    public void removeBroadcastListener(IBroadcastListener component) {
        client.removeBroadcastListener(component);
    }


    public class OnlineRegister {

        public int getId() {
            if (client != null) return client.getId();
            return 0;
        }

        public boolean isHost() {
            return getId() == 0;
        }

        public int createUser(int id, String name, Avatar avatar) {
            return client.createUser(id, name, avatar);
        }

        public Map<String, String> getPlayerNames() {
            return client.getPlayerNames();
        }

        public Avatar getAvatar(int id) {
            return client.getAvatar(id);
        }

        public void startGame() throws ServerSideException {
            client.startGame();
        }
        
        public Map<String, String> getCurrentUser(boolean cached) {
            return client.getCurrentUser(cached);
        }
       
        public Map<String, String> getCurrentUser() {
            return client.getCurrentUser();
        }

        public GamePhase getPhase(boolean cached) {
            return client.getPhase(cached);
        }

        public GamePhase getPhase() {
            return client.getPhase();
        }

        public void toggleCurrentUser() throws ServerSideException {
            client.toggleCurrentUser();
        }

        public void revalidateCache() {
            client.getCache().revalidateAll();
        }

    }

}
