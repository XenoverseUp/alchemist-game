package tests.digdem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Auth;
import domain.Board;
import enums.Avatar;
import enums.Potion;

public class TestsUseMethodPlayerClassDomainPackage {
    Auth auth;
    Board gameBoard;


    @BeforeEach
    void setup(){
        auth = new Auth();
        gameBoard = new Board(auth);
        auth.createUser("Digdem", Avatar.Serene);
        auth.createUser("Taylor S.", Avatar.Galactic);
        gameBoard.ingredientCardDeck.shuffle();
        gameBoard.dealCards();
        gameBoard.artifactCardDeck.shuffle();
        gameBoard.dealGolds();
        System.out.println("Setting up a new Alchemist Game, adding 2 players and initializing game before each test.");
    }

    @AfterEach
    void destroySetup(){
        auth = null;
        gameBoard = null;
        System.out.println("Test is finished.");
    }

    @Test
    void testNullParamater(){
        Exception exception = assertThrows(Exception.class, () ->
                        {auth.getCurrentPlayer().use(Potion.Health, "");});
        assertTrue(exception.getMessage().equals("You can test your potion on only yourself of your student."));
    }

    @Test
    void testInvalidParamater(){
        Exception exception = assertThrows(Exception.class, () ->
                        {auth.getCurrentPlayer().use(Potion.Poison, "me");});
        assertTrue(exception.getMessage().equals("You can test your potion on only yourself of your student."));
    }

    @Test
    void testStudentHealth() throws Exception{
        int gold = auth.getCurrentPlayer().inventory.getGold();
        auth.getCurrentPlayer().use(Potion.Health, "student");

        assertEquals(gold - 1, auth.getCurrentPlayer().inventory.getGold());
        assertTrue(auth.getCurrentPlayer().inventory.getGold() >= 0, "The gold of a player cannot be negative!");
    }

    @Test
    void testStudentAllPotions() throws Exception{
        int gold = auth.getCurrentPlayer().inventory.getGold();
        
        for (Potion p: Potion.values()){
            auth.getCurrentPlayer().use(Potion.Health, "student");
            assertEquals(gold - 1, auth.getCurrentPlayer().inventory.getGold(), "Bug happened with potion " + p.toString());
            assertTrue(auth.getCurrentPlayer().inventory.getGold() >= 0, "The gold of a player cannot be negative! " + p.toString());
            auth.addGoldToCurrentUser(1);
        }
    }

    @Test
    void testSelfHealth() throws Exception{
        int sickness = auth.getCurrentPlayer().getSickness();
        auth.getCurrentPlayer().use(Potion.Health, "self");

        assertEquals(sickness - 1, auth.getCurrentPlayer().getSickness());
        assertTrue(auth.getCurrentPlayer().getSickness() >= 0, "The sickness of a player cannot be negative!");
    }

    @Test
    void testSelfPoison() throws Exception{
        int sickness = auth.getCurrentPlayer().getSickness();
        auth.getCurrentPlayer().use(Potion.Poison, "self");

       
        assertEquals(sickness + 1, auth.getCurrentPlayer().getSickness());
        assertTrue(auth.getCurrentPlayer().getSickness() <= 2, "The sickness of a player cannot be more than 2!");
    }

    @Test
    void testSelfPoisonGetSick() throws Exception{
        auth.getCurrentPlayer().setSickness(2);
        auth.getCurrentPlayer().use(Potion.Poison, "self");
        assertEquals(0, auth.getCurrentPlayer().getSickness(), "After reaching sickness = 3, the sickness should be reset!");
        assertEquals(0, auth.getCurrentPlayer().inventory.getGold(), "After reaching sickness = 3, the palyer should lose all golds!");
    }

    @Test
    void testSelfWisdom() throws Exception{
        int reputation = auth.getCurrentPlayer().getReputation();
        auth.getCurrentPlayer().use(Potion.Wisdom, "self");

        assertEquals(reputation + 1, auth.getCurrentPlayer().getReputation());
    }

    @Test
    void testSelfInsanity() throws Exception{
        int reputation = auth.getCurrentPlayer().getReputation();
        auth.getCurrentPlayer().use(Potion.Wisdom, "self");

        assertEquals(reputation - 1, auth.getCurrentPlayer().getReputation());
    }

    @Test
    void testSelfSpeed() throws Exception{
        auth.getCurrentPlayer().use(Potion.Speed, "self");

        assertEquals(1, auth.getCurrentPlayer().extraActions);
    }

    @Test
    void testSelfParalysis() throws Exception{
        auth.getCurrentPlayer().use(Potion.Paralysis, "self");

        assertEquals(-2, auth.getCurrentPlayer().extraActions);
    }


    
    



    





}
