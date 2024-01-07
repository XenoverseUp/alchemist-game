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


    //Black-box test since it tests the functionality without knowing the internal implementation. 
    //It verifies that drawing a card reduces the deck size by one.
    @Test
    public void testDrawCardRemovesOneCard() {
        int initialSize = cardDeck.getPublicationCardDeck().size();
        cardDeck.drawCard();
        assertEquals(initialSize - 1, cardDeck.getPublicationCardDeck().size());
    }


    //bBack-box test as it checks if the drawn card is an instance of PublicationCard 
    //without requiring knowledge of the internal implementation.
    @Test
    public void testDrawCardReturnsPublicationCard() {
        PublicationCard drawnCard = cardDeck.drawCard();
        assertTrue(drawnCard instanceof PublicationCard);
    }


    //Black-box test as it checks the behavior of the shuffle() method, 
    //ensuring the deck size remains the same after shuffling.
    @Test
    public void testShuffleDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getPublicationCardDeck().size();
        cardDeck.shuffle();
        int shuffledSize = cardDeck.getPublicationCardDeck().size();
        assertEquals("Shuffling should not change the deck size", initialSize, shuffledSize);
    }
    
    //Glass-box test as it examines the behavior of the method by directly 
    //controlling the loop based on the deck size and checking if the deck size becomes zero after drawing all cards.
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
