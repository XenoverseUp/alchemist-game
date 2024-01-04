package test;
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

    @Test
    public void testDrawCardRemovesOneCard() {
        int initialSize = cardDeck.getPublicationCardDeck().size();
        PublicationCard drawnCard = cardDeck.drawCard();
        assertEquals(initialSize - 1, cardDeck.getPublicationCardDeck().size());
    }

    @Test
    public void testDrawCardReturnsPublicationCard() {
        PublicationCard drawnCard = cardDeck.drawCard();
        assertTrue(drawnCard instanceof PublicationCard);
    }

    @Test
    public void testShuffleDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getPublicationCardDeck().size();
        cardDeck.shuffle();
        int shuffledSize = cardDeck.getPublicationCardDeck().size();
        assertEquals("Shuffling should not change the deck size", initialSize, shuffledSize);
    }
    

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
