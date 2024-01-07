package test.domain;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import domain.PublicationCard;
import domain.PublicationCardDeck;

import java.util.ArrayList;

public class PublicationCardDeckTest {

    private PublicationCardDeck cardDeck;

    @Before
    public void setUp() {
        cardDeck = new PublicationCardDeck();
        cardDeck.shuffle();
    }


    //Black-box test since it tests the functionality without knowing the internal implementation. 
    /**
     * Tests that drawing a card removes one card from the deck.
     *
     * Requires: The deck size before drawing a card should be greater than zero.
     * Modifies: publicationCardDeck (by removing one card)
     * Effects: Verifies that after drawing a card, the deck size reduces by one.
     */
    @Test
    public void testDrawCardRemovesOneCard() {
        int initialSize = cardDeck.getPublicationCardDeck().size();
        cardDeck.drawCard();
        assertEquals(initialSize - 1, cardDeck.getPublicationCardDeck().size());
    }


    //Black-box test as it checks if the drawn card is an instance of PublicationCard 
    //without requiring knowledge of the internal implementation.
    /**
     * Tests that drawing a card returns an instance of PublicationCard.
     *
     * Requires: None.
     * Modifies: None.
     * Effects: Verifies that the drawn card is an instance of PublicationCard.
     */
    @Test
    public void testDrawCardReturnsPublicationCard() {
        PublicationCard drawnCard = cardDeck.drawCard();
        assertTrue(drawnCard instanceof PublicationCard);
    }


    //Black-box test as it checks the behavior of the shuffle() method 
    /**
     * Tests that shuffling does not change the deck size.
     *
     * Requires: None.
     * Modifies: publicationCardDeck (by shuffling)
     * Effects: Verifies that shuffling doesn't change the deck size.
     */
    @Test
    public void testShuffleDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getPublicationCardDeck().size();
        cardDeck.shuffle();
        int shuffledSize = cardDeck.getPublicationCardDeck().size();
        assertEquals("Shuffling should not change the deck size", initialSize, shuffledSize);
    }
    
    //Glass-box test as it examines the behavior of the method by directly 
    //controlling the loop based on the deck size and checking if the deck size becomes zero after drawing all cards.
    /**
     * Tests that drawing all cards in the deck empties the deck.
     *
     * Requires: None.
     * Modifies: publicationCardDeck (by repeatedly drawing cards)
     * Effects: Verifies that after drawing all cards, the deck becomes empty.
     */
    @Test
    public void testDrawAllCardsInDeck() {
        int deckSize = cardDeck.getPublicationCardDeck().size();
        ArrayList<PublicationCard> drawnCards = new ArrayList<>();
        for (int i = 0; i < deckSize; i++) {
            PublicationCard drawnCard = cardDeck.drawCard();
            drawnCards.add(drawnCard);
        }
        assertEquals(0, cardDeck.getPublicationCardDeck().size());
    }



    //Both Black-box and Glass-box test. 
    //It validates that drawn cards are unique by using a list to keep track of the drawn cards, 
    //and it verifies the uniqueness without delving into the internal implementation.
    /**
     * Tests that all drawn cards are unique.
     *
     * Requires: None.
     * Modifies: publicationCardDeck (by repeatedly drawing cards)
     * Effects: Verifies that all drawn cards are different from each other.
     */
    @Test
    public void testDrawnCardsUnique() {
        int deckSize = cardDeck.getPublicationCardDeck().size();
        ArrayList<PublicationCard> drawnCards = new ArrayList<>();
        for (int i = 0; i < deckSize; i++) {
            PublicationCard drawnCard = cardDeck.drawCard();
            assertFalse("Card already drawn", drawnCards.contains(drawnCard));
            drawnCards.add(drawnCard);
        }
    }
}
