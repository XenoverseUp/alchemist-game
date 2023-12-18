package domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import enums.Potion;


public class DeductionBoard {
	private HashMap<String, Integer> ingredientAddresses;
	private HashMap<HashSet<String>, Potion> experimentResults;
	private int[][] deductionTable;
	
	public DeductionBoard() {
		
		this.deductionTable = new int[8][8];
		this.experimentResults = new HashMap<>();
		this.ingredientAddresses = new HashMap<String, Integer>() {{
			put("mushroom", 0);
			put("fern", 1);
			put("warty toad", 2);
			put("bird claw", 3);
			put("moonshade", 4);
			put("mandrake root", 5);
			put("scorpion tail", 6);
			put("raven's feather", 7);
		}};
	}
	
	public void addExperimentResult(String ingredient1, String ingredient2, Potion po) {
		this.experimentResults.put(new HashSet<>(Arrays.asList(ingredient1, ingredient2)), po);
	}
	
	public void markDeductionTable(String ingredient, int moleculeCoordinate){
		this.deductionTable[moleculeCoordinate][ingredientAddresses.get(ingredient)] = 1;
	}
	
	public void unmarkDeductionTable(String ingredient, int moleculeCoordinate){
		this.deductionTable[moleculeCoordinate][ingredientAddresses.get(ingredient)] = 0;
	}

	public void toggleDeductionTable(String ingredient, int coordinate) {
		if (this.deductionTable[coordinate][ingredientAddresses.get(ingredient)] == 0) 
			this.deductionTable[coordinate][ingredientAddresses.get(ingredient)] = 1;
		else	
			this.deductionTable[coordinate][ingredientAddresses.get(ingredient)] = 0;
	}

	public int[][] getDeductionTable() {
		return deductionTable;
	}

	public HashMap<HashSet<String>, Potion> getExperimentResults() {
		return experimentResults;
	}
	
}
