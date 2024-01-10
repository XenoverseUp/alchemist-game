package ui.util;

import domain.Game;
import domain.TheAlchemistGame;
import ui.Window;

public class WindowBuilder {
    private String _title = "The Alchemist Game";
    private int _width = 800;
    private int _height = 628;
    private Game _game;

    public WindowBuilder title(String title) {
        this._title = title;
        return this;
    }

    public WindowBuilder width(int width) {
        this._width = width;
        return this;
    }

    public WindowBuilder height(int height) {
        this._height = height;
        return this;
    } 
    
    public WindowBuilder register(Game game) {
        this._game = game;
        return this;
    }

    public Window buildWindow() {
        return new Window(_title, _width, _height, _game);
    }

    
}
