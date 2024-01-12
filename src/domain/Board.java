package domain;
import enums.GamePhase;
import enums.Potion;
import error.NotEnoughActionsException;
import error.WrongGameRoundException;

public class Board {
	private Auth auth;
	public IngredientCardDeck ingredientCardDeck;
	public ArtifactCardDeck artifactCardDeck;
	protected GamePhase phase;
	protected int numberOfTurns;
	
	public Board(Auth auth) {
		this.auth = auth;
		this.ingredientCardDeck = new IngredientCardDeck();
		this.artifactCardDeck = new ArtifactCardDeck();
		this.phase = GamePhase.FirstRound;
		this.numberOfTurns = 0;
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
		this.numberOfTurns += 1;
		updatePhase();
	}
	
	public void forageIngredient() throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		IngredientCard icard = this.ingredientCardDeck.drawCard();
		auth.addIngredientCardToCurrentPlayer(icard);
		auth.decreaseLeftActionsOfCurrentPlayer();
	}
	
	public void transmuteIngredient(String name) throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		IngredientCard iCard = auth.getIngredientCardFromCurrentPlayer(name);
		this.auth.addGoldToCurrentUser(1);
		this.ingredientCardDeck.addCard(iCard);
		this.ingredientCardDeck.shuffle();
		auth.decreaseLeftActionsOfCurrentPlayer();
	}
	
	public int buyArtifact(String name) throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		ArtifactCard card = this.artifactCardDeck.get(name);
		if (this.auth.getCurrentPlayer().inventory.getGold() >= card.getPrice()) {
			this.auth.addArtifactCardToCurrentPlayer(card);
			this.auth.removeGoldFromCurrentUser(card.getPrice());
			auth.decreaseLeftActionsOfCurrentPlayer();
			return 0;
		} else return 1;
	}


	public int drawMysteryCard() throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		if (this.auth.getCurrentPlayer().inventory.getGold() < 5) return 1; 
		ArtifactCard card = this.artifactCardDeck.drawMysteryCard();
		this.auth.getCurrentPlayer().inventory.spendGold(5);
		this.auth.getCurrentPlayer().inventory.addArtifactCard(card);
		auth.decreaseLeftActionsOfCurrentPlayer();
		return 0;
	}

	public void discardArtifact(String name) throws NotEnoughActionsException{
		auth.checkLeftActionsOfCurrentPlayer();
		auth.getCurrentPlayer().inventory.discardArtifactCard(name);
		auth.decreaseLeftActionsOfCurrentPlayer();
	}
	
	public Auth getAuth() {
		return auth;
	}

	public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) throws WrongGameRoundException, NotEnoughActionsException, Exception {
		if(testOn.equals("sell")){
			if (this.phase == GamePhase.FirstRound){
				throw new WrongGameRoundException();
			}
		}
		auth.checkLeftActionsOfCurrentPlayer();
		
		if (testOn.equals("sell") && auth.getCurrentPlayer().inventory.getGold() < 2){
			throw new Exception("enough-gold-sell");
		}
		else if (testOn.equals("student") && auth.getCurrentPlayer().inventory.getGold() < 1){
			throw new Exception("enough-gold-student");
		}

		IngredientCard ingredient1 = auth.getIngredientCardFromCurrentPlayer(ingredientName1);
	    IngredientCard ingredient2 = auth.getIngredientCardFromCurrentPlayer(ingredientName2);

		Potion potion = PotionBrewingArea.combine(ingredient1, ingredient2);

		auth.getCurrentPlayer().deductionBoard.addExperimentResult(ingredientName1, ingredientName2, potion);
		auth.getCurrentPlayer().playerBoard.addDiscoveredPotion(potion);

		if(testOn.equals("sell")){
			auth.getCurrentPlayer().sell(potion);
		}
		else{
			try{
				auth.getCurrentPlayer().use(potion, testOn);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		auth.decreaseLeftActionsOfCurrentPlayer();
		return potion;
	}


	public void updatePhase(){
		if (numberOfTurns == 3 * auth.getNumOfPlayers()){
			this.phase = GamePhase.SecondRound;
		}
		else if(numberOfTurns == 6 * auth.getNumOfPlayers()){
			this.phase = GamePhase.FinalRound;
		}
		else if(numberOfTurns == 9 * auth.getNumOfPlayers()){
			this.phase = GamePhase.FinalScoring;
		}
	}

	public int getCurrentLeftActions(){
		return auth.getLeftActionsOfCurrentPlayer();
	}

	public GamePhase getPhase(){
		return this.phase;
	}
}



