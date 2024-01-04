package domain.test;

import static org.junit.jupiter.api.Assertions.*;

import enums.DeductionToken;
import enums.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.DeductionBoard;

import java.util.HashMap;

class DeductionBoardTest {

    private DeductionBoard deductionBoard;

    @BeforeEach
    void setUp() {
        deductionBoard = new DeductionBoard();
    }

    @Test
    void testAddExperimentResult() {
        deductionBoard.addExperimentResult("mushroom", "fern", Potion.Health);
        HashMap<String[], DeductionToken> tokens = deductionBoard.getDeductionTokens();

        assertNotNull(tokens);
        assertEquals(1, tokens.size());

        String[] ingredients = {"mushroom", "fern"};
        DeductionToken token = tokens.get(ingredients);
        assertNotNull(token);
        assertEquals(DeductionToken.RedPlus, token);
    }

    @Test
    void testToggleDeductionTable() {
        deductionBoard.toggleDeductionTable("mushroom", 0);
        int[][] table = deductionBoard.getDeductionTable();

        assertNotNull(table);
        assertEquals(0, table[0][0]); 
    }



    
}
