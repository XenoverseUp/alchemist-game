package error;

public class NullParameterException extends Exception {
    public NullParameterException() { super("Some of the passed parameters are null."); }
    public NullParameterException(String message) { super(message); }
    public NullParameterException(String message, Throwable cause) { super(message, cause); }
    public NullParameterException(Throwable cause) { super(cause); }
}
