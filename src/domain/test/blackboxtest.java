package domain.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.ArtifactCard;
import domain.ArtifactCardDeck;

import java.util.ArrayList;

/*--------------------------------------------------------------------
 * Verifying functionality, 
 * Retrieving a card by name, 
 * Drawing a mystery card, 
 * Checking if shuffling changes the order of the deck while guaranteeing all cards remain in the deck.
 */


class ArtifactCardDeckTest {

    private ArtifactCardDeck deck;

    @BeforeEach
    void setUp() {
        deck = new ArtifactCardDeck();
    }

    @Test
    void testGet() {
        String cardName = "Robe of Respect";
        ArtifactCard card = deck.get(cardName);
        assertNotNull(card);
        assertEquals(cardName, card.getName());
    }

    @Test
    void testDrawMysteryCard() {
        ArtifactCard drawnCard = deck.drawMysteryCard();
        assertNotNull(drawnCard);
        assertTrue(deck.getArtifactCardDeck().contains(drawnCard));
    }

    @Test
    void testShuffle() {
        ArrayList<ArtifactCard> originalDeck = new ArrayList<>(deck.getArtifactCardDeck());
        deck.shuffle();
        ArrayList<ArtifactCard> shuffledDeck = deck.getArtifactCardDeck();

        assertNotEquals(originalDeck, shuffledDeck);
        // Verify that the cards are still present after shuffling
        assertTrue(shuffledDeck.containsAll(originalDeck) && originalDeck.containsAll(shuffledDeck));
    }
}
