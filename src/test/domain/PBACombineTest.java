package test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.AtomFactory;
import domain.IngredientCard;
import domain.Molecule;
import domain.PotionBrewingArea;
import enums.Potion;
import error.NullParameterException;
import error.SameMoleculeException;

public class PBACombineTest {
    AtomFactory atomFactory = new AtomFactory();
    IngredientCard ingredientA, ingredientB, ingredientC, ingredientD, ingredientE, ingredientF, ingredientG, ingredientH;

    @BeforeEach
    void setup() {
        Molecule a = new Molecule(
            atomFactory.red().plus().small().create(), 
            atomFactory.green().minus().big().create(),
            atomFactory.blue().minus().small().create()
        );
        
        Molecule b = new Molecule(
            atomFactory.red().minus().small().create(), 
            atomFactory.green().plus().small().create(),
            atomFactory.blue().minus().big().create()
        );
        
        Molecule c = new Molecule(
            atomFactory.red().plus().big().create(), 
            atomFactory.green().plus().big().create(),
            atomFactory.blue().plus().big().create()
        );
        
        Molecule d = new Molecule(
            atomFactory.red().minus().small().create(), 
            atomFactory.green().plus().big().create(),
            atomFactory.blue().plus().small().create()
        );
        
        Molecule e = new Molecule(
            atomFactory.red().minus().big().create(), 
            atomFactory.green().minus().big().create(),
            atomFactory.blue().minus().big().create()
        );
        
        Molecule f = new Molecule(
            atomFactory.red().plus().small().create(), 
            atomFactory.green().minus().small().create(),
            atomFactory.blue().plus().big().create()
        );
        
        Molecule g = new Molecule(
            atomFactory.red().minus().big().create(), 
            atomFactory.green().minus().small().create(),
            atomFactory.blue().plus().small().create()
        );
        
        Molecule h = new Molecule(
            atomFactory.red().plus().big().create(), 
            atomFactory.green().plus().small().create(),
            atomFactory.blue().minus().small().create()
        );

        ingredientA = new IngredientCard("a", a);
        ingredientB = new IngredientCard("b", b);
        ingredientC = new IngredientCard("c", c);
        ingredientD = new IngredientCard("d", d);
        ingredientE = new IngredientCard("e", e);
        ingredientF = new IngredientCard("f", f);
        ingredientG = new IngredientCard("g", g);
        ingredientH = new IngredientCard("h", h);
    }

    @Test
    public void testNullParameters() throws Exception {
        NullParameterException exception = assertThrows(
            NullParameterException.class, 
            () -> PotionBrewingArea.combine(null, null) 
        );
        assertTrue(exception.getMessage().equals("Some of the passed parameters are null."));

        exception = assertThrows(
            NullParameterException.class, 
            () -> PotionBrewingArea.combine(ingredientA, null) 
        );
        assertTrue(exception.getMessage().equals("Some of the passed parameters are null."));
       
        exception = assertThrows(
            NullParameterException.class, 
            () -> PotionBrewingArea.combine(null, ingredientA) 
        );
        assertTrue(exception.getMessage().equals("Some of the passed parameters are null."));
       
        assertDoesNotThrow(() -> PotionBrewingArea.combine(ingredientA, ingredientB));
    }

    @Test
    public void testNeutralPotion() throws Exception {
        assertEquals(Potion.Neutral, PotionBrewingArea.combine(ingredientA, ingredientD));
        assertEquals(Potion.Neutral, PotionBrewingArea.combine(ingredientB, ingredientF));
        assertEquals(Potion.Neutral, PotionBrewingArea.combine(ingredientC, ingredientE));       
        assertEquals(Potion.Neutral, PotionBrewingArea.combine(ingredientG, ingredientH));       
    }
   
    @Test
    public void testHealthPotion() throws Exception {
        assertEquals(Potion.Health, PotionBrewingArea.combine(ingredientA, ingredientC));
        assertEquals(Potion.Health, PotionBrewingArea.combine(ingredientA, ingredientH));
        assertEquals(Potion.Health, PotionBrewingArea.combine(ingredientC, ingredientF));       
        assertEquals(Potion.Health, PotionBrewingArea.combine(ingredientF, ingredientH));       
    }
   
    @Test
    public void testPoisonPotion() throws Exception {
        assertEquals(Potion.Poison, PotionBrewingArea.combine(ingredientB, ingredientE));
        assertEquals(Potion.Poison, PotionBrewingArea.combine(ingredientB, ingredientG));
        assertEquals(Potion.Poison, PotionBrewingArea.combine(ingredientD, ingredientE));
        assertEquals(Potion.Poison, PotionBrewingArea.combine(ingredientD, ingredientG));       
    }

    @Test
    public void testSpeedPotion() throws Exception {
        assertEquals(Potion.Speed, PotionBrewingArea.combine(ingredientB, ingredientC));
        assertEquals(Potion.Speed, PotionBrewingArea.combine(ingredientB, ingredientD));
        assertEquals(Potion.Speed, PotionBrewingArea.combine(ingredientC, ingredientH));       
        assertEquals(Potion.Speed, PotionBrewingArea.combine(ingredientD, ingredientH));       
    }
   
    @Test
    public void testParalysisPotion() throws Exception {
        assertEquals(Potion.Paralysis, PotionBrewingArea.combine(ingredientA, ingredientF));
        assertEquals(Potion.Paralysis, PotionBrewingArea.combine(ingredientA, ingredientG));
        assertEquals(Potion.Paralysis, PotionBrewingArea.combine(ingredientE, ingredientF));
        assertEquals(Potion.Paralysis, PotionBrewingArea.combine(ingredientE, ingredientG));       
    }
   
    @Test
    public void testWisdomPotion() throws Exception {
        assertEquals(Potion.Wisdom, PotionBrewingArea.combine(ingredientC, ingredientD));
        assertEquals(Potion.Wisdom, PotionBrewingArea.combine(ingredientC, ingredientG));
        assertEquals(Potion.Wisdom, PotionBrewingArea.combine(ingredientD, ingredientF));
        assertEquals(Potion.Wisdom, PotionBrewingArea.combine(ingredientF, ingredientG));       
    }
   
    @Test
    public void testInsanityPotion() throws Exception {
        assertEquals(Potion.Insanity, PotionBrewingArea.combine(ingredientA, ingredientB));
        assertEquals(Potion.Insanity, PotionBrewingArea.combine(ingredientA, ingredientE));
        assertEquals(Potion.Insanity, PotionBrewingArea.combine(ingredientB, ingredientH));       
        assertEquals(Potion.Insanity, PotionBrewingArea.combine(ingredientE, ingredientH));       
    }

    @Test
    public void testInterchangingParameters() throws Exception {
        assertEquals(PotionBrewingArea.combine(ingredientA, ingredientB), PotionBrewingArea.combine(ingredientB, ingredientA));
        assertEquals(PotionBrewingArea.combine(ingredientF, ingredientG), PotionBrewingArea.combine(ingredientG, ingredientF));
    } 

    @Test
    public void testSameMolecules() throws SameMoleculeException {
        assertThrows(
            SameMoleculeException.class, 
            () -> PotionBrewingArea.combine(ingredientA, ingredientA)
        );

        assertThrows(
            SameMoleculeException.class, 
            () -> PotionBrewingArea.combine(ingredientH, ingredientH)
        );

        assertThrows(
            SameMoleculeException.class, 
            () -> PotionBrewingArea.combine(ingredientC, ingredientC)
        );
    } 
}
