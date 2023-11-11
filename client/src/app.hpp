#pragma once

#include "Window.hpp"

class App {
  public:
    App() : window(1360, 768, "BeanMaths SeePlusPlus") { this->gtState = 0; };
    ~App() { this->window.Close(); };

    void run();

  private:
    raylib::Window window;
    long gtState;
    bool shouldDeinit;

    void render();
    void update();
};