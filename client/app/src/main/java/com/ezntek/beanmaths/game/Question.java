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

    public Question() {
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
