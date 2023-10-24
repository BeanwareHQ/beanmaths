package com.ezntek.beanmaths.components;

public abstract class Component {
    public ComponentState state;
    
    public abstract void render();
    public abstract void refresh(int gtState);
}
