package test.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.ArtifactCard;
import domain.ArtifactCardDeck;

import java.util.ArrayList;

public class ArtifactCardDeckDrawCardTest {

    private ArtifactCardDeck cardDeck;

    @Before
    public void setUp() {
        cardDeck = new ArtifactCardDeck();
        cardDeck.shuffle();
    }

    @Test
    public void testStartingDeckIsNotNull() {
        assertNotNull("Starting deck should not be null", cardDeck.getArtifactCardDeck());
    }

    @Test
    public void testDrawCardDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getArtifactCardDeck().size();
        cardDeck.drawMysteryCard();
        assertEquals(initialSize, cardDeck.getArtifactCardDeck().size());
    }

    @Test
    public void testDrawCardReturnsArtifactCard() {
        ArtifactCard drawnCard = cardDeck.drawMysteryCard();
        assertTrue(drawnCard instanceof ArtifactCard);
    }

    @Test
    public void testDrawMysteryCardDoesNotReturnNull() {
        ArtifactCard drawnCard = cardDeck.drawMysteryCard();
        assertNotNull("Drawn card should not be null", drawnCard);
    }

    @Test
    public void testShuffleDoesNotChangeDeckSize() {
        int initialSize = cardDeck.getArtifactCardDeck().size();
        cardDeck.shuffle();
        int shuffledSize = cardDeck.getArtifactCardDeck().size();
        assertEquals("Shuffling should not change the deck size", initialSize, shuffledSize);
    }

    @Test
    public void testDeckIsShuffled() {
        ArrayList<ArtifactCard> originalDeck = new ArrayList<>(cardDeck.getArtifactCardDeck());
        cardDeck.shuffle();
        ArrayList<ArtifactCard> shuffledDeck = cardDeck.getArtifactCardDeck();
        assertNotEquals("Deck should be shuffled", originalDeck, shuffledDeck);
    }

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
