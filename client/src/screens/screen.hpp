#pragma once

#include "raylib-cpp/include/Rectangle.hpp"
#include "raylib-cpp/include/Window.hpp"

#include "src/components/component.hpp"
#include "src/navigation/navigationcontroller.hpp"
#include "src/rayguiex/rayguiex.hpp"
#include <iostream>

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
    // UI elements
    raylib::Rectangle dummyRec = raylib::Rectangle(296, 72, 776, 264);

    rayguiex::Button playButton = rayguiex::Button(
        [&](rayguiex::ButtonEvent) -> bool {
            std::cout << "play button clicked" << std::endl;
            return false;
        },
        raylib::Rectangle(480, 608, 408, 48), "Play",
        rayguiex::IconName::ICON_PLAYER_PLAY);

    rayguiex::Button settingsButton = rayguiex::Button(
        [&](rayguiex::ButtonEvent) -> bool {
            std::cout << "settings button clicked" << std::endl;
            return false;
        },
        raylib::Rectangle(480, 664, 120, 40), "Settings",
        rayguiex::IconName::ICON_GEAR);

    rayguiex::Button exitButton;

    rayguiex::Button aboutButton = rayguiex::Button(
        [&](rayguiex::ButtonEvent) -> bool {
            std::cout << "about button clicked" << std::endl;
            return false;
        },
        raylib::Rectangle(608, 664, 152, 40), "About",
        rayguiex::IconName::ICON_INFO);

public:
    components::ComponentState componentState =
        components::ComponentState::ENABLED;

    TitleScreen(raylib::Window& window, navigation::NavigationController& nc)
        : Screen(window, nc) {
        this->exitButton = rayguiex::Button(
            [&](rayguiex::ButtonEvent) -> bool {
                this->nc.pop();
                return true;
            },
            raylib::Rectangle(768, 664, 120, 40), "Quit",
            rayguiex::IconName::ICON_EXIT);
    }

    ~TitleScreen() = default;

    void render() override;
    void update(long gtState) override;
};
// class TitleScreen

} // namespace screens