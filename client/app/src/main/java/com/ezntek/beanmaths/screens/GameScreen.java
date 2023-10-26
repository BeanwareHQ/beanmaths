package com.ezntek.beanmaths.screens;

import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.navigation.NavigationController;

public class GameScreen extends Screen {
    public ComponentState cmpState = ComponentState.DISABLED;

    public GameScreen(NavigationController nc, int windowWidth, int windowHeight) {
        super(nc, windowWidth, windowHeight);
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
