#pragma once

#include "raylib-cpp/include/Color.hpp"
#include "raylib-cpp/include/Rectangle.hpp"
#include "raylib-cpp/include/Vector4.hpp"

#include "../screens/screen.hpp"

namespace dialogs {

class Dialog : public screens::Screen {
protected:
    raylib::Rectangle darkeningRect;
    raylib::Color darkeningColor;

public:
    bool darken;

    Dialog(raylib::Window& window)
        : screens::Screen(window),
          darkeningRect(0, 0, window.GetWidth(), window.GetHeight()),
          darkeningColor(raylib::Vector4(80, 80, 80, 80)) {}

    ~Dialog(){};

    void render() override;
};

} // namespace dialogs