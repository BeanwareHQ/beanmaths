package com.ezntek.beanmaths.game;

import com.ezntek.beanmaths.components.Component;

public class Question extends Component {
    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV,
        POW,
        ROOT,
    }

    Operation op;
    int first;
    int second;

    public Question() {

    }

    public Question(int first, int second) {

    }

    public Question(Operation op) {

    }

    public Question(int first, int second, Operation op) {
        this.op = op;
        this.first = first;
        this.second = second;
    }

    public int solve() {
        return -1; // TODO: write
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

    // ----- Generation Functions -----

    // ----- End Generation Functions -----
}
