package com.ezntek.beanmaths.components;

import static com.ezntek.beanmaths.components.ComponentState.*;

public abstract class Component {
    public ComponentState state;

    public static Component makeComponent() {
        class DummyComponent extends Component {
            public void render() {
            }

            public void update(long gtState) {
            }
        }

        return new DummyComponent();
    }

    public abstract void render();

    public abstract void update(long gtState);

    public boolean shouldRender() {
        return !(this.state == DISABLED || this.state == NODRAW);
    }

    public boolean shouldUpdate() {
        return !(this.state == DISABLED || this.state == NOUPDATE);
    }
}
