package com.ezntek.beanmaths.components;

import com.ezntek.beanmaths.components.ComponentState;

public abstract class Component {
    public Component state;

    public abstract void render();
    public abstract void refresh(int gtState);
}
