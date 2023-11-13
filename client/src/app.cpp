#include "app.hpp"
#include "Color.hpp"
#include <iostream>

void App::run() {
    while (!this->shouldDeinit) {
        this->update();
        this->window.BeginDrawing();
        this->render();
        this->window.EndDrawing();
    }
}

void App::render() {
    window.ClearBackground(raylib::Color::RayWhite());

    for (auto& screen : this->nc.getScreens()) {
        screen->render();
    }
}

void App::update() {
    this->gtState++;

    for (auto& screen : this->nc.getScreens()) {
        screen->update(this->gtState);
    }

    this->shouldDeinit |= this->window.ShouldClose();
}