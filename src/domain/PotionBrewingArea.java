package domain;

import enums.Potion;

public class PotionBrewingArea {
    public static Potion combine(IngredientCard ingredientCard1, IngredientCard ingredientCard2) {
        Molecule molecule1 = ingredientCard1.getMolecule();
        Molecule molecule2 = ingredientCard2.getMolecule();
        Atom redAtom1 = molecule1.getRed();
        Atom greenAtom1 = molecule1.getGreen();
        Atom blueAtom1 = molecule1.getBlue();
        Atom redAtom2 = molecule2.getRed();
        Atom greenAtom2 = molecule2.getGreen();
        Atom blueAtom2 = molecule2.getBlue();
        

        if(redAtom1.size != redAtom2.size){
            if(redAtom1.sign == '+' && redAtom2.sign == '+'){
                return Potion.Health;
            }
            else if(redAtom1.sign == '-' && redAtom2.sign == '-'){
                return Potion.Poison;
            }

        } else if(greenAtom1.size != greenAtom2.size){
            if(greenAtom1.sign == '+' && greenAtom2.sign == '+'){
                return Potion.Speed;
            }
            else if(greenAtom1.sign == '-' && greenAtom2.sign == '-'){
                return Potion.Paralysis;
            }

        } else if(blueAtom1.size != blueAtom2.size){
            if(blueAtom1.sign == '+' && blueAtom2.sign == '+'){
                return Potion.Wisdom;
            }
            else if(blueAtom1.sign == '-' && blueAtom2.sign == '-'){
                return Potion.Insanity;
            }

        }
        
        return Potion.Neutral;
    }    
}
