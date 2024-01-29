package error;

public class HostDoesNotExistsException extends Exception {
    public HostDoesNotExistsException() { super("Host does not exists."); }
    public HostDoesNotExistsException(String message) { super(message); }
    public HostDoesNotExistsException(String message, Throwable cause) { super(message, cause); }
    public HostDoesNotExistsException(Throwable cause) { super(cause); }
}
