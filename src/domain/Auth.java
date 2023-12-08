package domain;

import java.util.ArrayList;
import enums.Avatar;
import interfaces.ICurrentUserListener;

public class Auth {
    public ArrayList<Player> players = new ArrayList<>(2);
    public int currentUser = 0;
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
        
        System.out.println("It's " + players.get(currentUser).name + "'s turn.");
    }

    public void addGoldToCurrentUser(int amount) {
    	players.get(currentUser).inventory.addGold(amount);
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentUser);
    }
    
    public void removeGoldFromCurrentUser(int amount) {
    	players.get(currentUser).inventory.removeGold(amount);
    }
    
    
    public void addIngredientCardToCurrentPlayer(IngredientCard ingredient){
    	players.get(currentUser).inventory.addIngredientCard(ingredient);
    }
    
    public void addArtifactCardToCurrentPlayer(ArtifactCard artifact){
    	players.get(currentUser).inventory.addArtifactCard(artifact);
    }
    
    public IngredientCard getIngredientCardFromCurrentPlayer(int ingredientId) {
    	return players.get(currentUser).inventory.getIngredient(ingredientId);
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
