package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import enums.Avatar;
import enums.GamePhase;
import error.NotEnoughActionsException;
import error.ServerSideException;
import interfaces.IGameRegister;
import net.Client;

public class TheAlchemistGameOnline implements IGameRegister {
    private Client client;
    private Board staticBoard;

    public TheAlchemistGameOnline(Client client) {
        this.client = client;
    }

    public int getId() {
        if (client != null) return client.getId();
        return 0;
    }

    public boolean isHost() {
        return getId() == 0;
    }

    @Override
    public int createUser(String name, Avatar avatar) {
        return client.createUser(client.getId(), name, avatar);
    }

    @Override
    public Map<String, String> getPlayerNames() {
        return client.getPlayerNames();
    }

    @Override
    public Avatar getPlayerAvatar(int id) {
        return client.getAvatar(id);
    }

    @Override
    public int initializeGame() {
        try {
            client.startGame();
        } catch (ServerSideException e) {
            return 1;
        }

        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<ArtifactCard> getArtifactCardDeck() {
		return (ArrayList<ArtifactCard>)this.staticBoard.artifactCardDeck.getArtifactCardDeck().clone();
	}

    @Override
    public String getCurrentPlayerName() {
        return client.getCurrentUser(true).get("name");
    }
    
    public Map<String, String> getCurrentUser(boolean cached) {
        return client.getCurrentUser(cached);
    }
    
    public Map<String, String> getCurrentUser() {
        return client.getCurrentUser();
    }


    @Override
    public GamePhase getPhase() {
        return client.getPhase(true);
    }

    @Override
    public void toggleCurrentUser() {
        try {
			client.toggleCurrentUser();
		} catch (ServerSideException e) {
			e.printStackTrace();
		}
        revalidateCache();
    }

    public void revalidateCache() {
        client.getCache().revalidateAll();
    }

    @Override
    public void forageIngredient() throws NotEnoughActionsException   {
        client.forageIngredient();
        revalidateCache();
    }

    @Override
    public int drawMysteryCard() throws NotEnoughActionsException {
        int result = client.drawMysteryCard();
        revalidateCache();
        return result;
    }


    @Override
    public int buyArtifact(String name) throws NotEnoughActionsException {
        int result = client.buyArtifact(name);
        revalidateCache();
        return result;
    }

    @Override
    public List<String> getCurrentPlayerArtifacts() {
        return client.getCurrentPlayerArtifacts();
    }
    
    @Override
    public List<String> getCurrentPlayerIngredients() {
        return client.getCurrentPlayerIngredients();
    }
    
    @Override
    public void transmuteIngredient(String name) throws NotEnoughActionsException {
        client.transmuteIngredient(name);
        revalidateCache();
    }

    @Override
    public void discardArtifact(String name) throws NotEnoughActionsException {
        client.discardArtifact(name);
        revalidateCache();
    }

    public boolean isYourTurn() {
        return Integer.parseInt(this.getCurrentUser(true).get("id")) == client.getId();
    }

    @Override
    public int getCurrentPlayerActions() {
       return Integer.parseInt(this.getCurrentUser(true).get("left-actions"));
    }

    @Override
    public int getCurrentPlayerGold() {
        return Integer.parseInt(this.getCurrentUser(true).get("gold"));
    }

    @Override
    public int getCurrentPlayerReputation() {
        return Integer.parseInt(this.getCurrentUser(true).get("reputation"));
    }

    @Override
    public int getCurrentPlayerSickness() {
        return Integer.parseInt(this.getCurrentUser(true).get("sickness"));
    }
}
