#include "Rectangle.hpp"
#include "raygui.h"
#include "src/components/component.hpp"
#include <functional>
#include <optional>
#include <string>

namespace rayguiex {
/**
 * @brief A thin wrapper around raygui.
 *
 * rayguiex: a thin wrapper around raygui to
 * conform to BeanMaths' component system.
 *
 * It aims to make event handling less painful
 * for the developer, and to allow components to be
 * rendered in batches with other components.
 */

using IconName = GuiIconName;

template <class Rt, class Evt> class GuiComponent {
public:
    using EventCallback = std::function<Rt(Evt event)>;

    GuiComponent() {
        /**
         * @brief WARNING: DO NOT USE THIS (default) CONSTRUCTOR!
         *
         * This will not initialize the component properly,
         * and is only here to shut the C++ Compiler up when
         * someone doesn't put any arguments in the constructor
         * when they use this type and all subclass types as a field
         * in a class.
         *
         * This is for the sake of convenience, the
         * ability to properly initialize the component as an assignment
         * within the constructor, not in the initializer list.
         */
        this->eventCallback = [](Evt) -> Rt { return Rt(); };
    }

    GuiComponent(EventCallback cb, raylib::Rectangle bounds) : bounds(bounds) {
        this->setCallback(cb);
    }

    components::ComponentState componentState;

    virtual void render() = 0;
    virtual Rt update(long gtState) = 0;

    bool shouldRender() const;
    bool shouldUpdate() const;

    void setCallback(EventCallback cb) { this->eventCallback = cb; }

    EventCallback eventCallback;

protected:
    raylib::Rectangle bounds;
};

enum ButtonEvent { CLICKED };

class Button : public GuiComponent<bool, ButtonEvent> {
public:
    Button() : GuiComponent<bool, ButtonEvent>() {}

    Button(EventCallback cb, raylib::Rectangle bounds)
        : GuiComponent<bool, ButtonEvent>(cb, bounds) {}

    Button(EventCallback cb, raylib::Rectangle bounds, std::string text)
        : GuiComponent<bool, ButtonEvent>(cb, bounds), buttonText(text) {}

    Button(EventCallback cb, raylib::Rectangle bounds, std::string text,
           IconName icon)
        : GuiComponent<bool, ButtonEvent>(cb, bounds), buttonText(text),
          iconName(icon) {}

    void render() override;
    bool update(long gtState) override;

protected:
    bool buttonClicked;

    std::string buttonText = "";
    std::optional<IconName> iconName;
};

} // namespace rayguiex