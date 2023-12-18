package domain;

import java.util.HashSet;
import java.util.Set;
import enums.Potion;

public class PlayerBoard {
	
	protected Set<Potion> discoveredPotions;

	public PlayerBoard() {
		this.discoveredPotions = new HashSet<>();
	}
	
	protected void addDiscoveredPotion(Potion po) {
		discoveredPotions.add(po);
	}
	
	

}
