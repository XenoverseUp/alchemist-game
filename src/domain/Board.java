package domain;

import enums.GamePhase;

import enums.Potion;
import error.NotEnoughActionsException;
import error.WrongGameRoundException;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Board {

	private Auth auth;
	public IngredientCardDeck ingredientCardDeck;
	public ArtifactCardDeck artifactCardDeck;

	public PublicationCardDeck publicationCardDeck;
	public AlchemyMarkerDeck alchemyMarkerDeck;

	protected GamePhase phase;
	protected int numberOfTurns;
	String filePath = "gamelog.txt";
	BufferedWriter bufferedWriter;

	public Board(Auth auth) {
		this.auth = auth;
		this.alchemyMarkerDeck = new AlchemyMarkerDeck();
		this.ingredientCardDeck = new IngredientCardDeck();
		this.artifactCardDeck = new ArtifactCardDeck();

		this.publicationCardDeck = new PublicationCardDeck(this.ingredientCardDeck.getDeck());

		this.phase = GamePhase.FirstRound;
		this.numberOfTurns = 0;
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			this.bufferedWriter = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void dealCards() {
		for (Player player : auth.players) {
			for (int i = 0; i < 2; i++) {
				IngredientCard iCard = this.ingredientCardDeck.drawCard();
				player.inventory.addIngredientCard(iCard);
			}
		}
		writetoFile("Cards are dealed.");
	}

	public void dealGolds() {
		for (Player p : auth.players)
			p.inventory.addGold(10);

		writetoFile("Golds are dealed.");
	}

	public void toggleCurrentUser() {
		auth.toggleCurrentUser();
		this.numberOfTurns += 1;
		updatePhase();

		String formattedString = String.format("It's %s's turn.", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
	}

	public void forageIngredient() throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		IngredientCard icard = this.ingredientCardDeck.drawCard();
		auth.addIngredientCardToCurrentPlayer(icard);
		auth.decreaseLeftActionsOfCurrentPlayer();

		String formattedString = String.format("%s foraged an ingredient.", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
	}

	public void transmuteIngredient(String name) throws NotEnoughActionsException {

		auth.checkLeftActionsOfCurrentPlayer();

		IngredientCard iCard = auth.getIngredientCardFromCurrentPlayer(name);
		this.auth.addGoldToCurrentUser(1);
		if (getAuth().getCurrentPlayer().inventory.hasArtifactCard("Trader's Touch").equals(true)) {
			this.auth.addGoldToCurrentUser(1);
		}
		this.ingredientCardDeck.addCard(iCard);
		this.ingredientCardDeck.shuffle();
		auth.decreaseLeftActionsOfCurrentPlayer();

		String formattedString = String.format("%s transmuted an ingredient.", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
	}

	public int buyArtifact(String name) throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		ArtifactCard card = this.artifactCardDeck.get(name);
		if (this.auth.getCurrentPlayer().inventory.getGold() >= card.getPrice()) {
			this.auth.addArtifactCardToCurrentPlayer(card);
			this.auth.removeGoldFromCurrentUser(card.getPrice());
			auth.decreaseLeftActionsOfCurrentPlayer();

			String formattedString = String.format("%s bought an artifact.", auth.getCurrentPlayer().name);
			writetoFile(formattedString);
			return 0;
		} else {

			String formattedString = String.format("%s was too poor to buy an artifact.", auth.getCurrentPlayer().name);
			writetoFile(formattedString);
			return 1;
		}

	}

	public int drawMysteryCard() throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		if (this.auth.getCurrentPlayer().inventory.getGold() < 5)
			return 1;
		ArtifactCard card = this.artifactCardDeck.drawMysteryCard();
		this.auth.getCurrentPlayer().inventory.spendGold(5);
		this.auth.getCurrentPlayer().inventory.addArtifactCard(card);
		auth.decreaseLeftActionsOfCurrentPlayer();
		String formattedString = String.format("%s got a mystery card. Wow!", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
		return 0;
	}

	public void discardArtifact(String name) throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		auth.getCurrentPlayer().inventory.discardArtifactCard(name);
		auth.decreaseLeftActionsOfCurrentPlayer();
		String formattedString = String.format("%s discarded an artifact card.", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
	}

	public Auth getAuth() {
		return auth;
	}

	public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn)
			throws WrongGameRoundException, NotEnoughActionsException, Exception {
		if (testOn.equals("sell")) {
			if (this.phase == GamePhase.FirstRound) {
				throw new WrongGameRoundException();
			}
		}
		auth.checkLeftActionsOfCurrentPlayer();

		if (testOn.equals("sell") && auth.getCurrentPlayer().inventory.getGold() < 2) {
			throw new Exception("enough-gold-sell");
		} else if (testOn.equals("student") && auth.getCurrentPlayer().inventory.getGold() < 1) {
			throw new Exception("enough-gold-student");
		}

		IngredientCard ingredient1 = auth.getIngredientCardFromCurrentPlayer(ingredientName1);
		IngredientCard ingredient2 = auth.getIngredientCardFromCurrentPlayer(ingredientName2);
		if (getAuth().getCurrentPlayer().inventory.hasArtifactCard("Magic Mortar").equals(true)) {
			auth.addIngredientCardToCurrentPlayer(ingredient1);
		}

		Potion potion = PotionBrewingArea.combine(ingredient1, ingredient2);

		try {
			auth.getCurrentPlayer().use(potion, testOn);
		} catch (Exception e) {
			System.out.println(e);
		}

		auth.getCurrentPlayer().deductionBoard.addExperimentResult(ingredientName1, ingredientName2, potion);
		auth.getCurrentPlayer().playerBoard.addDiscoveredPotion(potion);

		if (testOn.equals("sell")) {
			auth.getCurrentPlayer().sell(potion);
		} else {
			try {
				auth.getCurrentPlayer().use(potion, testOn);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		auth.decreaseLeftActionsOfCurrentPlayer();
		String formattedString = String.format("%s made an experiment.", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
		return potion;
	}

	public int publishTheory() throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();

		if (!alchemyMarkerDeck.getChosen().checkAvailability()) {
			if (getAuth().getCurrentPlayer().inventory.getGold() > 0) {

				if (this.publicationCardDeck.getChosen().getAlchemyMarker() == null) {
					auth.getCurrentPlayer().increaseReputation(1);
					if (getAuth().getCurrentPlayer().inventory.hasArtifactCard("Robe of Respect").equals(true)) {
						auth.getCurrentPlayer().increaseReputation(1);
					}

					if (getAuth().getCurrentPlayer().inventory.hasArtifactCard("Printing Press").equals(false)) {
						auth.removeGoldFromCurrentUser(1);
					}

					this.publicationCardDeck.getChosen().setAlchemyMarker(alchemyMarkerDeck.getChosen());
					alchemyMarkerDeck.getChosen().associate();
					publicationCardDeck.getChosen().setPlayer(auth.getCurrentPlayer());

					auth.decreaseLeftActionsOfCurrentPlayer();

					String formattedString = String.format("%s published a theory.", auth.getCurrentPlayer().name);
					writetoFile(formattedString);
					return 1;
				}
			}
		}
		return 0;
	}

	// chooses a publication card, states the published theory is wrong, doesn't
	// need to state the correct theory
	public int debunkTheory() throws NotEnoughActionsException {
		auth.checkLeftActionsOfCurrentPlayer();
		if (!this.publicationCardDeck.getChosen().getPlayer().equals(auth.getCurrentPlayer())) {
			if ((this.publicationCardDeck.getChosen().getAlchemyMarker() != null)) {
				Molecule m = this.publicationCardDeck.getChosen().getIngredient().getMolecule();
				if (this.publicationCardDeck.getChosen().getAlchemyMarker().getMolecule().equals(m)) {
					auth.getCurrentPlayer().decreaseReputation(1);

					String formattedString = String.format("%s tried to debunk a theory but was not successful.",
							auth.getCurrentPlayer().name);
					writetoFile(formattedString);
					auth.decreaseLeftActionsOfCurrentPlayer();
					return 0;
				} else {
					this.publicationCardDeck.getChosen().getAlchemyMarker().dissociate();
					AlchemyMarker marker = null;
					auth.getCurrentPlayer().increaseReputation(2);
					if (getAuth().getCurrentPlayer().inventory.hasArtifactCard("Robe of Respect").equals(true)) {
						auth.getCurrentPlayer().increaseReputation(1);
					}
					if (this.publicationCardDeck.getChosen().getPlayer().inventory.hasArtifactCard("Wisdom Idol")
							.equals(false)) {
						this.publicationCardDeck.getChosen().getPlayer().decreaseReputation(2);
					}
					for (int i = 0; i < this.ingredientCardDeck.getDeck().size(); i++) {
						if (this.ingredientCardDeck.getDeck().get(i).getMolecule()
								.equals(this.alchemyMarkerDeck.getMarker(i).getMolecule())) {
							marker = this.alchemyMarkerDeck.getMarker(i);
						}
					}
					this.publicationCardDeck.getChosen().setAlchemyMarker(marker);

					String formattedString = String.format("%s successfully debunked a theory.",
							auth.getCurrentPlayer().name);
					writetoFile(formattedString);
					auth.decreaseLeftActionsOfCurrentPlayer();
					return 1;
				}
			}
		}
		return 2;
	}

	public void updatePhase() {
		if (numberOfTurns == 3 * auth.getNumOfPlayers()) {
			this.phase = GamePhase.SecondRound;
		} else if (numberOfTurns == 6 * auth.getNumOfPlayers()) {
			this.phase = GamePhase.FinalRound;
		} else if (numberOfTurns == 9 * auth.getNumOfPlayers()) {
			this.phase = GamePhase.FinalScoring;
		}
	}

	public int getCurrentLeftActions() {
		return auth.getLeftActionsOfCurrentPlayer();
	}

	public GamePhase getPhase() {
		return this.phase;

	}

	public void clearPlayers() {
		this.auth.clearPlayers();
		this.phase = GamePhase.FirstRound;
		this.numberOfTurns = 0;
	}

	public void activateArtifact(String name) {
		auth.getCurrentPlayer().inventory.activateArtifact(name);
		String formattedString = String.format("%s activated artifact %s.", auth.getCurrentPlayer().name, name);
		writetoFile(formattedString);
	}

	public void removeArtifactCardAfterUsing(String name) {
		auth.getCurrentPlayer().inventory.removeArtifactCardAfterUsing(name);
	}

	public void paralyseEveryone() {
		auth.players.forEach(p -> p.extraActions = -2);
		auth.getCurrentPlayer().extraActions = 0;
		String formattedString = String.format("%s used Stanley Parable.", auth.getCurrentPlayer().name);
		writetoFile(formattedString);
	}

	public boolean hasArtifactCard(String name) {
		return auth.getCurrentPlayer().inventory.hasArtifactCard(name);
	}

	public void swapAfterIndex(int first, int second, int third) { // puts three cards starting from index to threeCards
																	// array, updates the ingredient card deck using the
																	// parameters
		if ((first != second) & (second != third) & (third != first)) {
			ArrayList<IngredientCard> threeCards = new ArrayList<IngredientCard>();
			int index = ingredientCardDeck.index;
			int size = ingredientCardDeck.getDeck().size();
			IngredientCard currentCard = ingredientCardDeck.getDeck().get(index);
			IngredientCard nextCard = ingredientCardDeck.getDeck().get((index + 1) % size);
			IngredientCard twoNextCard = ingredientCardDeck.getDeck().get((index + 2) % size);
			threeCards.add(currentCard);
			threeCards.add(currentCard);
			threeCards.add(currentCard);
			threeCards.set(first - 1, currentCard);
			threeCards.set(second - 1, nextCard);
			threeCards.set(third - 1, twoNextCard);
			ingredientCardDeck.getDeck().set(index, threeCards.get(0));
			ingredientCardDeck.getDeck().set((index + 1) % size, threeCards.get(1));
			ingredientCardDeck.getDeck().set((index + 2) % size, threeCards.get(2));
			ingredientCardDeck.shouldShuffle = false;
			String formattedString = String.format("%s used Elixir of Insight.", auth.getCurrentPlayer().name);
			writetoFile(formattedString);
		}

	}

	public void writetoFile(String formattedString) {
		try {
			bufferedWriter.write(formattedString);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
