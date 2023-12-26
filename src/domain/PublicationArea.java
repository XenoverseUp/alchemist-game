package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * PublicationArea: Where the theory guesses are stored. 
 */
public class PublicationArea {
    private Map<AlchemyMarker, IngredientCard> theories;

    public PublicationArea() {
        theories = new HashMap<>();
    }

    public void addTheory(AlchemyMarker marker, IngredientCard ingredientCard) {
        theories.put(marker, ingredientCard);
        marker.associate();
    }

    public void removeTheory(AlchemyMarker marker) {
        theories.remove(marker);
        marker.dissociate();
    }

    public IngredientCard getTheory(AlchemyMarker marker) {
        return theories.get(marker);
    }

    public Set<Map.Entry<AlchemyMarker, IngredientCard>> getAllTheories() {
        return theories.entrySet();
    }
}
