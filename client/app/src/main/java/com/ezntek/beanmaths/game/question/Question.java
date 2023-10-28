package com.ezntek.beanmaths.game.question;

import com.ezntek.beanmaths.game.question.Operations.Operation;
import com.ezntek.beanmaths.components.Component;

public class Question extends Component {
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

    @Override
    public void render() {
        if (!super.shouldRender())
            return;
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }
}
