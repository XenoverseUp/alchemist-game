package domain;

import java.util.ArrayList;
import java.util.Collections;

public class ArtifactCardDeck {
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();
	private int drawingIndex = 0;

	public ArtifactCardDeck() {
		artifactCardDeck.add(new ArtifactCard(
			"Elixir of Insight", 
			9,
			4,
			"Allows a player to view the top three cards of the ingredient deck and rearrange them in any order."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Robe of Respect", 
			6, 
			2, 
			"Each gain of reputation is bigger by 1. For example, if you debunk a theory, instead of gaining 2 points, you gain 3."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Trader's Touch", 
			4, 
			1, 
			"Transmuting ingredients gives you 2 golds instead of one for the rest of the game."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Stanley Parable", 
			6, 
			2, 
			"Every other player is paralyses 1 round except you. Use it wisely, though."
		));

		// artifactCardDeck.add(new ArtifactCard( 
		// 	"Philosopher's Compass", 
		// 	5,
		// 	2,
		// 	"Once per round, the player can swap the position of two alchemy markers on the Deduction Board"
		// ));

		// artifactCardDeck.add(new ArtifactCard( 
		// 	"Request Potion", 
		// 	3,
		// 	1,
		// 	"Player chooses getting one of the potions that another player has and adding their own inventory"
		// ));

		// artifactCardDeck.add(new ArtifactCard( 
		// 	"Terminate the Player", 
		// 	7,
		// 	10,
		// 	"player skips that round and does nothing"
		// ));
	}

	public void shuffle() {
		Collections.shuffle(artifactCardDeck);	
	}
	
	public ArtifactCard drawCard() {
		ArtifactCard card = this.artifactCardDeck.get(drawingIndex);
		this.drawingIndex = this.getNextIndex();
		return card;
	}

	public ArrayList<ArtifactCard> getArtifactCardDeck(){
		return this.artifactCardDeck;
	}

	public int getPriceOfNextArtifact(){

		if (artifactCardDeck.size() == 0){
			return -1;
		}

		return artifactCardDeck.get(artifactCardDeck.size() - 1).getPrice();
	}

	public ArtifactCard getArtifactByName(String name) {
		for (ArtifactCard card: artifactCardDeck) {
			if (card.getName().equals(name)) return card;
		}

		return null;
	}

	private int getNextIndex() {
		if (this.drawingIndex == this.artifactCardDeck.size() - 1) return 0;
		return this.drawingIndex + 1; 
	}

}
