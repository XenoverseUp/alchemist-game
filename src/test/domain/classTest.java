package test.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.IngredientCard;
import domain.IngredientCardDeck;
import domain.Molecules;

import java.util.ArrayList;

class IngredientCardDeckTest {

    private IngredientCardDeck deck;

    @BeforeEach
    void setUp() {
        deck = new IngredientCardDeck();
    }

    @Test
    void testDrawCard() {
        IngredientCard drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertTrue(deck.getDeck().contains(drawnCard));

        assertTrue(deck.repOk());
    }

    @Test
    void testGetByName() {
        String cardName = "scorpion tail";
        IngredientCard card = deck.getByName(cardName);
        assertNotNull(card);
        assertEquals(cardName, card.getName());

        assertTrue(deck.repOk());
    }

    @Test
    void testAddCard() {
        IngredientCard newCard = new IngredientCard("newIngredient", Molecules.molecules.get(0));
        deck.addCard(newCard);
        assertTrue(deck.getDeck().contains(newCard));

        assertTrue(deck.repOk());
    }

    @Test
    void testIncrementIndex() {
        int initialSize = deck.getDeck().size();
        deck.incrIndex();
        int newIndex = deck.getIndex();

        assertEquals(0, newIndex);
        assertTrue(deck.getDeck().size() == initialSize || deck.getDeck().size() == initialSize + 1);

        assertTrue(deck.repOk());
    }
}

