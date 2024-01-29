package test.domain;
import domain.*;
import enums.Avatar;
import org.junit.*;
import static org.junit.Assert.*;

public class publishTheoryTest {

    /*
      Publish Theory Test (Ceren):
      Specifications:
      1- Requires: 
      - alchemyMarkerDeck: must be propoerly initialized and have a correct state in order to 
      perform the getChosen() function. 
      - publicationCardDeck: necessary to initialize and ensure that it is in a suitable condition to 
      use the getChosen() method. 
      - auth: has to be initialized and in a state where the functions getCurrentPlayer(), increaseReputation(), and 
      removeGoldFromCurrentUser() may be used. 
      - alchemyMarkerDeck.getChosen().checkAvailability() should provide a boolean value to indicate the course 
      of action for the method. 

      2- Modifies: 
      - Player's Gold and Reputation: When the requirements above are fulfilled, the reputation of the current player is 
      increased by 1, and the amount of gold posessed by the player reduces by 1.
      - Alchemy Marker Association: The selected alchemy marker becomes linked to an ingredient card when the above conditions are met. 
      - Publication Card Deck: The selected card in the publishing card deck is altered by placing an alchemy marker and linking it to 
      the current player. 
      - GUI State: A graphical user interface dialog box is presented based on certain conditions. 

      3- Effects: 
      1. Publication Successful: 
      - Player's reputation increases by 1.
      - Player's gold reduces by 1. 
      - The selected publication card gets marked with an alchemy marker and linked to the player currently 
      posessing it.
      - A notification confirming the Publishing is successful is displayed to the player. 
      2. No changes (if conditions are not met):
      - No modifications will be made if the original requirements are not met. 
      - Specifically,
      If alchemyMarkerDeck.getChosen().checkAvailability() returns a boolean value of false, it indicates that no modifications are made to 
      the player's gold, reputation or any condition of the deck. 
    */

    private Board board;
    private Auth auth;

    @Before
    public void setUp() {
        auth = new Auth();
        auth.createUser("Ceren", Avatar.Celestial);
        auth.createUser("Beril", Avatar.Luminous);
        board = new Board(auth);
        assertFalse("Players list should not be empty", auth.players.isEmpty());
    }


    /* 
    1) testSuccessfulTheoryPublication(): Black Box Testing. 
    Focus: Evaluating the functionality of the publishTheory method from an external standpoint, specifically examining if the 
    marker is properly set and if the reputation and gold values are adjusted accordingly. 
    This testing does not take into consideration the internal mechanisms.
    */
    @Test
    public void testSuccessfulTheoryPublication() {
        
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0); 
        PublicationCardDeck pubDeck = board.publicationCardDeck;
        pubDeck.setChosen(0); 
        board.publishTheory();
        assertNotNull("Marker should be set", pubDeck.getChosen().getAlchemyMarker());
        assertEquals("Reputation should increase", 1, auth.getCurrentPlayer().getReputation());
        assertEquals("Gold should decrease", -1, auth.getCurrentPlayer().inventory.getGold());
    }

    /* 
    2) testTheoryPublicationWithUnavailableMarker(): Black Box Testing. 
    Focus: Verifies if the system functions appropriately, (i.e does not indicate the presence of a marker) when a marker is not accessible,
    only based on externally visible actions. 
    */
    @Test
    public void testTheoryPublicationWithUnavailableMarker() {
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);
        markerDeck.getChosen().associate(); 
        PublicationCardDeck pubDeck = board.publicationCardDeck;
        pubDeck.setChosen(0);
        board.publishTheory();
        assertNull("Marker should not be set", pubDeck.getChosen().getAlchemyMarker());
    }

    /* 
    3) testTheoryPublicationWithAlreadyAssociatedMarker(): White Box Testing. 
    Focus: Developed with a knowledge of the system's internal state, notably that publishing cards can already contain 
    associated markers. The test evaluates system's response in this particular circumstance.
    */
    @Test
    public void testTheoryPublicationWithAlreadyAssociatedMarker() {
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0); 
        PublicationCardDeck pubDeck = board.publicationCardDeck;
        pubDeck.setChosen(0); 
        pubDeck.getChosen().setAlchemyMarker(markerDeck.getChosen());
        board.publishTheory();
        assertEquals("Marker should remain the same", markerDeck.getChosen(), pubDeck.getChosen().getAlchemyMarker());
    }

    /* 
    4) testGoldDeductionOnSuccessfulTheoryPublication(): White Box Testing. 
    Focus: Verifies the alteration of the internal state (deduction of gold) following the execution of the method, requiring comprehension 
    of how the method internally impacts the player's gold.
    */
    @Test
    public void testGoldDeductionOnSuccessfulTheoryPublication() {
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0); 
        PublicationCardDeck pubDeck = board.publicationCardDeck;
        pubDeck.setChosen(0); 
        int initialGold = auth.getCurrentPlayer().inventory.getGold();
        board.publishTheory();
        assertEquals("Gold should be deducted on successful publication", initialGold - 1, auth.getCurrentPlayer().inventory.getGold());
    }
    
    /* 
    5) testTheoryPublicationAffectsCorrectPlayer(): Black Box Testing. 
    Focus: Confirms that the impact of the publication (the connection between the publication and the current player)
    is as anticipated, without going into detail about how the method internally manages switching or publishing logic. 
    */
    @Test
    public void testTheoryPublicationAffectsCorrectPlayer() {
        auth.toggleCurrentUser(); 
        Player currentPlayer = auth.getCurrentPlayer();
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);
        PublicationCardDeck pubDeck = board.publicationCardDeck;
        pubDeck.setChosen(0); 
        board.publishTheory();
        assertEquals("The publication should be associated with the current player", currentPlayer, pubDeck.getChosen().getPlayer());
    }
    
    
}
