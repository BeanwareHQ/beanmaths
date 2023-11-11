#pragma once

namespace components {

enum ComponentState {
    ENABLED,
    NORENDER,
    NOUPDATE,
    DISABLED,
};

class Component {
  public:
    ComponentState componentState;

    bool shouldRender();
    bool shouldUpdate();

    virtual void render();
    virtual void update(long gtState);
};

} // namespace components