package domain;

import java.util.ArrayList;
import java.util.Collections;

public class ArtifactCardDeck {
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();

	public ArtifactCardDeck() {
		ArtifactCard artifactCard1 = new ArtifactCard(
				"one-time",
				"allows a player to view the top three cards of the ingredient deck and rearrange them in any order",
				"Elixir of Insight",
				3);
		ArtifactCard artifactCard2 = new ArtifactCard(
				"one-time",
				"Once per round, the player can swap the position of two alchemy markers on the Deduction Board",
				"Philosopher’s Compass",
				3);
		ArtifactCard artifactCard3 = new ArtifactCard(
				"one-time",
				"Player chooses getting one of the potions that another player has and adding their own inventory",
				"Request Potion",
				3);
		ArtifactCard artifactCard4 = new ArtifactCard(
				"one-time",
				"player skips that round and does nothing",
				"Terminate the Player",
				3);

		artifactCardDeck.add(artifactCard1);
		artifactCardDeck.add(artifactCard2);
		artifactCardDeck.add(artifactCard3);
		artifactCardDeck.add(artifactCard4);

	}

	public void shuffle() {
		Collections.shuffle(artifactCardDeck);

	}

	public ArtifactCard drawCard() {
		ArtifactCard aCard = artifactCardDeck.remove(artifactCardDeck.size() - 1);
		return aCard;
	}

	public ArrayList<ArtifactCard> getArtifactCardDeck() {
		return this.artifactCardDeck;
	}

	public int getPriceOfNextArtifact() {

		if (artifactCardDeck.size() == 0) {
			return -1;
		}

		return artifactCardDeck.get(artifactCardDeck.size() - 1).getPrice();
	}

	public void addCard(ArtifactCard card) {

		artifactCardDeck.add(card);
	}

}
