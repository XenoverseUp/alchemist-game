package domain.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.PublicationCard;
import domain.PublicationCardDeck;

import java.util.ArrayList;

/*
 * Through Glass Box testing, the goal is testing the internal workings of a program, not just its functionality. 
 * In the PublicationCardDeckTest class, we’re testing the PublicationCardDeck class’s methods and we know how they’re implemented.

testDrawCard: Through such a test, we are checking if the drawCard method correctly removes a card from the deck and returns it.
->After a card is drawn, it should no longer be in the deck.
testShuffle: Through such a test, we are checking  if the shuffle method correctly shuffles the deck. 
->After shuffling, the order of the deck should change, but all cards should still be present in the deck.
 */

class PublicationCardDeckGbTest {

    private PublicationCardDeck deck;

    @BeforeEach
    void setUp() {
        deck = new PublicationCardDeck();
    }

    @Test
    void testDrawCard() {
        PublicationCard drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertFalse(deck.getPublicationCardDeck().contains(drawnCard));
    }

    @Test
    void testShuffle() {
        ArrayList<PublicationCard> originalDeck = new ArrayList<>(deck.getPublicationCardDeck());
        deck.shuffle();
        ArrayList<PublicationCard> shuffledDeck = deck.getPublicationCardDeck();

        assertNotEquals(originalDeck, shuffledDeck);
        // Verify that the cards are still present after shuffling
        assertTrue(shuffledDeck.containsAll(originalDeck) && originalDeck.containsAll(shuffledDeck));
    }
}
