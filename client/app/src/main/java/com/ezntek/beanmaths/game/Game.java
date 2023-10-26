package com.ezntek.beanmaths.game;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.screens.GameScreen.GameMode;
//import com.google.code.gson.Gson;

public class Game extends Component {
    GameMode mode;

    public Game(GameMode mode) {
        this.mode = mode;
    }

    public void render() {
        if (!super.shouldRender())
            return;
    }

    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }
}
