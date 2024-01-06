package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ArtifactCardDeck {
	private ArrayList<ArtifactCard> artifactCardDeck = new ArrayList<>();
	private Random random = new Random();

	public ArtifactCardDeck() {
		// NOTICE: Max price of an artifact should be 9 gold.

		artifactCardDeck.add(new ArtifactCard(
			"Elixir of Insight", 
			9,
			4,
			"Allows a player to view the top three cards of the ingredient deck and rearrange them in any order."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Robe of Respect", 
			6, 
			2, 
			"Each gain of reputation is bigger by 1. For example, if you debunk a theory, instead of gaining 2 points, you gain 3."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Trader's Touch", 
			4, 
			1, 
			"Transmuting ingredients gives you 2 golds instead of one for the rest of the game."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Stanley Parable", 
			6, 
			2, 
			"Every other player is paralyses 1 round except you. Use it wisely, though."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Magic Mortar", 
			8, 
			3, 
			"You get to keep one of the ingredients after making experiments on them. Useful for making more research or money."
		));

		artifactCardDeck.add(new ArtifactCard(
			"Printing Press", 
			5, 
			1, 
			"Publish a theory free of charge. So, you don't need to pay 1 gold to the bank." 
		));

		artifactCardDeck.add(new ArtifactCard(
			"Wisdom Idol", 
			3, 
			7, 
			"When your theory is debunked, you don't lose reputation points. However, keeping it till the end would be pretty cool, I guess." 
		));

		shuffle();
	}

	public ArtifactCard get(String name) {
		return this.artifactCardDeck
						.stream()
						.filter(card -> card.getName().equals(name))
						.findFirst()
						.get();
	}

	public void shuffle() {
		Collections.shuffle(artifactCardDeck);	
	}
	
	public ArtifactCard drawMysteryCard() {
		ArrayList<ArtifactCard> weighted = new ArrayList<>();
		this.artifactCardDeck.forEach(card -> {
			for (int i = 0; i < 12 - card.getPrice(); i++) weighted.add(card);
		});

		ArtifactCard card = weighted.get(random.nextInt(weighted.size()));
	
		if (card.getName() == "Elixir of Insight"){
			useElixirOfInsight();
		}

		if (card.getName() == "Robe of Respect"){
			useRobeOfRespect();
		}

		if (card.getName() == "Trader's Touch"){
			useTradersTouch();
		}

		if (card.getName() == "Magic Mortar"){
			useMagicMortar();
		}

		if (card.getName() == "Printing Press"){
			usePrintingPress();
		}

		if (card.getName() == "Wisdom Idol"){
			useWisdomIdol();
		}

		//return weighted.get(random.nextInt(weighted.size()));
		return card;
	}

	public ArrayList<ArtifactCard> getArtifactCardDeck(){
		return this.artifactCardDeck;
	}


	public void useElixirOfInsight() {
        viewAndRearrangeTopThreeCards();
        
    }
    
	public void useRobeOfRespect() {
		

	}

	public void useTradersTouch() {
	

	}

	public void useMagicMortar() {
	

	}

	public void usePrintingPress() {
		publishTheoryFreeOfCharge();
	
	}

	public void useWisdomIdol() {
		
	
	}


    private void viewAndRearrangeTopThreeCards() {
         ArrayList<IngredientCard> topThreeCards = IngredientCardDeck.subList(0, 3);

        for (IngredientCard card : topThreeCards) {
            System.out.println(card.getName());
        }

        Collections.shuffle(topThreeCards);
		
        for (int i = 0; i < 3; i++) {
            IngredientCardDeck.set(i, topThreeCards.get(i));
        }
	}


	private void publishTheoryFreeOfCharge() {
		PublicationCard chosenPublicationCard = PublicationCardDeck.getChosen();
		if (chosenPublicationCard != null && chosenPublicationCard.getAlchemyMarker() == null) {
			Player currentPlayer = Auth.getCurrentPlayer();
			currentPlayer.increaseReputation(1);
	
			// Set the Alchemy Marker associated with the publication card (assuming there is an available marker)
			AlchemyMarker chosenAlchemyMarker = AlchemyMarker.getChosen();
			if (chosenAlchemyMarker != null && chosenAlchemyMarker.checkAvailability()) {
				chosenPublicationCard.setAlchemyMarker(chosenAlchemyMarker);
				chosenAlchemyMarker.associate();
				chosenPublicationCard.setPlayer(currentPlayer);
	
				// Using SwingUtilities.invokeLater to update GUI in a thread-safe manner
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null,
							"Published Theory Successfully without any charge!\n Reputation: +1",
							"Success!", JOptionPane.PLAIN_MESSAGE);
				});
			} 
		}
    }


	private void activateTradersTouch() {
        
	
	}

	public int modifyReputationGain() {
		int reputation = getReputation();


    }




}
