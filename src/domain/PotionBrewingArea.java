package domain;

import enums.Potion;
import error.NullParameterException;
import error.SameMoleculeException;

public class PotionBrewingArea {
    public static Potion combine(IngredientCard ingredientCard1, IngredientCard ingredientCard2) throws NullParameterException, SameMoleculeException  {
        if (ingredientCard1 == null || ingredientCard2 == null) throw new NullParameterException();
        
        if (ingredientCard1.getMolecule() == ingredientCard2.getMolecule()) throw new SameMoleculeException();
        
        Molecule molecule1 = ingredientCard1.getMolecule();
        Molecule molecule2 = ingredientCard2.getMolecule();
        Atom redAtom1 = molecule1.getRed();
        Atom greenAtom1 = molecule1.getGreen();
        Atom blueAtom1 = molecule1.getBlue();
        Atom redAtom2 = molecule2.getRed();
        Atom greenAtom2 = molecule2.getGreen();
        Atom blueAtom2 = molecule2.getBlue();


        if(redAtom1.size != redAtom2.size){

            if(redAtom1.sign == '+' && redAtom2.sign == '+') {
                return Potion.Health;
            } 
            
            if(redAtom1.sign == '-' && redAtom2.sign == '-') {
                return Potion.Poison;
            }

        } 


        if(greenAtom1.size != greenAtom2.size) {

            if(greenAtom1.sign == '+' && greenAtom2.sign == '+') {
                return Potion.Speed;
            } 
            
            if(greenAtom1.sign == '-' && greenAtom2.sign == '-') {
                return Potion.Paralysis;
            }

        } 

        if(blueAtom1.size != blueAtom2.size) {

            if(blueAtom1.sign == '+' && blueAtom2.sign == '+') {
                return Potion.Wisdom;
            } 
            
            if(blueAtom1.sign == '-' && blueAtom2.sign == '-') {
                return Potion.Insanity;
            }
        }
        
        return Potion.Neutral;
    }    
}
