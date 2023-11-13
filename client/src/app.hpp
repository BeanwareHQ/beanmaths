#pragma once

#include <memory>

#include "raylib-cpp/include/Window.hpp"
#include "src/navigation/navigationcontroller.hpp"
#include "src/screens/screen.hpp"

class App {
public:
    App() : window(1360, 768, "BeanMaths SeePlusPlus") {
        this->gtState = 0;
        screens::TitleScreen titleScreen(this->window, this->nc);

        this->nc = navigation::NavigationController(
            std::make_unique<screens::TitleScreen>(titleScreen));
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