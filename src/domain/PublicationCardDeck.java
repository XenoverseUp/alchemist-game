package domain;

import java.util.ArrayList;
import java.util.Collections;

public class PublicationCardDeck {
    
    private ArrayList<PublicationCard> publicationCardDeck = new ArrayList<PublicationCard>();


    public PublicationCardDeck() {
		PublicationCard publicationCard_1 = new PublicationCard("ARTFAIC", "player has more than 2 minerals in its inventory", 10);
        PublicationCard publicationCard_2 = new PublicationCard("ARTFACC", "Player has value > 50", 9);
        PublicationCard publicationCard_3 = new PublicationCard("MFCTIIC", "player has at least 1 herb in its inventory", 3);
        PublicationCard publicationCard_4 = new PublicationCard("MCTICND", "player’s herb values sum > 7", 7);
        PublicationCard publicationCard_5 = new PublicationCard("DEDRICD", "Player has 3 mushrooms in its inventory", 2);
        PublicationCard publicationCard_6 = new PublicationCard("MERIDUD", "player owns at least 1 mineral in its inventory", 13);
		
		publicationCardDeck.add(publicationCard_1);
        publicationCardDeck.add(publicationCard_2);
        publicationCardDeck.add(publicationCard_3);
        publicationCardDeck.add(publicationCard_4);
        publicationCardDeck.add(publicationCard_5);
        publicationCardDeck.add(publicationCard_6);

	}



    public void shuffle() {
		Collections.shuffle(publicationCardDeck);
		
	}
	
	public PublicationCard drawCard() {
		
		PublicationCard pCard = publicationCardDeck.remove(publicationCardDeck.size() - 1);
		
		return pCard;	
	}
















}
