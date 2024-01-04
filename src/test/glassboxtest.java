package domain.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.PublicationArea;
import domain.PublicationCardDeck;

import java.util.ArrayList;

/*------------------------------------------------------------------------------
 * PublicationCardDeck object is instantiated within PublicationArea, 
 * it can be pass a new PublicationCardDeck object to the methods being tested, 
 * as these methods don't directly use the passed PublicationCardDeck. 
 * This way, we isolate and test the PublicationArea.
 */

class PublicationAreaTest {

    private PublicationArea publicationArea;

    @BeforeEach
    void setUp() {
        publicationArea = new PublicationArea();
    }

    @Test
    void testDisplayPublicationCardsRequirements() {
        ArrayList<String> requirements = publicationArea.displayPublicationCardsRequirements(new PublicationCardDeck());
        assertNotNull(requirements);
        assertFalse(requirements.isEmpty());
        
    }

    @Test
    void testDisplayPublicationCardsNames() {
        ArrayList<String> names = publicationArea.displayPublicationCardsNames(new PublicationCardDeck());
        assertNotNull(names);
        assertFalse(names.isEmpty());
        
    }

    @Test
    void testDisplayPublicationCardsValues() {
        ArrayList<Integer> values = publicationArea.displayPublicationCardsValues(new PublicationCardDeck());
        assertNotNull(values);
        assertFalse(values.isEmpty());
        
    }
}


