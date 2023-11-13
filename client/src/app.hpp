#pragma once

#include "raylib-cpp/include/Window.hpp"
#include "src/navigation/navigationcontroller.hpp"
#include <memory>

class App {
public:
    App() : window(1360, 768, "BeanMaths SeePlusPlus") {
        this->gtState = 0;
        auto titleScreen = std::make_unique<screens::TitleScreen>(
            screens::TitleScreen(this->window));

        this->nc = navigation::NavigationController(std::move(titleScreen));
    };
    ~App() { this->window.Close(); };

    void run();

private:
    navigation::NavigationController nc;
    raylib::Window window;
    long gtState;
    bool shouldDeinit;

    void render();
    void update();
};