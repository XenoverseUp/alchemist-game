package domain;

import enums.GamePhase;

import enums.Potion;
import error.NotEnoughActionsException;
import error.WrongGameRoundException;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Board {

	private Auth auth;
	public IngredientCardDeck ingredientCardDeck;
	public ArtifactCardDeck artifactCardDeck;

	public PublicationCardDeck publicationCardDeck;
	public AlchemyMarkerDeck alchemyMarkerDeck;

	protected GamePhase phase;
	protected int numberOfTurns;

	

	public Board(Auth auth) {
		this.auth = auth;
		this.alchemyMarkerDeck = new AlchemyMarkerDeck();
		this.ingredientCardDeck = new IngredientCardDeck();
		this.artifactCardDeck = new ArtifactCardDeck();

		this.publicationCardDeck = new PublicationCardDeck(this.ingredientCardDeck.getDeck());
		

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
		auth.decreaseLeftActionsOfCurrentPlayer();
		IngredientCard icard = this.ingredientCardDeck.drawCard();
		auth.addIngredientCardToCurrentPlayer(icard);
	}
	
	public void transmuteIngredient(String name) throws NotEnoughActionsException {
		auth.decreaseLeftActionsOfCurrentPlayer();

		IngredientCard iCard = auth.getIngredientCardFromCurrentPlayer(name);
		this.auth.addGoldToCurrentUser(1);
		this.ingredientCardDeck.addCard(iCard);
		this.ingredientCardDeck.shuffle();
	}

	
	public int buyArtifact(String name) throws NotEnoughActionsException {
		auth.decreaseLeftActionsOfCurrentPlayer();
		ArtifactCard card = this.artifactCardDeck.get(name);
		if (this.auth.getCurrentPlayer().inventory.getGold() >= card.getPrice()) {
			this.auth.addArtifactCardToCurrentPlayer(card);
			this.auth.removeGoldFromCurrentUser(card.getPrice());
			return 0;
		} else return 1;
	}


	public int drawMysteryCard() throws NotEnoughActionsException {
		auth.decreaseLeftActionsOfCurrentPlayer();
		if (this.auth.getCurrentPlayer().inventory.getGold() < 5) return 1; 
		ArtifactCard card = this.artifactCardDeck.drawMysteryCard();
		this.auth.getCurrentPlayer().inventory.spendGold(5);
		this.auth.getCurrentPlayer().inventory.addArtifactCard(card);
		return 0;
	}

	public void discardArtifact(String name) throws NotEnoughActionsException{
		auth.decreaseLeftActionsOfCurrentPlayer();
		auth.getCurrentPlayer().inventory.discardArtifactCard(name);
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
		auth.decreaseLeftActionsOfCurrentPlayer();
		if (testOn.equals("sell") && auth.getCurrentPlayer().inventory.getGold() < 2){
			throw new Exception("enough-gold-sell");
		}
		else if (testOn.equals("student") && auth.getCurrentPlayer().inventory.getGold() < 1){
			throw new Exception("enough-gold-student");
		}

		IngredientCard ingredient1 = auth.getIngredientCardFromCurrentPlayer(ingredientName1);
		IngredientCard ingredient2 = auth.getIngredientCardFromCurrentPlayer(ingredientName2);

		Potion potion = PotionBrewingArea.combine(ingredient1, ingredient2);


		try {
			auth.getCurrentPlayer().use(potion, testOn);
		} catch (Exception e) {
			System.out.println(e);
		}


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
		return potion;
	}


	public void publishTheory() {
		if (!alchemyMarkerDeck.getChosen().checkAvailability()) {
			if (this.publicationCardDeck.getChosen().getAlchemyMarker() == null) {
				auth.getCurrentPlayer().increaseReputation(1);
				auth.removeGoldFromCurrentUser(1);
				this.publicationCardDeck.getChosen().setAlchemyMarker(alchemyMarkerDeck.getChosen());
				alchemyMarkerDeck.getChosen().associate();
				publicationCardDeck.getChosen().setPlayer(auth.getCurrentPlayer());
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Published Theory Successfully!\n Reputation: +1\n Gold: -1",
							"Success!", JOptionPane.PLAIN_MESSAGE);
				});
			}
		}
	}

	// chooses a publication card, states the published theory is wrong, doesn't
	// need to state the correct theory
	public void debunkTheory() {
		if (this.publicationCardDeck.getChosen().getAlchemyMarker() != null) {
			Molecule m = this.publicationCardDeck.getChosen().getIngredient().getMolecule();
			if (this.publicationCardDeck.getChosen().getAlchemyMarker().getMolecule().equals(m)) {
				auth.getCurrentPlayer().decreaseReputation(1);
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Published Theory Was Correct.\n Reputation: -1",
							"Failed!", JOptionPane.PLAIN_MESSAGE);
				});
			} else {
				this.publicationCardDeck.getChosen().getAlchemyMarker().dissociate();
				AlchemyMarker marker = null;
				auth.getCurrentPlayer().increaseReputation(2);
				this.publicationCardDeck.getChosen().getPlayer().decreaseReputation(2);
				for (int i = 0; i < this.ingredientCardDeck.getDeck().size(); i++) {
					if (this.ingredientCardDeck.getDeck().get(i).getMolecule()
							.equals(this.alchemyMarkerDeck.getMarker(i).getMolecule())) {
						marker = this.alchemyMarkerDeck.getMarker(i);
					}
				}
				this.publicationCardDeck.getChosen().setAlchemyMarker(marker);
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Debunked Theory Successfully!\n Reputation: +2",
							"Success!", JOptionPane.PLAIN_MESSAGE);
				});
			}
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



