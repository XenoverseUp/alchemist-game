package domain;

import java.util.ArrayList;

public class ArtifactCardDeck {
	
	
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();
	
	public void shuffle() {
		
		
	}
	
	public ArtifactCard drawCard() {
		
		ArtifactCard aCard = artifactCardDeck.remove(artifactCardDeck.size() - 1);
		
		return aCard;	
	}

}
