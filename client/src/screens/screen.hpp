#pragma once

#include "raylib-cpp/include/Rectangle.hpp"
#include "raylib-cpp/include/Window.hpp"

#include "src/components/component.hpp"
#include "src/navigation/navigationcontroller.hpp"

namespace screens {

class Screen : public components::Component {
public:
    Screen(raylib::Window& window, navigation::NavigationController& nc)
        : Component(), window(window), nc(nc){};

    // virtual void render();
    // virtual void refresh(long gtState);

protected:
    raylib::Window& window;
    navigation::NavigationController& nc;
};

// class TitleScreen
class TitleScreen : public Screen {
private:
    struct TitleScreenState {
        bool playButton;
        bool settingsButton;
        bool aboutButton;
        bool exitButton;
    };
    TitleScreenState state;

    // UI elements
    const raylib::Rectangle dummyRec = raylib::Rectangle(296, 72, 776, 264);
    const raylib::Rectangle playButton = raylib::Rectangle(480, 608, 408, 48);
    const raylib::Rectangle settingsButton =
        raylib::Rectangle(480, 664, 120, 40);
    const raylib::Rectangle exitButton = raylib::Rectangle(768, 664, 120, 40);
    const raylib::Rectangle aboutButton = raylib::Rectangle(608, 664, 152, 40);

public:
    components::ComponentState componentState =
        components::ComponentState::ENABLED;

    TitleScreen(raylib::Window& window, navigation::NavigationController& nc)
        : Screen(window, nc) {
        this->state = TitleScreenState{false, false, false, false};
    }

    ~TitleScreen() = default;

    void render() override;
    void update(long gtState) override;
};
// class TitleScreen

// class

} // namespace screens