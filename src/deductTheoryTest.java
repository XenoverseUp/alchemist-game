import domain.*;
import enums.Avatar;
import org.junit.*;
import static org.junit.Assert.*;

public class deductTheoryTest {
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

    /* Black box testing */
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
     * Black box testing, Focus on External Behavior:
     * 
     * The test primarily focuses on the external behavior of the system. It checks
     * whether the association between the published theory and the alchemy marker
     * is maintained after calling board.debunkTheory(). The test doesn't rely on or
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

        assertNotNull("Debunked theory should be matched with correct marker",
                publicationDeck.getChosen().getAlchemyMarker());
    }

    /* Glass box testing */
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

    /* Glass box testing */
    @Test
    public void testDebunkTheoryAffectsCorrectPlayer() {

        AlchemyMarkerDeck markerDeck = board.alchemyMarkerDeck;
        markerDeck.setChosen(0);

        PublicationCardDeck publicationCardDeck = board.publicationCardDeck;
        publicationCardDeck.setChosen(0);
        board.publishTheory();
        int initialRep = auth.getCurrentPlayer().getReputation();
        auth.toggleCurrentUser();
        board.debunkTheory();

        assertEquals("Reputation should decrease for the theory's player",
                publicationCardDeck.getChosen().getPlayer().getReputation(),
                initialRep - 2);

    }

    /* Glass box testing */
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
