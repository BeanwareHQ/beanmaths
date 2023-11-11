#include "./component.hpp"

namespace components {

bool Component::shouldRender() {
    return !(this->componentState == ComponentState::NORENDER ||
             this->componentState == ComponentState::DISABLED);
}

bool Component::shouldUpdate() {
    return !(this->componentState == ComponentState::NOUPDATE ||
             this->componentState == ComponentState::DISABLED);
}

} // namespace components