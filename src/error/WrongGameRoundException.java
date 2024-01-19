package error;

public class WrongGameRoundException extends Exception {
    public WrongGameRoundException(){
        super("The action is not available on this round");
    }
}
