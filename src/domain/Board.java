package domain;

import enums.Potion;


public class Board {

	private Auth auth;
	public IngredientCardDeck ingredientCardDeck;
	public ArtifactCardDeck artifactCardDeck;
	public PublicationCardDeck publicationCardDeck;
	public AlchemyMarkerDeck alchemyMarkerDeck;
	

	public Board(Auth auth) {
		this.auth = auth;
		this.alchemyMarkerDeck = new AlchemyMarkerDeck();
		this.ingredientCardDeck = new IngredientCardDeck();
		this.artifactCardDeck = new ArtifactCardDeck();
		this.publicationCardDeck = new PublicationCardDeck(this.ingredientCardDeck.getDeck());
	}

	public void dealCards() {
		for (Player player : auth.players) {
			for (int i = 0; i < 2; i++) {
				IngredientCard iCard = this.ingredientCardDeck.drawCard();
				player.inventory.addIngredientCard(iCard);
			}
		}
	}

	public void dealGolds() {
		for (Player p : auth.players)
			p.inventory.addGold(3);
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

	public void buyArtifact() {
		ArtifactCard aCard = this.artifactCardDeck.drawCard();
		this.auth.addArtifactCardToCurrentPlayer(aCard);
		this.auth.removeGoldFromCurrentUser(aCard.getPrice());
	}

	public Auth getAuth() {
		return auth;
	}

	public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn) {
		IngredientCard ingredient1 = auth.getIngredientCardFromCurrentPlayer(ingredientName1);
		IngredientCard ingredient2 = auth.getIngredientCardFromCurrentPlayer(ingredientName2);

		Potion potion = PotionBrewingArea.combine(ingredient1, ingredient2);

		try {
			auth.getCurrentPlayer().use(potion, testOn);
		} catch (Exception e) {
			System.out.println(e);
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
					JOptionPane.showMessageDialog(null, "Published Theory Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
				});


			}
		}
	}

	public void debunkTheory(int cardID, int markerID) {
		if (alchemyMarkerDeck.getMarker(markerID).checkAvailability()) {
			if (this.publicationCardDeck.getCard(cardID).getAlchemyMarker() != null) {
				if (alchemyMarkerDeck.getMarker(markerID).getMolecule()
						.equals(this.publicationCardDeck.getCard(cardID).getAlchemyMarker().getMolecule())) {

				} else {

				}
				auth.getCurrentPlayer().increaseReputation(1);
				auth.removeGoldFromCurrentUser(1);
				publicationCardDeck.getCard(cardID).setAlchemyMarker(alchemyMarkerDeck.getMarker(markerID));
				alchemyMarkerDeck.getMarker(markerID).associate();
				publicationCardDeck.getCard(cardID).setPlayer(auth.getCurrentPlayer());

			}
		}
	}

}
