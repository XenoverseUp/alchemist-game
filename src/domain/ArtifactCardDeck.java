package domain;

import java.util.ArrayList;
import java.util.Collections;

public class ArtifactCardDeck {
	
	
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();


	public ArtifactCardDeck() {
		ArtifactCard artifactCard_1 = new ArtifactCard("one-time", "allows a player to view the top three cards of the ingredient deck and rearrange them in any order", "Elixir of Insight");
		ArtifactCard artifactCard_2 = new ArtifactCard("one-time", "Once per round, the player can swap the position of two alchemy markers on the Deduction Board", "Philosopher’s Compass");
		ArtifactCard artifactCard_3 = new ArtifactCard("one-time", "Player chooses getting one of the potions that another player has and adding their own inventory", "Request Potion");
		ArtifactCard artifactCard_4 = new ArtifactCard("one-time", "player skips that round and does nothing", "Terminate the Player");

		artifactCardDeck.add(artifactCard_1);
		artifactCardDeck.add(artifactCard_2);
		artifactCardDeck.add(artifactCard_3);
		artifactCardDeck.add(artifactCard_4);

	}


	
	public void shuffle() {
		Collections.shuffle(artifactCardDeck);
		
	}
	
	public ArtifactCard drawCard() {
		
		ArtifactCard aCard = artifactCardDeck.remove(artifactCardDeck.size() - 1);
		
		return aCard;	
	}

	public ArrayList<ArtifactCard> getArtifactCardDeck(){
		return this.artifactCardDeck;
	}

}
