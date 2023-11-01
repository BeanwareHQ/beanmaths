package com.ezntek.beanmaths.game.question;

import com.ezntek.beanmaths.game.question.Operations.Operation;

import static com.raylib.Jaylib.*;

public class Question {
    Operation op;
    int first;
    int second;

    public Question(int first, int second, Operation op) {
        this.op = op;
        this.first = first;
        this.second = second;
    }

    public int solve() {
        switch (this.op) {
            case ADD:
                return this.first + this.second;
            case SUB:
                return this.first - this.second;
            case MUL:
                return this.first * this.second;
            case DIV:
                return Math.round(this.first / this.second);
            case POW:
                return (int) Math.pow(this.first, this.second);
            case ROOT:
                float result = (float) Math.pow(this.first, (1 / this.second));
                return Math.round(result);
            default:
                return -1;
        }
    }

    public void render(Texture symbolTexture, int screenWidth, int screenHeight) {
        int frameWidth = 90;
        int frameHeight = 120;

        Rectangle sourceRect = new Rectangle(0, 0, frameWidth, frameHeight);
        Rectangle destRect = new Rectangle(screenWidth / 2, screenHeight / 2, frameWidth, frameHeight);
        DrawTexturePro(symbolTexture, sourceRect, destRect, new Vector2(0, 0), 0, WHITE);
    }

    public void update(long gtState) {

    }
}
