#include "app.hpp"
#include "Color.hpp"

void App::run() {
    while (!this->shouldDeinit) {
        this->update();
        this->window.BeginDrawing();
        this->render();
        this->window.EndDrawing();
    }
}

void App::render() { window.ClearBackground(raylib::Color::RayWhite()); }

void App::update() {
    this->gtState++;

    this->shouldDeinit |= this->window.ShouldClose();
}