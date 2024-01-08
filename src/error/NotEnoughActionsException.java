package error;

public class NotEnoughActionsException extends Exception {
    
    public NotEnoughActionsException(){
        super("The user does not have enough actions to play.");
    }
}
