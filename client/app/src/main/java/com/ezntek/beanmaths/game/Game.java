package com.ezntek.beanmaths.game;

import com.ezntek.beanmaths.util.RequiresDeinit;
import com.ezntek.beanmaths.game.question.Question;
import com.ezntek.beanmaths.game.question.Operations.Operation;
import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.screens.GameScreen.GameMode;

import static com.raylib.Jaylib.*;
import com.raylib.Raylib.Texture;

public class Game extends Component implements RequiresDeinit {
    GameMode mode;
    Question question;
    Texture texture;

    int windowWidth;
    int windowHeight;

    public Game(GameMode mode, int windowWidth, int windowHeight) {
        this.mode = mode;
        this.question = new Question(1, 1, Operation.ADD);
        this.texture = ResourceLoader.loadOperationsTexture();

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void render() {
        if (!super.shouldRender())
            return;

        this.question.render(this.texture, this.windowWidth, this.windowHeight);
    }

    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }

    @Override
    public void init() {

    }

    @Override
    public void deinit() {
        UnloadTexture(this.texture);
    }
}
