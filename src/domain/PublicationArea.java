package domain;

import java.util.ArrayList;

/**
 * PublicationArea
 */
public class PublicationArea {

    private PublicationCardDeck pbCardDeck = new PublicationCardDeck();
    private ArrayList<PublicationCard> availablePublicationCardDeck = pbCardDeck.getPublicationCardDeck();

    public ArrayList<String> displayPublicationCardsRequirements(PublicationCardDeck cards) {
        ArrayList<String> availableCardsReq = new ArrayList<String>();
        for (PublicationCard card : availablePublicationCardDeck) {
            availableCardsReq.add(card.getRequirement());
        }
        return availableCardsReq;
    }

    public ArrayList<String> displayPublicationCardsNames(PublicationCardDeck cards) {
        ArrayList<String> availableCardsName = new ArrayList<String>();
        for (PublicationCard card : availablePublicationCardDeck) {
            availableCardsName.add(card.getName());
        }
        return availableCardsName;
    }

    public ArrayList<Integer> displayPublicationCardsValues(PublicationCardDeck cards) {
        ArrayList<Integer> availableCardsName = new ArrayList<Integer>();
        for (PublicationCard card : availablePublicationCardDeck) {
            availableCardsName.add(card.getValue());
        }
        return availableCardsName;
    }

}