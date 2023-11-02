package com.ezntek.beanmaths.screens;

import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.util.RequiresDeinit;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.game.Game;
import com.ezntek.beanmaths.navigation.NavigationController;

public class GameScreen extends Screen implements RequiresDeinit {
    public enum GameMode {
        SINGLEPLAYER,
        MULTIPLAYER,
    }

    public ComponentState cmpState = ComponentState.DISABLED;
    private Game game;

    public GameScreen(Config cfg, NavigationController nc, int windowWidth, int windowHeight, GameMode gameMode) {
        super(cfg, nc, windowWidth, windowHeight);

        this.game = new Game(gameMode, windowWidth, windowHeight);
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
            this.deinit();
            nc.pop();
            return;
        }

        this.game.update(gtState);
    }

    @Override
    public void init() {
        this.game.init();
    }

    @Override
    public void deinit() {
        this.game.deinit();
    }
}
