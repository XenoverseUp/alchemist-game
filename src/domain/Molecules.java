package domain;

import java.util.ArrayList;

public class Molecules {
    public static AtomFactory factory = new AtomFactory();
    public static ArrayList<Molecule> molecules = new ArrayList<Molecule>() {{
        add(new Molecule(
            factory.red().plus().small().create(), 
            factory.green().minus().big().create(),
            factory.blue().minus().small().create()
        ));
        
        add(new Molecule(
            factory.red().minus().small().create(), 
            factory.green().plus().small().create(),
            factory.blue().minus().big().create()
        ));
        
        add(new Molecule(
            factory.red().plus().big().create(), 
            factory.green().plus().big().create(),
            factory.blue().plus().big().create()
        ));
        
        add(new Molecule(
            factory.red().minus().small().create(), 
            factory.green().plus().big().create(),
            factory.blue().plus().small().create()
        ));
        
        add(new Molecule(
            factory.red().minus().big().create(), 
            factory.green().minus().big().create(),
            factory.blue().minus().big().create()
        ));
        
        add(new Molecule(
            factory.red().plus().small().create(), 
            factory.green().minus().small().create(),
            factory.blue().plus().big().create()
        ));
        
        add(new Molecule(
            factory.red().minus().big().create(), 
            factory.green().minus().small().create(),
            factory.blue().plus().small().create()
        ));
        
        add(new Molecule(
            factory.red().plus().big().create(), 
            factory.green().plus().small().create(),
            factory.blue().minus().small().create()
        ));
    }};
}
