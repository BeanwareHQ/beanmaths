#include "raylib-cpp/include/Color.hpp"

#include "app.hpp"

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

    if (this->nc.empty()) {
        shouldDeinit = true;
        return; // exit and allow while loop to break
    }

    for (auto& screen : this->nc.getScreens()) {
        screen->update(this->gtState);
    }

    this->shouldDeinit |= this->window.ShouldClose();
}