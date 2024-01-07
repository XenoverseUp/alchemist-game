import domain.*;
import enums.Avatar;
import org.junit.*;
import static org.junit.Assert.*;

/*
1-Requirements for debunkTheory:

The successful execution of debunkTheory requires the proper initialization and 
correct state of the following components: alchemyMarkerDeck, publicationCardDeck and auth, which must 
be initialized and in a state where functions such as getCurrentPlayer(), increaseReputation(), 
and removeGoldFromCurrentUser() can be utilized.

2-Modifications by debunkTheory:

Upon meeting the specified requirements, debunkTheory introduces several modifications. 
It impacts the player's reputation by increasing the reputation of the 
current player by 2 (as per specifications). 
The alchemy marker association is altered, causing the selected alchemy marker to dissociate. 
Furthermore, the selected publication card in the publishing card deck undergoes a 
modification as an alchemy marker is removed, and it is no longer linked to the current player. 
The GUI state is adjusted based on these conditions.

3-Effects of debunkTheory:

a) Successful Debunk:
When the specified requirements are fulfilled, indicating a successful debunk, 
debunkTheory results in an increase in the player's reputation by 2 and 
decrase in published theory's owner's reputation by 2. 
The selected alchemy marker dissociates, and the linked publication card is altered to remove the association. 
Then the correct alchemy marker is attached.
A graphical user interface dialog box is presented to the player, 
confirming the successful debunking of the theory. 

b) Unsuccessful Debunk:
When the specified requirements are fulfilled, but the published theory was already correct, 
debunkTheory results in an decrease in the player's reputation by 1.
Nothing happens to the selected alchemy marker.
A graphical user interface dialog box is presented to the player, 
confirming the unsuccessful debunking of the theory. 

c) No Changes (Conditions Not Met):
If the original requirements are not met, debunkTheory ensures that no modifications are made to the 
player's gold, reputation, or any condition of the deck.  
*/

public class debunkTheoryTest {
    private Board board;
    private Auth auth;

    @Before
    public void setUp() {
        auth = new Auth();
        auth.createUser("Beril", Avatar.Mystical);
        auth.createUser("Ceren", Avatar.Radiant);
        board = new Board(auth);
        assertFalse("Players list should not be empty", auth.players.isEmpty());
    }

    /*
     * 1) testDebunkTheoryWithWrongTheory(): Black box testing.
     * The test primarily focuses on the external behavior of the
     * system. It checks
     * whether the wrong marker is dissociated after calling board.debunkTheory().
     * The
     * test checks for an observable outcome—specifically, whether the marker is
     * dissociated. It doesn't delve into the specific internal details of how the
     * dissociation is implemented.
     */
    @Test
    public void testDebunkTheoryWithWrongTheory() {

        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);

        PublicationCardDeck publicationDeck = board.publicationCardDeck;
        publicationDeck.setChosen(0);
        board.publishTheory(); // ASSUME PUBLISHED THEORY IS WRONG (7/8 POSSIBILITY)
        auth.toggleCurrentUser();
        board.debunkTheory();

        assertFalse("Marker should be dissociated", markerDeck.getChosen().checkAvailability());

    }

    /*
     * 2) testDebunkTheoryWithAssociatedMarker(): Black box testing.
     * The test primarily focuses on the external behavior of the
     * system. It checks
     * whether the association between the published theory and the true alchemy
     * marker
     * is established after calling board.debunkTheory(). The test doesn't rely on
     * or
     * assume any knowledge of the internal workings of the system. It interacts
     * with the system through its public interfaces board.publishTheory()
     * and board.debunkTheory()
     * 
     */
    @Test
    public void testDebunkTheoryWithAssociatedMarker() {

        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);

        PublicationCardDeck publicationDeck = board.publicationCardDeck;
        publicationDeck.setChosen(0);

        board.publishTheory();
        auth.toggleCurrentUser();
        board.debunkTheory();

        assertNotNull("Debunked theory should be matched with the correct marker",
                publicationDeck.getChosen().getAlchemyMarker());
    }

    /*
     * 3) testReputationIncreaseOnDebunkedTheory(): Black box testing.
     * The test primarily focuses on the external behavior of the
     * system. It checks whether the reputation of the current player increases by 2
     * points after calling board.debunkTheory().
     */
    @Test
    public void testReputationIncreaseOnDebunkedTheory() {
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);

        PublicationCardDeck publicationCardDeck = board.publicationCardDeck;
        publicationCardDeck.setChosen(0);

        board.publishTheory(); // ASSUME PUBLISHED THEORY IS WRONG (7/8 POSSIBILITY)
        auth.toggleCurrentUser();
        int initialRep = auth.getCurrentPlayer().getReputation();
        board.debunkTheory();

        int rep = auth.getCurrentPlayer().getReputation();

        assertEquals("Reputation should be increased on debunked theory", rep, initialRep + 2);
    }

    /*
     * 4) testDebunkTheoryAffectsCorrectPlayer(): Glass box testing.
     * The test is designed to verify specific aspects of the
     * internal logic of the system. It checks conditions related to the association
     * and dissociation of markers and the calculation of player reputation. The
     * test includes assertions checking preconditions (for example marker
     * association
     * before debunking) and postconditions (for example marker dissociation and
     * reputation decrease after debunking).
     */
    @Test
    public void testDebunkTheoryAffectsCorrectPlayer() {
        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);

        PublicationCardDeck publicationCardDeck = board.publicationCardDeck;
        publicationCardDeck.setChosen(0);

        board.publishTheory();
        auth.toggleCurrentUser();
        int initialRep = publicationCardDeck.getChosen().getPlayer().getReputation();

        assertTrue("Marker should be associated before debunking", markerDeck.getChosen().checkAvailability());
        board.debunkTheory();
        assertFalse("Marker should be dissociated after debunking", markerDeck.getChosen().checkAvailability());

        int finalRep = publicationCardDeck.getChosen().getPlayer().getReputation();
        assertEquals("Reputation should decrease for the theory's player", initialRep - 2, finalRep);
    }

    /*
     * 5) testDebunkTheoryWithNoTheoryPublicationCard(): Glass box testing.
     * The test delves into the internal logic of the system by
     * examining
     * its behavior when trying to debunk a theory without a corresponding theory
     * publication card (achieved through publicationCardDeck.setChosen(1)). The
     * test evaluates how the system reacts to this particular scenario. It
     * makes assertions to verify preconditions, such as the initial
     * reputation of the players, and postconditions, specifically confirming that
     * attempting to debunk without a theory publication card results in no change
     * in player reputation
     */
    @Test
    public void testDebunkTheoryWithNoTheoryPublicationCard() {

        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);

        PublicationCardDeck publicationCardDeck = board.publicationCardDeck;
        publicationCardDeck.setChosen(0);
        board.publishTheory();
        int publisherInitialRep = publicationCardDeck.getChosen().getPlayer().getReputation();
        publicationCardDeck.setChosen(1);
        auth.toggleCurrentUser();
        int debunkerInitialRep = auth.getCurrentPlayer().getReputation();
        board.debunkTheory();
        publicationCardDeck.setChosen(0);

        assertEquals("Nothing should happen", publicationCardDeck.getChosen().getPlayer().getReputation(),
                publisherInitialRep);
        assertEquals("Nothing should happen", auth.getCurrentPlayer().getReputation(),
                debunkerInitialRep);

    }

}
