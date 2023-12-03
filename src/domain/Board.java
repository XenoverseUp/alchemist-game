package domain;

public class Board {
	
	private Auth auth;
	public IngredientCardDeck ingredientCardDeck;
	public ArtifactCardDeck artifactCardDeck;
	
	public Board(Auth auth) {
		
		this.auth = auth;
		this.ingredientCardDeck = new IngredientCardDeck();
		this.artifactCardDeck = new ArtifactCardDeck();
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
	

	
	
	
	
	
	
	
	

}
