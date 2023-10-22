package beanmaths.components;

import java.util.Optional;

public abstract class Component<D> {
    public boolean shouldDeinit;
    public Optional<D> deinitVal;

    public abstract void render();
    public abstract void refresh(int gtState);
}
