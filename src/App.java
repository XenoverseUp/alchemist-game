import domain.TheAlchemistGame;
import ui.Window;
import ui.WindowBuilder;

public class App {
    public static void main(String[] args) throws Exception {
        TheAlchemistGame game = new TheAlchemistGame();
        Window window = new WindowBuilder()
                                .width(1440)
                                .height(768)
                                .register(game)
                                .buildWindow();
        window.init();
    }  
}
