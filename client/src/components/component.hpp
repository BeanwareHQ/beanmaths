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

    virtual ~Component() = default;

    bool shouldRender() const;
    bool shouldUpdate() const;

    virtual void render() = 0;
    virtual void update(long gtState) = 0;
};

} // namespace components