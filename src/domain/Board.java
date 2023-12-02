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

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
	
	
	
	
	
	
	

}
