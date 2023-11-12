#pragma once

#include "Color.hpp"
#include "Rectangle.hpp"
#include "Vector4.hpp"

#include "src/screens/screen.hpp"

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

    void render() override;
};

} // namespace dialogs