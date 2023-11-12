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
    ComponentState componentState = ComponentState::ENABLED;

    virtual ~Component(){};

    bool shouldRender() const;
    bool shouldUpdate() const;

    virtual void render(){};
    virtual void update(long gtState){};
};

} // namespace components