package domain;

import java.util.ArrayList;
import enums.Avatar;
import error.HostDoesNotExistsException;
import error.NotEnoughActionsException;
import interfaces.ICurrentUserListener;

public class Auth {
    public ArrayList<Player> players = new ArrayList<>(2);
    private int currentUser = 0;
    private ArrayList<ICurrentUserListener> currentUserListeners = new ArrayList<>();

    /**
     * @param name - Name of the alchemist.
     * @param avatar - Avatar of the alchemist.
     * @returns {int} Error code.
     * 
     * Error Codes:
     *  0 - Everything worked as expected.
     *  1 - Name is already taken.
     *  2 - Name is empty.
     *  3 - Avatar is already taken.
     */
    public int createUser(String name, Avatar avatar) {
        if (name.equals("") || name == null) return 2;

        for (Player p : this.players) {
            if (p.name.equals(name)) return 1;
            if (p.avatar == avatar) return 3;
        }

        int id = this.players.size();
        Player player = new Player(id, name, avatar);

        this.players.add(player);

        return 0;
    }

    public void toggleCurrentUser() {
        this.currentUser += 1;
        if (this.currentUser == players.size()) this.currentUser = 0;
        getCurrentPlayer().calculateTotalActions();
        publishCurrentUserChange();
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

    public void decreaseLeftActionsOfCurrentPlayer() throws NotEnoughActionsException {
        getCurrentPlayer().decreaseLeftActions();
    }

    public void calculateTotalActionsNewCurrentPlayer(){
        getCurrentPlayer().calculateTotalActions();
    }

    public int getLeftActionsOfCurrentPlayer(){
        return getCurrentPlayer().leftActions;
    }

    public int getNumOfPlayers(){
        return players.size();
    }

    // Method for observer pattern
    public void addCurrentUserListener(ICurrentUserListener currentUserListener){
		currentUserListeners.add(currentUserListener);
	}

    // Method for observer pattern
	public void publishCurrentUserChange(){
		for(ICurrentUserListener listener : currentUserListeners) listener.onCurrentUserChange();
	}

    public ArrayList<Player> calculateWinner(){
        ArrayList<Player> winners = new ArrayList<>();
        Player candidateWinner = null;
        int[] candidateScore = null;
        for (Player player: this.players){
            if(candidateWinner == null || candidateScore == null){
                candidateWinner = player;
                candidateScore = player.calculateFinalScore();
            } 
            else{
                int [] score = player.calculateFinalScore();
                if ((score[0] > candidateScore[0]) || (score[0] == candidateScore[0] && score[1] > score[1] )){
                    candidateWinner = player;
                    candidateScore = score;
                    if (winners.size() > 0){
                        winners.clear();
                    }
                }
                else if (score[0] == candidateScore[0] && score[1] == candidateScore[1]){
                    winners.add(player);
                }
                
            }
        }
        winners.add(candidateWinner);
        return winners;
    }
}
