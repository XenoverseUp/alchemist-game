package domain;

import java.util.ArrayList;


public class PublicationCardDeck {
    
    private ArrayList<PublicationCard> publicationCardDeck = new ArrayList<PublicationCard>();


    public PublicationCardDeck(ArrayList<IngredientCard> ingredientCardDeck) {

        int id = 0;
        for (IngredientCard i: ingredientCardDeck){
            PublicationCard publicationCard = new PublicationCard(i, id);
            publicationCardDeck.add(publicationCard);
            id ++;
        }

	}

    public PublicationCard getCard(int id){
        return publicationCardDeck.get(id);
    }
    // no need to shuffle the deck! 
    // public void shuffle() {
		//Collections.shuffle(publicationCardDeck);
		
	//}
	

    public ArrayList<PublicationCard> getPublicationCardDeck(){
        return this.publicationCardDeck;
    }
















}
