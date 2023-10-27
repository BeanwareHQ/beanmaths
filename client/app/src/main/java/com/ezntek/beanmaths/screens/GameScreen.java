package com.ezntek.beanmaths.screens;

import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.game.Game;
import com.ezntek.beanmaths.navigation.NavigationController;

public class GameScreen extends Screen {
    public enum GameMode {
        SINGLEPLAYER,
        MULTIPLAYER,
    }

    public ComponentState cmpState = ComponentState.DISABLED;
    private Game game;

    public GameScreen(NavigationController nc, int windowWidth, int windowHeight, GameMode gameMode) {
        super(nc, windowWidth, windowHeight);

        this.game = new Game(gameMode);
        // TODO: implement multiplayer (😭)
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        this.game.render();
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        if (IsKeyPressed(KEY_ESCAPE)) {
            nc.pop();
            return;
        }

        this.game.update(gtState);
    }
}
