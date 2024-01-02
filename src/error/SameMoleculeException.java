package error;

public class SameMoleculeException extends Exception {
    public SameMoleculeException() { super("Given ingredients have the same molecule structure,"); }
    public SameMoleculeException(String message) { super(message); }
    public SameMoleculeException(String message, Throwable cause) { super(message, cause); }
    public SameMoleculeException(Throwable cause) { super(cause); }
}
