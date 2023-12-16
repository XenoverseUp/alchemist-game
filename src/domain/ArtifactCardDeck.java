package domain;

import java.util.ArrayList;
import java.util.Collections;

public class ArtifactCardDeck {
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();

	public ArtifactCardDeck() {
		artifactCardDeck.add(new ArtifactCard(
			"Elixir of Insight", 
			10,
			"allows a player to view the top three cards of the ingredient deck and rearrange them in any order",
		));

		artifactCardDeck.add(new ArtifactCard( 
			"Philosopher's Compass", 
			5,
			"Once per round, the player can swap the position of two alchemy markers on the Deduction Board", 
		));

		artifactCardDeck.add(new ArtifactCard( 
			"Request Potion", 
			3,
			"Player chooses getting one of the potions that another player has and adding their own inventory", 
		));

		artifactCardDeck.add(new ArtifactCard( 
			"Terminate the Player", 
			7,
			"player skips that round and does nothing", 
		));
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

}
