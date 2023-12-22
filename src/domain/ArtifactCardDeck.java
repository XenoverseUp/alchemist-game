package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArtifactCardDeck {
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();
	private Random random = new Random();

	public ArtifactCardDeck() {
		// NOTICE: Max price of an artifact should be 9 gold.

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

	public ArtifactCard get(String name) {
		return this.artifactCardDeck
						.stream()
						.filter(card -> card.getName().equals(name))
						.findFirst()
						.get();
	}

	public void shuffle() {
		Collections.shuffle(artifactCardDeck);	
	}
	
	public ArtifactCard drawMysteryCard() {
		ArrayList<ArtifactCard> weighted = new ArrayList<>();
		this.artifactCardDeck.forEach(card -> {
			for (int i = 0; i < 12 - card.getPrice(); i++) weighted.add(card);
		});

		return weighted.get(random.nextInt(weighted.size()));
	}

	public ArrayList<ArtifactCard> getArtifactCardDeck(){
		return this.artifactCardDeck;
	}


}
