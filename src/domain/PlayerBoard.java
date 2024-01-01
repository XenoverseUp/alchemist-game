package domain;

import java.util.HashSet;
import enums.Potion;

public class PlayerBoard {
	
	protected HashSet<Potion> discoveredPotions;

	public PlayerBoard() {
		this.discoveredPotions = new HashSet<>();
	}
	
	protected void addDiscoveredPotion(Potion po) {
		discoveredPotions.add(po);
	}
	
	protected HashSet<Potion> getDiscoveredPotions(){
		
		return (HashSet<Potion>) discoveredPotions.clone();
	}
	

}
