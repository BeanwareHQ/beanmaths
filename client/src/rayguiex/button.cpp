#include "rayguiex.hpp"
#include "src/components/component.hpp"

namespace rayguiex {

using components::ComponentState;

template <class Rt, class Evt>
bool GuiComponent<Rt, Evt>::shouldRender() const {
    return !(this->componentState == ComponentState::NORENDER ||
             this->componentState == ComponentState::DISABLED);
}

template <class Rt, class Evt>
bool GuiComponent<Rt, Evt>::shouldUpdate() const {
    return !(this->componentState == ComponentState::NOUPDATE ||
             this->componentState == ComponentState::DISABLED);
}

void Button::render() {
    if (!this->shouldRender())
        return;

    const char* text = this->buttonText.c_str();

    if (this->iconName.has_value())
        text = GuiIconText(this->iconName.value(), text);

    this->buttonClicked = GuiButton(this->bounds, text);
}

bool Button::update(long) {
    // if (!this->shouldUpdate())
    if (this->buttonClicked)
        return this->eventCallback(ButtonEvent::CLICKED);
    return false;
}

} // namespace rayguiex