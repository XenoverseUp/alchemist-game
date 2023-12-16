package domain;

import java.util.ArrayList;
import enums.Avatar;
import interfaces.ICurrentUserListener;

public class Auth {
    public ArrayList<Player> players = new ArrayList<>(2);
    private int currentUser = 0;
    private ArrayList<ICurrentUserListener> currentUserListeners = new ArrayList<>();

    public int createUser(String name, Avatar avatar) {
        if (name.equals("") || name == null) return 2;

        for (Player p : this.players) {
            if (p.name.equals(name))
                return 1;
        }

        Player player = new Player(this.players.size(), name, avatar);
        this.players.add(player);

        return 0;
    }

    public void toggleCurrentUser() {
        this.currentUser += 1;
        if (this.currentUser == players.size()) this.currentUser = 0;
        publishCurrentUserChange();
        getCurrentPlayer().leftActions = 2 + getCurrentPlayer().extraActions;
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentUser);
    }

    public void addGoldToCurrentUser(int amount) {
    	getCurrentPlayer().inventory.addGold(amount);
    }
    
    public void removeGoldFromCurrentUser(int amount) {
    	getCurrentPlayer().inventory.spendGold(amount);
    }
    
    public void addIngredientCardToCurrentPlayer(IngredientCard ingredient) {
    	getCurrentPlayer().inventory.addIngredientCard(ingredient);
    }
    
    public void addArtifactCardToCurrentPlayer(ArtifactCard artifact){
    	getCurrentPlayer().inventory.addArtifactCard(artifact);
    }
    
    public IngredientCard getIngredientCardFromCurrentPlayer(String name) {
    	return getCurrentPlayer().inventory.getIngredient(name);
    }

    // Method for observer pattern
    public void addCurrentUserListener(ICurrentUserListener currentUserListener){
		currentUserListeners.add(currentUserListener);
	}

    // Method for observer pattern
	public void publishCurrentUserChange(){
		for(ICurrentUserListener listener : currentUserListeners) listener.onCurrentUserChange();
	}
}
