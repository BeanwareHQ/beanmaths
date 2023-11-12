#pragma once

#include <vector>

#include "src/dialogs/dialog.hpp"
#include "src/screens/screen.hpp"

namespace navigation {

class NavigationController {
public:
    NavigationController() {}
    NavigationController(screens::Screen initialScreen) {
        this->add(initialScreen);
    }

    screens::Screen back() const;
    bool empty() const;

    void add(screens::Screen screen);
    void add(dialogs::Dialog dialog);
    screens::Screen pop();
    auto getScreens() -> std::vector<screens::Screen>&;

private:
    std::vector<screens::Screen> screens;
};

} // namespace navigation
