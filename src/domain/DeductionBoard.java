package domain;

import java.util.HashMap;

import enums.DeductionToken;
import enums.Potion;


public class DeductionBoard {
	private HashMap<String, Integer> ingredientAddresses;
	private HashMap<String[], DeductionToken> deductionTokens;
	private int[][] deductionTable;
	
	public DeductionBoard() {
		this.deductionTable = new int[8][8];
		this.deductionTokens = new HashMap<>();

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
		this.deductionTokens.put(new String[]{ingredient1, ingredient2}, findDeductionToken(po));
	}
	
	public void toggleDeductionTable(String ingredient, int coordinate) {
		this.deductionTable[coordinate][ingredientAddresses.get(ingredient)] ^= 1;
	}

	public int[][] getDeductionTable() {
		return deductionTable.clone();
	}

	public HashMap<String[], DeductionToken> getDeductionTokens() {
		return (HashMap<String[], DeductionToken>) deductionTokens.clone();
	}

	private DeductionToken findDeductionToken(Potion p) {
		switch (p) {
			case Health: return DeductionToken.RedPlus;
			case Poison: return DeductionToken.RedMinus;
			case Wisdom: return DeductionToken.BluePlus;
			case Insanity: return DeductionToken.BlueMinus;
			case Speed: return DeductionToken.GreenPlus;
			case Paralysis: return DeductionToken.GreenPlus;
			case Neutral: return DeductionToken.Neutral;
			default: return null;
		}
	}
	 
	
}
