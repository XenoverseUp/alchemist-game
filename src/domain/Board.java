package domain;
import enums.Potion;

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
		for (Player p: auth.players) 
    		p.inventory.addGold(10);
	}
	
	public void toggleCurrentUser() {
		auth.toggleCurrentUser();
	}
	
	public void forageIngredient() {
		IngredientCard icard = this.ingredientCardDeck.drawCard();
		auth.addIngredientCardToCurrentPlayer(icard);
	}
	
	public void transmuteIngredient(String name) {
		IngredientCard iCard = auth.getIngredientCardFromCurrentPlayer(name);
		this.auth.addGoldToCurrentUser(1);
		this.ingredientCardDeck.addCard(iCard);
		this.ingredientCardDeck.shuffle();
	}
	
	public int buyArtifact(String name) {
		ArtifactCard card = this.artifactCardDeck.get(name);
		if (this.auth.getCurrentPlayer().inventory.getGold() >= card.getPrice()) {
			this.auth.addArtifactCardToCurrentPlayer(card);
			this.auth.removeGoldFromCurrentUser(card.getPrice());
			return 0;
		} else return 1;
	}

	public int drawMysteryCard() {
		if (this.auth.getCurrentPlayer().inventory.getGold() < 4) return 1; 

		ArtifactCard card = this.artifactCardDeck.drawMysteryCard();
		this.auth.getCurrentPlayer().inventory.spendGold(5);
		this.auth.getCurrentPlayer().inventory.addArtifactCard(card);
		
		return 0;
	}
	
	public Auth getAuth() {
		return auth;
	}

	public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) {
		IngredientCard ingredient1 = auth.getIngredientCardFromCurrentPlayer(ingredientName1);
	    IngredientCard ingredient2 = auth.getIngredientCardFromCurrentPlayer(ingredientName2);

		Potion potion = PotionBrewingArea.combine(ingredient1, ingredient2);
		
		try {
			auth.getCurrentPlayer().deductionBoard.addExperimentResult(ingredientName1, ingredientName2, potion);
			auth.getCurrentPlayer().use(potion, testOn);
		} catch (Exception e) {
			System.out.println(e);
		}

		return potion;
	}

}
