import domain.Game;
import ui.Window;
import ui.util.WindowBuilder;

public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        
        Window window = new WindowBuilder()
                                .width(1440)
                                .height(768)
                                .register(game)
                                .buildWindow();
        window.init();
    }  
}
