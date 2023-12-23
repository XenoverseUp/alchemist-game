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

		artifactCardDeck.add(new ArtifactCard(
			"Magic Mortar", 
			8, 
			3, 
			"You get to keep one of the ingredients after making experiments on them. Useful for making more research or money."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Printing Press", 
			5, 
			1, 
			"Publish a theory free of charge. So, you don't need to pay 1 gold to the bank." 
		));

		artifactCardDeck.add(new ArtifactCard(
			"Wisdom Idol", 
			3, 
			7, 
			"When your theory is debunked, you don't lose reputation points. However, keeping it till the end would be pretty cool, I guess." 
		));

		shuffle();
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
