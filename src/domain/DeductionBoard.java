package domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import enums.Potion;


public class DeductionBoard {
	protected HashMap<String, Integer> ingredientAddresses;
	protected HashMap<HashSet<String>, Potion> experiementResults;
	protected int[][] deductionTable;
	
	public DeductionBoard() {
		
		this.deductionTable = new int[8][8];
		this.experiementResults = new HashMap<>();
		this.ingredientAddresses = new HashMap<String, Integer>() {{
			put("mushroom", 0);
			put("fern", 1);
			put("scorpion tail", 6);
			put("bird claw", 3);
			put("warty toad", 2);
			put("mandrake root", 5);
			put("raven's feather", 7);
			put("moonshade", 4);
		}};
	}
	
	public void addExperimentResult(String ingredient1, String ingredient2, Potion po) {
		
		this.experiementResults.put(new HashSet<>(Arrays.asList(ingredient1, ingredient2)), po);
		
	}
	
	public void markDeductionTable(String ingredient, int moleculeCoordinate){
		this.deductionTable[moleculeCoordinate][ingredientAddresses.get(ingredient)] = 1;
	}
	
	public void unmarkDeductionTable(String ingredient, int moleculeCoordinate){
		this.deductionTable[moleculeCoordinate][ingredientAddresses.get(ingredient)] = 0;
	}
	
	
	
	
	
}
