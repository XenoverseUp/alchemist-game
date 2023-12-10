package domain;

import java.util.HashSet;

public class Board {
	
	private Auth auth;
	public IngredientCardDeck ingredientCardDeck;
	public ArtifactCardDeck artifactCardDeck;
	private HashSet<Integer> publishedTheories;
	
	public Board(Auth auth) {
		this.auth = auth;
		this.ingredientCardDeck = new IngredientCardDeck();
		this.artifactCardDeck = new ArtifactCardDeck();
		this.publishedTheories = new HashSet<>();
	}
	
	public void dealCards() {
		for (Player player: auth.players) {
			for (int i = 0; i < 2; i++) {
				IngredientCard iCard = this.ingredientCardDeck.drawCard();
				player.inventory.addIngredientCard(iCard);
			}
		}
	}
	
	
	public void dealGolds() {
		for (Player p: auth.players) {
    		p.inventory.addGold(3);
    	}
	}
	
	public void toggleCurrentUser() {
		auth.toggleCurrentUser();
	}

	
	public void forageIngredient() {
		IngredientCard icard = this.ingredientCardDeck.drawCard();
		auth.addIngredientCardToCurrentPlayer(icard);
	}
	
	public void transmuteIngredient(int ingredientId) {
		IngredientCard iCard = auth.getIngredientCardFromCurrentPlayer(ingredientId);
		this.auth.addGoldToCurrentUser(1);
		this.ingredientCardDeck.addCard(iCard);
			
	}
	
	public void buyArtifact() {
		ArtifactCard aCard = this.artifactCardDeck.drawCard();
		this.auth.addArtifactCardToCurrentPlayer(aCard);
		this.auth.removeGoldFromCurrentUser(aCard.getPrice());
	}

	
	public Auth getAuth() {
		return auth;
	}

	public boolean publishATheory(String bookNamei ,int markerID) {
		Player currentPlayer = auth.getCurrentPlayer();

		//if the theory is already published
		if (publishedTheories.contains(markerID)) {
			System.out.println("Theory for this marker is already published!");
			return false;
		}
		//if the current player has enough gold
		if (currentPlayer.inventory.getGold() < 1) {
			System.out.println("Not enough gold to publish!");
			return false;
		}

		currentPlayer.inventory.removeGold(1); //deduct 1 gold 
		publishedTheories.add(markerID); //publish the theory
		currentPlayer.increaseReputation(1); //increase the reputation

		System.out.println("Theory succesfully published by " + currentPlayer.name + ".");


		return true;
	}


	
	
	
	
	
	
	
	

}
