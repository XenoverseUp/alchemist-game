package test.domain;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.ArtifactCard;
import domain.ArtifactCardDeck;

import java.util.ArrayList;

/*
 This ArtifactCardDeckDrawCardTest provide tests for the drawMysteryCard method in ArtifactCardDeck.java whose specifications (requires, modifies and effects statements) can be demosntarated as follows:

    Requires:   An initialized ArtifactCardDeck with at least one ArtifactCard.
                Each ArtifactCard should have a valid price.
                The Java Random library should be available.
 
    Modifies:   artifactCardDeck:   This method modifies the artifactCardDeck by creating a weighted list of ArtifactCards.
                                    The weighting is done through the price of each artifact card.
 
    Effects:    This method returns an ArtifactCard from the deck. The card is selected randomly, but cards with lower prices
                have a higher chance of being selected (the chance is inversely proportional to the price).
                If the deck is empty, this method will throw an IndexOutOfBoundsException.
 */

public class ArtifactCardDeckDrawCardTest {

    private ArtifactCardDeck cardDeck;

    // Requires: An instance of ArtifactCardDeck
    // Modifies: cardDeck
    // Effects: Initializes cardDeck and shuffles it
    @Before
    public void setUp() {
        cardDeck = new ArtifactCardDeck();
        cardDeck.shuffle();
    }

    // testStartingDeckIsNotNull: This is a Black Box test.
    // It checks the external behavior of the system - whether the deck is not null
    // after initialization - without considering the internal workings of the
    // method.
    // Requires: An initialized cardDeck
    // Effects: Checks if the starting deck is not null
    @Test
    public void testStartingDeckIsNotNull() {
        assertNotNull("Starting deck should not be null", cardDeck.getArtifactCardDeck());
    }

    // testDrawCardDoesNotChangeDeckSize: This is a Black Box test.
    // It checks the external behavior of the system - whether the deck size remains
    // the same after drawing a card - without considering the internal workings of
    // the method.
    // Requires: An initialized cardDeck
    // Modifies: cardDeck
    // Effects: Draws a card and checks if the deck size remains the same
    @Test
    public void testDrawCardDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getArtifactCardDeck().size();
        cardDeck.drawMysteryCard();
        assertEquals(initialSize, cardDeck.getArtifactCardDeck().size());
    }

    // testDrawCardReturnsArtifactCard: This is a Black Box test.
    // It checks the external behavior of the system - whether the drawn card is an
    // instance of ArtifactCard - without considering the internal workings of the
    // method.
    // Requires: An initialized cardDeck
    // Modifies: cardDeck
    // Effects: Draws a card and checks if it is an instance of ArtifactCard
    @Test
    public void testDrawCardReturnsArtifactCard() {
        ArtifactCard drawnCard = cardDeck.drawMysteryCard();
        assertTrue(drawnCard instanceof ArtifactCard);
    }

    // testDrawMysteryCardDoesNotReturnNull: This is a Black Box test.
    // It checks the external behavior of the system - whether the drawn card is not
    // null - without considering the internal workings of the method.
    // Requires: An initialized cardDeck
    // Modifies: cardDeck
    // Effects: Draws a card and checks if it is not null
    @Test
    public void testDrawMysteryCardDoesNotReturnNull() {
        ArtifactCard drawnCard = cardDeck.drawMysteryCard();
        assertNotNull("Drawn card should not be null", drawnCard);
    }

    // testShuffleDoesNotChangeDeckSize: This is a Black Box test.
    // It checks the external behavior of the system - whether the deck size remains
    // the same after shuffling - without considering the internal workings of the
    // method.
    // Requires: An initialized cardDeck
    // Modifies: cardDeck
    // Effects: Shuffles the deck and checks if the deck size remains the same
    @Test
    public void testShuffleDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getArtifactCardDeck().size();
        cardDeck.shuffle();
        int shuffledSize = cardDeck.getArtifactCardDeck().size();
        assertEquals("Shuffling should not change the deck size", initialSize, shuffledSize);
    }

    // testDeckIsShuffled: This is a Glass Box test.
    // It checks the internal behavior of the system - whether the order of the deck
    // has changed after shuffling - by comparing the deck before and after the
    // shuffle.
    // Requires: An initialized cardDeck
    // Modifies: cardDeck
    // Effects: Shuffles the deck and checks if the deck order has changed
    @Test
    public void testDeckIsShuffled() {
        ArrayList<ArtifactCard> originalDeck = new ArrayList<>(cardDeck.getArtifactCardDeck());
        cardDeck.shuffle();
        ArrayList<ArtifactCard> shuffledDeck = cardDeck.getArtifactCardDeck();
        assertNotEquals("Deck should be shuffled", originalDeck, shuffledDeck);
    }

    // testDrawAllCardsInDeckDoesNotChangeDeckSize: This is a Black Box test.
    // It checks the external behavior of the system - whether the deck size remains
    // the same after drawing all cards - without considering the internal workings
    // of the method.
    // Requires: An initialized cardDeck
    // Modifies: cardDeck
    // Effects: Draws all cards in the deck and checks if the deck size remains the
    // same
    @Test
    public void testDrawAllCardsInDeckDoesNotChangeDeckSize() {
        int deckSize = cardDeck.getArtifactCardDeck().size();
        ArrayList<ArtifactCard> drawnCards = new ArrayList<>();
        for (int i = 0; i < deckSize; i++) {
            ArtifactCard drawnCard = cardDeck.drawMysteryCard();
            drawnCards.add(drawnCard);
        }
        assertEquals(deckSize, cardDeck.getArtifactCardDeck().size());
    }

}
