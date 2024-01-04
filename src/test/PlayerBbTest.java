package domain.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Player;
import enums.Avatar;
import enums.Potion;

/*
 * Through Black Box testing method, the functionality of an application is tested without looking into its internal code structure. 
 * In the PlayerTest class, we’re testing the Player class’s methods without knowing how they’re implemented.

testIncreaseReputation: Checks if the increaseReputation method correctly increases the player’s reputation by the given amount.
testDecreaseReputation: Checks if the decreaseReputation method correctly decreases the player’s reputation by the given amount.
testIncreaseSickness: Checks if the increaseSickness method correctly increases the player’s sickness level by the given amount.
testDecreaseSickness: Checks if the decreaseSickness method correctly decreases the player’s sickness level by the given amount.
testUsePotion: Checks if the use method correctly updates the player’s sickness and reputation levels based on the type of potion used.
 */


    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1, "Test Player", Avatar.Galactic);
    }

    @Test
    void testIncreaseReputation() {
        player.increaseReputation(10);
        assertEquals(10, player.getReputation());
    }

    @Test
    void testDecreaseReputation() {
        player.increaseReputation(10);
        player.decreaseReputation(5);
        assertEquals(5, player.getReputation());
    }

    @Test
    void testIncreaseSickness() {
        player.increaseSickness(2);
        assertEquals(2, player.getSickness());
    }

    @Test
    void testDecreaseSickness() {
        player.increaseSickness(2);
        player.decreaseSickness(1);
        assertEquals(1, player.getSickness());
    }

    @Test
 
            player.use(Potion.Health, "self");
            assertEquals(-1, player.getSickness());
            player.use(Potion.Poison, "self");
            assertEquals(0, player.getSickness());
            player.use(Potion.Wisdom, "self");
            assertEquals(1, player.getReputation());
            player.use(Potion.Insanity, "self");
            assertEquals(0, player.getReputation());
        } catch (Exception e) {
           fai(

    "Exception s
    o
