package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import enums.Ingredient;

/**
 * DeductionBoard
 */
public class DeductionBoard {

    private HashMap books = new HashMap<String, Ingredient>();
    public ArrayList theories = new ArrayList<Theory>();

    public String generateTheoryName() {

        // ??
        return "theoryName";
    }

    
}